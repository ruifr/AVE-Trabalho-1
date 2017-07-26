package util;

import api.dto.ContainerDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public class CacheStream<T,R> {
    private Map<String,LinkedList<R>> map = new HashMap<>();
    private BiFunction<String, Integer, CompletableFuture<ContainerDto<T>>> containerSupplier;
    private Function<T,R> dtoToModel;

    private CacheStream(BiFunction<String, Integer, CompletableFuture<ContainerDto<T>>> containerSupplier, Function<T,R> dtoToModel) {
        this.containerSupplier = containerSupplier;
        this.dtoToModel = dtoToModel;
    }

    private Stream<R> toStream(String query, int sPage, Consumer<R> add) {
        return stream(new Spliterators.AbstractSpliterator<R>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = sPage;
            CompletableFuture<ContainerDto<T>> gcd = containerSupplier.apply(query, page);
            int idxDto[] = new int[] { 0 };
            Spliterator<R> str;

            @Override
            public boolean tryAdvance(Consumer<? super R> action) {
                if(str == null) {
                    T[] tmp = containerSupplier.apply(query, page++).thenApply(ContainerDto::getModel).join();
                    idxDto[0] = 0;
                    str = Stream.generate(() -> tmp[idxDto[0]++]).limit(tmp.length).map(dtoToModel).spliterator();
                    if(str == null) return false;
                }
                if(str.tryAdvance(item -> { add.accept(item); action.accept(item);})) return true;
                if(!gcd.join().isValidPage(page)) return false;
                str = null;
                return tryAdvance(action);
            }
        }, false);
    }

    private Stream<R> toStream(String query, LinkedList<R> l) {
        return stream(new Spliterators.AbstractSpliterator<R>(Long.MAX_VALUE, Spliterator.ORDERED) {
            private int idx = 0;
            private boolean flag = false;
            private Spliterator<R> split;
            @Override
            public boolean tryAdvance(Consumer<? super R> action) {
                if (flag)
                    return split.tryAdvance(action);

                if (idx >= l.size()) {
                    split = toStream(query, idx / 30 + 1, l::add)
                            .skip(idx - idx / 30)
                            .spliterator();
                    flag = true;
                    return tryAdvance(action);
                }

                action.accept(l.get(idx++));
                return true;
            }
        }, false);
    }

    public Stream<R> toStream(String query) {
        LinkedList<R> l = map.get(query);

        if(l != null)
            return toStream(query, l);

        l = new LinkedList<>();
        Stream<R> ret = toStream(query, 1, l::add);
        map.put(query,l);
        return ret;
    }

    public static <T,R> CacheStream<T,R> from(BiFunction<String, Integer, CompletableFuture<ContainerDto<T>>> containerSupplier, Function<T,R> dtoToModel) {
        return new CacheStream<>(containerSupplier, dtoToModel);
    }
}
package util;

import api.dto.ContainerDto;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;

public class CacheStream<T,R> {
    private Map<String,LinkedList<R>> map = new HashMap<>();
    private BiFunction<String, Integer, ContainerDto<T>> containerSupplier;
    private Function<T,R> dtoToModel;

    private CacheStream(BiFunction<String, Integer, ContainerDto<T>> containerSupplier, Function<T,R> dtoToModel) {
        this.containerSupplier = containerSupplier;
        this.dtoToModel = dtoToModel;
    }

    private Stream<R> toStream(String query, int sPage, Consumer<R> add) {
        return stream(new Spliterators.AbstractSpliterator<R>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = sPage;
            ContainerDto gcd;
            T dtos[];
            int idxDto[] = new int[] { 0 };
            Spliterator<R> str;

            @Override
            public boolean tryAdvance(Consumer<? super R> action) {
                if(gcd == null) {
                    gcd = containerSupplier.apply(query, page++);
                    dtos = (T[])gcd.getModel();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).limit(dtos.length).map(dtoToModel).spliterator();
                    return tryAdvance(action);
                }
                if(str.tryAdvance(item -> { add.accept(item); action.accept(item);}))
                    return true;
                if(gcd.isValidPage(page)) {
                    idxDto[0] = 0;
                    dtos = containerSupplier.apply(query, page++).getModel();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).limit(dtos.length).map(dtoToModel).spliterator();
                    return tryAdvance(action);
                }
                return false;
            }
        }, false);
    }

    private Stream<R> toStream(String query, LinkedList<R> l) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(Long.MAX_VALUE, Spliterator.ORDERED) {
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
        map.put(query,l);
        return toStream(query, 1, l::add);
    }

    public static <T,R> CacheStream<T,R> from(BiFunction<String, Integer, ContainerDto<T>> containerSupplier, Function<T,R> dtoToModel) {
        return new CacheStream<>(containerSupplier, dtoToModel);
    }
}
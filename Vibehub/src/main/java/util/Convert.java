package util;

import api.dto.ContainerDto;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.*;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public class Convert {
    public static <T,R> Stream<R> toStream(BiFunction<String, Integer, ContainerDto<T>> gcdSup, Function<T,R> func, String query) {
        return stream(new Spliterators.AbstractSpliterator<R>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            ContainerDto gcd;
            T dtos[];
            int idxDto[] = new int[] { 0 };
            Spliterator<R> str;

            @Override
            public boolean tryAdvance(Consumer<? super R> action) {
                if(gcd == null) {
                    gcd = gcdSup.apply(query, page++);
                    dtos = (T[])gcd.getModel();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).limit(dtos.length).map(func).spliterator();
                    return tryAdvance(action);
                }
                if(str.tryAdvance(action::accept))
                    return true;
                if(gcd.isValidPage(page)) {
                    idxDto[0] = 0;
                    dtos = gcdSup.apply(query, page++).getModel();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).limit(dtos.length).map(func).spliterator();
                    return tryAdvance(action);
                }
                return false;
            }
        }, false);
    }
}
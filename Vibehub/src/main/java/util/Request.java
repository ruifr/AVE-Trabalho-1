package util;

import java.io.InputStream;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Miguel Gamboa
 *         created on 22-03-2017
 */
public class Request implements IRequest {

    final Function<String, InputStream> getStream;

    public Request(Function<String, InputStream> getStream) {
        this.getStream = getStream;
    }

    @Override
    public final Stream<String> getContent(String path) {
        Spliterator<String> iter = new IteratorFromReader(getStream.apply(path));
        return StreamSupport.stream(iter, false);
    }

}

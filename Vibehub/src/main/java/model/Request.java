package model;

import java.io.InputStream;
import java.util.function.Function;

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
    public final Iterable<String> getContent(String path) {
        return null;//() -> new IteratorFromReader(getStream.apply(path));
    }

}

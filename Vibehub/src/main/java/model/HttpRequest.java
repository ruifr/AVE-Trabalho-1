package model;

import model.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.function.Function;

/**
 * @author Miguel Gamboa
 *         created on 08-03-2017
 */
public class HttpRequest extends Request {

    public HttpRequest() {
        super(HttpRequest::getStream);
    }

    public static InputStream getStream(String path) {
        try {
            return new URL(path).openStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

package util;

import java.io.*;
import java.net.URL;

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
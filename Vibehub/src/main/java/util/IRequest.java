package util;

import java.util.stream.Stream;

public interface IRequest {

    Stream<String> getContent(String path);
}

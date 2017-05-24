import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Cache {
    public static <T,R>Function<T,R> memoize(Function<T,R> func) {
        final Map<T,R> map = new HashMap<>();
        return key -> {
            R res = map.get(key);
            if(res == null) {
                res = func.apply(key);
                map.put(key,res);
            }
            return res;
        };
    }
}
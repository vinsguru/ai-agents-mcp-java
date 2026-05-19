import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static Map<String, Object> cache = new HashMap<>();

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

}
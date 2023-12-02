package library.lib.Library.Utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class Service {

    private static final String BASE_URL = "http://localhost:8080/api";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String buildParameters(Map<Object, Object> data) {
        StringJoiner joiner = new StringJoiner("&", "?", "");

        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            joiner.add(encodePair(entry.getKey().toString(), entry.getValue().toString()));
        }

        return joiner.toString();
    }

    private static String encodePair(String key, String value) {
        return URLEncoder.encode(key, StandardCharsets.UTF_8) +
                "=" +
                URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}

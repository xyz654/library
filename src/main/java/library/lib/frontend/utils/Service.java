package library.lib.frontend.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class Service {

    private Service() {

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

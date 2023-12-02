package library.lib.Library.Utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Service {

    private static final String BASE_URL = "http://localhost:8080/api";

    public static String getBaseUrl() {
        return BASE_URL;
    }
    public static String buildParameters(Map<Object, Object> data) {
        // Build a request body in the form of key-value pairs
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if(builder.length() == 0){
                builder.append("?");
            }
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return builder.toString();
    }
}

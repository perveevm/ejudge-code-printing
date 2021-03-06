package ru.strategy48.ejudge.printer.server.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Some useful methods for working with {@link com.sun.net.httpserver.HttpHandler}
 */
public class HandlerUtils {
    /**
     * Returns parsed GET/POST parameters from encoded URL
     *
     * @param response server response
     * @return {@link Map} from parameter name to its value
     * @throws IOException if error happened while reading from {@link HttpExchange}
     */
    public static Map<String, String> parseResponseParameters(final HttpExchange response) throws IOException {
        StringBuilder parameters = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getRequestBody()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parameters.append(line);
            }
        }

        Map<String, String> splitParameters = new HashMap<>();
        for (String param : parameters.toString().split("&")) {
            String[] cur = param.split("=");
            if (cur.length != 2) {
                continue;
            }

            String name = URLDecoder.decode(cur[0], StandardCharsets.UTF_8);
            String val = URLDecoder.decode(cur[1], StandardCharsets.UTF_8);
            splitParameters.put(name, val);
        }

        return splitParameters;
    }
}

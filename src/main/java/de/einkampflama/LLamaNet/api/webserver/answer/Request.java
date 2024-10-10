package de.einkampflama.LLamaNet.api.webserver.answer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public class Request {
    private final HttpExchange exchange;
    private final InputStream stream;
    private final Headers headers;

    private int code;

    private boolean alreadySended = false;

    public Request(HttpExchange exchange) {
        this.exchange = exchange;
        this.stream = exchange.getRequestBody();
        this.headers = this.exchange.getRequestHeaders();

        this.code = 200;
    }

    public Map<String, String> getQuery() {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> queryParams = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 1) {
                    queryParams.put(keyValue[0], keyValue[1]);
                } else {
                    queryParams.put(keyValue[0], "");
                }
            }
        }
        return queryParams;
    }

    public Map<String, String> getPostParams() throws IOException {
        String body = getBodyAsString();
        Map<String, String> postParams = new HashMap<>();

        if (body != null && !body.isEmpty()) {
            String[] pairs = body.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    postParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return postParams;
    }

    public Map<String, String> getCookies() {
        Map<String, String> cookieMap = new HashMap<>();
        String cookieHeader = headers.getFirst("Cookie");

        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");
            for (String cookie : cookies) {
                String[] keyValue = cookie.split("=");
                if (keyValue.length == 2) {
                    String name = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    cookieMap.put(name, value);
                }
            }
        }

        return cookieMap;
    }

    public Map<String, String> getBody() throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        return formToMap(body.toString());
    }

    public String getBodyAsString() throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private Map<String, String> formToMap(String form) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = form.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = decode(keyValue[0].trim());
                String value = decode(keyValue[1].trim());
                map.put(key, value);
            }
        }
        return map;
    }

    private String decode(String value) {
        try {
            return java.net.URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public String getMethod() {
        return exchange.getRequestMethod();
    }

    public void setHeader(String key, String value){
        if(!alreadySended){
            headers.set(key, value);
        }
    }

    public String getHeader(String key){
        return headers.getFirst(key);
    }

    public void addHeader(String key, String value){
        if(!alreadySended){
            headers.add(key, value);
        }
    }

    public void close() throws IOException {
        if (!alreadySended) {
            exchange.sendResponseHeaders(code, 0);
        }
        stream.close();
    }

    public InputStream getStream() {
        return stream;
    }
}

package de.einkampflama.LLamaNet.api.webserver.answer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

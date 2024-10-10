package de.einkampflama.LLamaNet.api.webserver.answer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public class Response {
    private final HttpExchange exchange;
    private final OutputStream stream;
    private final Headers headers;

    private int code;

    private boolean alreadySended = false;

    public Response(HttpExchange exchange) {
        this.exchange = exchange;
        this.stream = exchange.getResponseBody();
        this.headers = this.exchange.getResponseHeaders();

        this.code = 200;
    }

    public void printAsJson(Map<String, String> map) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\": ")
                    .append("\"").append(entry.getValue()).append("\", ");
        }

        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }
        jsonBuilder.append("}");

        setHeader("Content-Type", "application/json");
        print(jsonBuilder.toString());
    }

    public void setCookie(Cookie cookie) {
        headers.add("Set-Cookie", cookie.toSetCookieHeader());
    }

    public void print(String content) throws IOException {
        print(content.getBytes(StandardCharsets.UTF_8));
    }

    public void print(byte[] bytes) throws IOException {
        if (!alreadySended)
            exchange.sendResponseHeaders(code, 0);
        alreadySended = true;
        stream.write(bytes);
        stream.flush();
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
}

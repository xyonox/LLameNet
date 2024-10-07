package de.einkampflama.LLamaNet.api.webserver.answer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

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

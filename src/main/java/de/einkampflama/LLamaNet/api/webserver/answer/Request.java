package de.einkampflama.LLamaNet.api.webserver.answer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

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

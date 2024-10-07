package de.einkampflama.LLamaNet.api.webserver;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import de.einkampflama.LLamaNet.api.webserver.answer.Exchange;
import de.einkampflama.LLamaNet.api.webserver.answer.Request;
import de.einkampflama.LLamaNet.api.webserver.answer.Response;
import de.einkampflama.LLamaNet.api.webserver.route.RouteHandler;
import de.einkampflama.LLamaNet.api.webserver.route.RouteFace;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public class LLWebServer {
    private final HttpServer server;
    private final RouteHandler routeHandler;

    public LLWebServer(int port, RouteHandler routeHandler) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.routeHandler = routeHandler;

        // Setze den Executor auf einen Cached Thread Pool, um Anfragen parallel zu bearbeiten
        server.setExecutor(Executors.newCachedThreadPool());
    }

    public void start() {
        server.start();
        System.out.println("Server started and listening on port " + server.getAddress().getPort());
    }

    public void stop(int delay) {
        server.stop(delay);
    }

    public void registerRoute(String path, RouteFace routeFace) {
        server.createContext(path, new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    // Erstelle die Anfrage- und Antwortobjekte
                    Request request = new Request(exchange);
                    Response response = new Response(exchange);
                    Exchange exchangeObj = new Exchange(request, response);

                    // Rufe den Handler der Route auf
                    routeFace.handle(exchangeObj);

                    // Schliesse die Antwort
                    response.close();
                } catch (Exception e) {
                    // Fehlerbehandlung
                    e.printStackTrace();
                    String errorMessage = "500 Internal Server Error: " + e.getMessage();
                    exchange.sendResponseHeaders(500, errorMessage.length());
                    exchange.getResponseBody().write(errorMessage.getBytes());
                    exchange.close();
                }
            }
        });
    }

    public void registerRoutes() {
        routeHandler.getRoutes().forEach(this::registerRoute);
    }
}
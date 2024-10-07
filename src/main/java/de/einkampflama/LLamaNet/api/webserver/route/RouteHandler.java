package de.einkampflama.LLamaNet.api.webserver.route;

import de.einkampflama.LLamaNet.LLamaNet;

import java.util.HashMap;

public class RouteHandler {
    private final LLamaNet lLamaNet;
    private final HashMap<String, RouteFace> routes = new HashMap<>();

    public RouteHandler(LLamaNet lLamaNet) {
        this.lLamaNet = lLamaNet;
    }

    public void register(RouteFace route) {
        if (route.info == null) {
            lLamaNet.getLogger().error("Route have no info");
        } else {
            routes.put(route.info.path(), route);
        }
    }

    public HashMap<String, RouteFace> getRoutes() {
        return routes;
    }
}

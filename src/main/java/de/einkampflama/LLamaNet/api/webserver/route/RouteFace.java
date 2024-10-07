package de.einkampflama.LLamaNet.api.webserver.route;

import de.einkampflama.LLamaNet.api.webserver.answer.Exchange;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public abstract class RouteFace {

    protected final Route info;

    protected RouteFace(){
        this.info = this.getClass().getAnnotation(Route.class);
    }

    public synchronized void handle(Exchange exchange){

    }

    public Route getInfo() {
        return info;
    }
}

package de.einkampflama.LLamaNet.api.webserver.route;

import de.einkampflama.LLamaNet.api.webserver.answer.Exchange;

public abstract class RouteFace {

    protected final Route info;

    protected RouteFace(){
        this.info = this.getClass().getAnnotation(Route.class);
    }

    public synchronized void handle(Exchange exchange){

    }
}

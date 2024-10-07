package de.einkampflama.LLamaNet.api.webserver.route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String path();

}

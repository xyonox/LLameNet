package de.einkampflama.LLamaNet.api.webserver.route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String path();

}

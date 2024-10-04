package de.einkampflama.LLamaNet.api.socket.server;

import java.net.Socket;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public interface LLClientHandler {
    /**
     * @implNote don't forget to close the socket
     * @param clientSocket
     */
    void handle(Socket clientSocket);

    default void synchronizedHandle(Socket clientSocket) {
        synchronized(this) {
            handle(clientSocket);
        }
    }
}

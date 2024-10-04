package de.einkampflama.LLamaNet;

import de.einkampflama.LLamaNet.api.socketserver.impl.LLClientHandlerImpl;
import de.einkampflama.LLamaNet.api.socketserver.server.LLServerSocket;
import de.einkampflama.LLamaNet.logging.Logger;
import de.einkampflama.LLamaNet.logging.LoggerImpl;

import java.io.IOException;

public class LLamaNet {
    private Logger logger;

    public LLamaNet(){
        this.logger = new LoggerImpl();
    }

    public Logger getLogger() {
        return logger;
    }

    //Testing stuff
    public static void main(String[] args) {
        LLamaNet t = new LLamaNet();
        LLServerSocket s = null;
        try {
            s = new LLServerSocket(t, 9999);
            s.setAcceptClients(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            s.listen(new LLClientHandlerImpl(t));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

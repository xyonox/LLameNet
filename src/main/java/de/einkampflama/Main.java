package de.einkampflama;

import de.einkampflama.LLamaNet.LLamaNet;
import de.einkampflama.LLamaNet.api.socketserver.client.LLClient;
import de.einkampflama.LLamaNet.logging.LoggerImpl;

public class Main {
    public static void main(String[] args) {
        new LLClient(new LLamaNet(), "localhost", 9999);
    }
}
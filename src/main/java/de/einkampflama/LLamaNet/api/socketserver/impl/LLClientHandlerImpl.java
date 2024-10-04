package de.einkampflama.LLamaNet.api.socketserver.impl;

import de.einkampflama.LLamaNet.LLamaNet;
import de.einkampflama.LLamaNet.api.socketserver.server.LLClientHandler;
import de.einkampflama.LLamaNet.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LLClientHandlerImpl implements LLClientHandler {
    private Logger logger;
    private final LLamaNet lLamaNet;

    public LLClientHandlerImpl(LLamaNet lLamaNet) {
        this.lLamaNet = lLamaNet;
        this.logger = lLamaNet.getLogger();
    }


    @Override
    public void handle(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("message from client: " + message);

                if ("END".equals(message)) {
                    System.out.println("Client ended the connection");
                    break;
                }


                out.println("Server hat empfangen: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

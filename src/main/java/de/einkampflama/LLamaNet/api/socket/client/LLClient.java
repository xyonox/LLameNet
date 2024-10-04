package de.einkampflama.LLamaNet.api.socket.client;

import de.einkampflama.LLamaNet.LLamaNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * EXAMPLE CLIENT `testing`
 */
public class LLClient {
    private final LLamaNet lLamaNet;

    private Socket socket;

    public LLClient(LLamaNet lLamaNet ,String host, int port){
        this.lLamaNet = lLamaNet;
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {


            String userInput;
            String serverResponse;

            while (true) {
                userInput = console.readLine();

                out.println(userInput);

                if ("END".equalsIgnoreCase(userInput)) {
                    break;
                }

                serverResponse = in.readLine();
                System.out.println(serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

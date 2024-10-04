package de.einkampflama.LLamaNet.api.socketserver.server;

import de.einkampflama.LLamaNet.LLamaNet;
import de.einkampflama.LLamaNet.api.LLController;
import de.einkampflama.LLamaNet.logging.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main Server for clients
 *
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public class LLServerSocket extends ServerSocket implements LLController {

    private boolean acceptClients = false;
    private final LLamaNet lLamaNet;
    private Logger logger;

    /**
     * Create Server Socket (only port)
     *
     * @param port
     * @throws IOException
     */
    public LLServerSocket(LLamaNet lLamaNet, int port) throws IOException {
        super(port);
        this.lLamaNet = lLamaNet;
        this.logger = lLamaNet.getLogger();
        start(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Create Server Socket (port and backlog count)
     *
     * @param port
     * @throws IOException
     */
    public LLServerSocket(LLamaNet lLamaNet, int port, int backlog) throws IOException {
        super(port, backlog);
        this.lLamaNet = lLamaNet;
        this.logger = lLamaNet.getLogger();
        start(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Create Server Socket (port, backlog count and binding address)
     *
     * @param port
     * @throws IOException
     */
    public LLServerSocket(LLamaNet lLamaNet, int port, int backlog, InetAddress bindAddr) throws IOException {
        super(port, backlog, bindAddr);
        this.lLamaNet = lLamaNet;
        this.logger = lLamaNet.getLogger();
        start(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Listing client -> send the client to the LLClientHandler
     *
     * @param handler
     * @throws IOException
     */
    public void listen(LLClientHandler handler) throws IOException {
        if(acceptClients)
            while (acceptClients){
                Socket clientSocket = this.accept();
                handler.synchronizedHandle(clientSocket);
             }
        else {
            logger.notAllowed("Boolean (acceptClients | form LLServerSocket) don't allow to listen clients.");
        }
    }

    /**
     * Overwrite accept() - adding log info
     *
     * @return
     * @throws IOException
     */
    @Override
    public Socket accept() throws IOException {
        Socket clientSocket = super.accept();

        logger.info("New client connected - Inet > " + clientSocket.getInetAddress() +
                " LocalAddress > " + clientSocket.getLocalAddress()
        );

        return clientSocket;
    }

    /**
     * Close event
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        super.close();
        stop(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @Override
    public void start(String time) {

    }

    @Override
    public void stop(String time) {
        setAcceptClients(false);
    }

    @Override
    public void restart(String time) {

    }

    public void setAcceptClients(boolean acceptClients) {
        this.acceptClients = acceptClients;
    }

    public boolean isAcceptClients() {
        return acceptClients;
    }
}
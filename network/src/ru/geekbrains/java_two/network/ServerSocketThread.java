package ru.geekbrains.java_two.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocketThread extends Thread {
    ServerSocketThreadListener listener;
    private int port;
    private int timeout;

    public ServerSocketThread(ServerSocketThreadListener listener, String name, int port, int timeout) {
        super(name);
        this.port = port;
        this.timeout = timeout;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        listener.onServerStart(this);
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(timeout);
            listener.onServerSocketCreated(this, server);
            while (!isInterrupted()) {
                Socket socket = null;
                try {
                    socket = server.accept();
                    listener.onSocketAccepted(this, server, socket);
                } catch (SocketTimeoutException e) {
                    listener.onServerTimeout(this, server);
                }
            }
        } catch (IOException e) {
            listener.onServerException(this, e);
        } finally {
            listener.onServerStop(this);
        }
    }
}

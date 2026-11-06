
package com.syleera.sobhana11.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;

    private final ExecutorService threadPool =
            Executors.newCachedThreadPool();

    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public void start() {

        try {

            serverSocket = new ServerSocket(port);

            System.out.println("===============================");
            System.out.println("    ELEVATOR SYSTEM SERVER");
            System.out.println("===============================");
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for clients...\n");

            while (!serverSocket.isClosed()) {

                Socket client = serverSocket.accept();

                System.out.println(
                        "[+] Client connected: " +
                        client.getRemoteSocketAddress()
                );

                ClientHandler handler = new ClientHandler(client);

                threadPool.submit(handler);
            }

        } catch (IOException e) {

            if (!serverSocket.isClosed()) {
                System.err.println("Server error: " + e.getMessage());
            }

        } finally {

            threadPool.shutdown();
        }
    }

    public void stop() {

        try {

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

        } catch (IOException e) {

            System.err.println("Could not stop server: " + e.getMessage());

        } finally {

            threadPool.shutdown();
        }
    }
}

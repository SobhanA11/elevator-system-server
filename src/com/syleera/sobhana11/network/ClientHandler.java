package com.syleera.sobhana11.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (
                Socket socket = client;

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(), true
                )
        ) {
            String message;

            while ((message = input.readLine()) != null) {
                System.out.println(
                        "[Client " + socket.getRemoteSocketAddress()
                                + "] " + message
                );

                output.println("Server received: " + message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println(
                    "Client connection error: " + e.getMessage()
            );

        } finally {
            System.out.println(
                    "[-] Client disconnected: " +
                    client.getRemoteSocketAddress()
            );
        }
    }
}

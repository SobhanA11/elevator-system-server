package com.syleera.sobhana11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("===============================");
            System.out.println("    ELEVATOR SYSTEM SERVER");
            System.out.println("===============================");
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...\n");

            while (true) {
                // Wait for a new client
                Socket client = server.accept();
                System.out.println(
                        "[+] Client connected: " +
                        client.getRemoteSocketAddress()
                );
                // Handle each client independently
                threadPool.submit(() -> handleClient(client));
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    private static void handleClient(Socket client) {
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

            // Keep listening to this client
            while ((message = input.readLine()) != null) {

                System.out.println(
                        "[Client " + socket.getRemoteSocketAddress()
                                + "] " + message
                );

                // Send response
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

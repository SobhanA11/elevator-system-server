
package com.syleera.sobhana11;

import com.syleera.sobhana11.network.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(8080);

        server.start();
    }
}

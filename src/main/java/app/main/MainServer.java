package app.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class MainServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        try (ServerSocket s = new ServerSocket(5050)) {
            while (true) {
                Socket incoming = s.accept();
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
            }
        }

    }

}

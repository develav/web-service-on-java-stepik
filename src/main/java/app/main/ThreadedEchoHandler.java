package app.main;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ThreadedEchoHandler implements Runnable {

    private static final String bye = "Bye.";
    private final Socket incoming;

    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {

        try (InputStream inputStream = incoming.getInputStream();
             OutputStream outputStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inputStream, StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

            boolean done = false;

            while (!done) {
                String line = in.nextLine();
                if (line.trim().equals("Bye.")) {
                    done = true;
                }
                out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

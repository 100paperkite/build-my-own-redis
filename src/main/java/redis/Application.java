package redis;

import redis.protocol.RESP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {
    public static void main(String[] args) {
        Socket clientSocket = null;
        int port = 6379;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);

            while (true) {
                clientSocket = serverSocket.accept();

                InputStream inputStream = clientSocket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // TODO: parsing input
                while (reader.ready() && reader.readLine() != null) ;

                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(RESP.SIMPLE_STRING.message("PONG").getBytes());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

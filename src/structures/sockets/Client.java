package structures.sockets;

import structures.sockets.interfaces.Listener;
import structures.sockets.interfaces.Sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client implements Sender {
    Socket connection;
    PrintWriter output;
    BufferedReader input;

    Listener listener;
    Thread listenerThread;

    public Client(String address, Listener listener) {
        this.listener = listener;
        Sender sender = this;

        String host = "localhost";
        int port = 20000;

        if (address.contains(":")) {
            String[] hostAndPort = address.split(":");
            address = hostAndPort[0];
            port = Integer.parseInt(hostAndPort[1]);
        }
        if (address.length() > 0) {
            host = address;
        }

        System.out.println("Establishing a connecting with " + host + ":" + port);

        try {
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            listenerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (connection.isConnected()) {
                        try {
                            String message = input.readLine();
                            listener.message(message, sender);
                        } catch (Exception err) {
                            System.err.println("Failed to receive message:");
                            err.printStackTrace();
                            break;
                        }
                    }
                }
            });
            listenerThread.start();
        } catch (Exception err) {
            System.err.println("Failed to connect" + err);
        }
    }

    private boolean isConnected() {
        return connection.isConnected()
                && output != null
                && input != null;
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                input = null;
                output = null;
                connection.close();
            } catch (Exception err) {
                System.err.println("Failed to close connection:");
                err.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String msg) {
        if (isConnected()) {
            output.println(msg);
        }
    }
}

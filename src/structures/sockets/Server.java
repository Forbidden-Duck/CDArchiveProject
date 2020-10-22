package structures.sockets;

import structures.sockets.interfaces.Listener;
import structures.sockets.interfaces.Sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Sender {
    ServerSocket server;
    Listener listener;
    ArrayList<ServerClient> clients;

    public Server(int port, Listener listener) {
        this.listener = listener;
        Sender sender = this;
        clients = new ArrayList<>();

        try {
            server = new ServerSocket(port);
            System.out.println("Listening on: " + port);
            listener.ready();

            boolean isRunning = true;
            while (isRunning) {
                ServerClient client = new ServerClient(server.accept());
                clients.add(client);
                System.out.println("New client on " + client.connection.getInetAddress().getHostAddress());

                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Start client listener...");
                        while (client.connection.isConnected()) {
                            try {
                                listener.message(client.input.readLine(), sender);
                            } catch (Exception err) {
                                System.err.println("Failed to read message:");
                                err.printStackTrace();
                            }
                        }
                    }
                })).start();
            }
        } catch (Exception err) {
            System.err.println("Failed to listen for connections:");
            err.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String msg) {
        for (ServerClient client : clients) {
            if (client.connection.isClosed()) {
                clients.remove(client);
            } else {
                client.output.println(msg);
            }
        }
    }

    class ServerClient {
        Socket connection;
        PrintWriter output;
        BufferedReader input;

        private ServerClient(Socket connection) {
            this.connection = connection;
            try {
                output = new PrintWriter(connection.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (Exception err) {
                System.err.println("Failed to create client:");
                err.printStackTrace();
            }
        }
    }
}

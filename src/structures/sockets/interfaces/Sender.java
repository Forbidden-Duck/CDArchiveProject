package structures.sockets.interfaces;

public interface Sender {
    /**
     * Send a message to the server
     * @param msg Message being sent
     */
    void sendMessage(String msg);
}

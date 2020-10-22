package structures.sockets.interfaces;

public interface Listener {
    /**
     * Emits when the server is ready
     */
    void ready();

    /**
     * Emits when the server receives a message
     * @param msg Message being sent
     * @param sender Sender who sent the message
     */
    void message(String msg, Sender sender);
}

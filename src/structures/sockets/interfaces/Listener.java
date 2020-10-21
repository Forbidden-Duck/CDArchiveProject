package structures.sockets.interfaces;

public interface Listener {
    void message(String msg, Sender sender);
}

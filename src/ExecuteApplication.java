import structures.sockets.Server;
import structures.sockets.interfaces.Listener;
import structures.sockets.interfaces.Sender;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Executes and runs the program
 */
public class ExecuteApplication {
    public ExecuteApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new views.Main.MainDisplay();
            }
        });

        new Server(20000, new Listener() {
            @Override
            public void ready() {

            }

            @Override
            public void message(String msg, Sender sender) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                sender.sendMessage(
                        "[" + sdfDate.format(now) + "] : " + msg
                );
            }
        });
    }

    public static void main(String[] args) {
        new ExecuteApplication();
    }
}

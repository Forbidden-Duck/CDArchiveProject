import javax.swing.*;

/**
 * Executes and runs the program
 */
public class ExecuteApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new views.Main.MainDisplay();
            }
        });
    }
}

package views.Automation;

import structures.data.CD;
import structures.sockets.Client;
import structures.sockets.interfaces.Listener;
import structures.sockets.interfaces.Sender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainEvents {
    Client client;

    /**
     * Creates a new Event Manager for Automation Console
     */
    public MainEvents() {
    }

    //region Button Events

    //region Top Level

    private JTextField txtBarcode = null;

    /**
     * Display the barcode in the field
     * @param cd
     */
    void DisplayBarcode(CD cd) {
        txtBarcode.setText(cd.getBarcode() + "");
    }

    /**
     * Clear the barcode field
     */
    void ClearBarcode() {
        txtBarcode.setText("");
    }

    /**
     * Save the barcode field for later editing
     * @param barcode
     */
    void SaveFieldComponent(JTextField barcode) {
        this.txtBarcode = barcode;
    }

    /**
     * Process the provided CD/CDs with the action
     * @param index Index of the action
     * @param action Name of the action
     * @param table Table associated with the display
     */
    void Process(int index, String action, JTable table) {
        // <= 3 SINGLE CD
        // > 3 TABLE
        if (index <= 3) {
            CD selected;
            if (table.getRowCount() < 1) {
                JOptionPane.showMessageDialog(
                        null,
                        "There are no CDs to process",
                        "Process",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (txtBarcode.getText().length() < 1) {
                if (table.getRowCount() == 1) {
                    selected = MainEvents.ConvertRowToCD(0, table);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No CD selected",
                            "Process",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } else {
                selected = MainEvents.BarcodeToCD(txtBarcode.getText(), table);
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to find CD to process",
                        "Process",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Send(action, "Item = " + selected.getBarcode());
            CDProcess(action, selected);
        } else {
            List<CD> cds = new ArrayList<>();
            int rowCount = table.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                cds.add(MainEvents.ConvertRowToCD(i, table));
            }
            Send(action, "Items = " + cds.size());
            CDsProcess(action, Arrays.copyOf(cds.toArray(), cds.size(), CD[].class));
        }
    }

    /**
     * Process a CD
     * @param action Name of the action
     * @param cd CD being processed
     */
    void CDProcess(String action, CD cd) {
        String content =
                "Robotic arm is being prepared to process. Follow prompts on screen done by the robot\n" +
                        "Please allow the Robotic arm to finish before going near the arm\n\n" +
                        "Action: " + action + "\nTitle: " + cd.getTitle() + "\nBarcode: " + cd.getBarcode();
        JOptionPane.showMessageDialog(
                null,
                content,
                "Process",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Process a collection of CDs
     * @param action Name of the action
     * @param cds CDs being processed
     */
    void CDsProcess(String action, CD[] cds) {
        String content =
                "Robotic arm is being prepared to process. Follow prompts on screen done by the robot\n" +
                        "Please allow the Robotic arm to finish before going near the arm\n\n" +
                        "Action: " + action + "\nAmount of CDs: " + cds.length;
        JOptionPane.showMessageDialog(
                null,
                content,
                "Process",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //endregion

    /**
     * Disposes of the provided Frame
     * @param frame
     */
    static void Exit(JFrame frame) {
        frame.dispose();
    }

    //endregion

    //region Table Events

    private JTable tblCDs = null;

    /**
     * Load a collection of CDs into a new table
     * @param cds
     * @return
     */
    JTable LoadIntoTable(CD[] cds) {
        JTable table = new JTable(0, 0);
        DefaultTableModel dm = (DefaultTableModel) table.getModel();

        // Add Columns
        dm.addColumn("Title");
        dm.addColumn("Author");
        dm.addColumn("Section");
        dm.addColumn("X");
        dm.addColumn("Y");
        dm.addColumn("Barcode");
        dm.addColumn("Description");

        // Add Rows
        for (int i = 0; i < cds.length; i++) {
            CD cd = cds[i];
            dm.addRow(new Object[]{
                    cd.getTitle(),
                    cd.getAuthor(),
                    cd.getSection(),
                    Integer.toString(cd.getXAxis()),
                    Integer.toString(cd.getYAxis()),
                    Integer.toString(cd.getBarcode()),
                    cd.getDescription(),
                    Boolean.toString(cd.getOnLoan())
            });
        }

        return table;
    }

    /**
     * Displays the selected row in the table
     * @param row
     * @param table
     */
    void RowSelectionUpdate(int row, JTable table) {
        CD cd = this.ConvertRowToCD(row, table);
        this.DisplayBarcode(cd);
    }

    /**
     * Save the table for later editing
     * @param table
     */
    void SaveTableComponent(JTable table) {
        this.tblCDs = table;
    }

    /**
     * Return the table component
     * @return
     */
    JTable GetTableComponent() {
        return this.tblCDs;
    }

    /**
     * Convert a Row into a CD
     * @param row
     * @param table
     * @return
     */
    static CD ConvertRowToCD(int row, JTable table) {
        String title = (String) table.getValueAt(row, 0);
        String author = (String) table.getValueAt(row, 1);
        String section = (String) table.getValueAt(row, 2);
        int xAxis = Integer.parseInt((String) table.getValueAt(row, 3));
        int yAxis = Integer.parseInt((String) table.getValueAt(row, 4));
        int barcode = Integer.parseInt((String) table.getValueAt(row, 5));
        String description = (String) table.getValueAt(row, 6);

        return new CD(
                title, author,
                description, section,
                xAxis, yAxis,
                barcode, false
        );
    }

    /**
     * Convert a barcode to a CD using a table
     * @param barcode
     * @param table
     * @return
     */
    static CD BarcodeToCD(String barcode, JTable table) {
        CD selected = null;
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (table.getValueAt(i, 5).equals(barcode)) {
                selected = MainEvents.ConvertRowToCD(i, table);
                break;
            }
        }
        return selected;
    }

    //endregion

    //region Combobox Events

    static String[] defaultActions() {
        return new String[]{
                "Add",
                "Retrieve",
                "Return",
                "Remove"
        };
    }

    static String[] allActions() {
        String[] defaults = MainEvents.defaultActions();
        String[] all = new String[7];

        for (int i = 0; i < defaults.length; i++) {
            all[i] = defaults[i];
        }
        all[4] = "Random Sort";
        all[5] = "Mostly Sort";
        all[6] = "Reverse Sort";
        return all;
    }

    //endregion

    //region Socket Events

    /**
     * Connections to the Server with a new Client
     */
    void Connect() {
        client = new Client("", new Listener() {
            @Override
            public void ready() {

            }

            @Override
            public void message(String msg, Sender sender) {

            }
        });
    }

    /**
     * Sends a predetermined message
     * @param action Action being processed
     * @param barcode Barcode of the item OR size of the collection
     */
    void Send(String action, String barcode) {
        client.sendMessage("Processing : " + "Action = " + action + " : " + barcode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception err) {
                    SendCustom(action, barcode, "Failed to Process");
                    return;
                }
                SendCustom(action, barcode, "Processed");
            }
        }).start();
    }

    /**
     * Sends a custom message in replace of the predetermined
     * @param action Action being processed
     * @param barcode Barcode of the item OR size of the collection
     * @param message Message to send
     */
    private void SendCustom(String action, String barcode, String message) {
        client.sendMessage(message + ": " + "Action = " + action + " : " + barcode);
    }

    //endregion
}

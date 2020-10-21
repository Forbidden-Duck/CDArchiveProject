package views.Automation;

import structures.data.CD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainEvents {
    public MainEvents() {
    }

    //region Button Events

    //region Top Level

    private JTextField txtBarcode = null;

    void DisplayBarcode(CD cd) {
        txtBarcode.setText(cd.getBarcode() + "");
    }

    void ClearBarcode() {
        txtBarcode.setText("");
    }

    void SaveFieldComponent(JTextField barcode) {
        this.txtBarcode = barcode;
    }

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
            CDProcess(action, selected);
        } else {
            List<CD> cds = new ArrayList<>();
            int rowCount = table.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                cds.add(MainEvents.ConvertRowToCD(i, table));
            }
            CDsProcess(action, Arrays.copyOf(cds.toArray(), cds.size(), CD[].class));
        }
    }

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

    static void Exit(JFrame frame) {
        frame.dispose();
    }

    //endregion

    //region Table Events

    private JTable tblCDs = null;

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

    void RowSelectionUpdate(int row, JTable table) {
        CD cd = this.ConvertRowToCD(row, table);
        this.DisplayBarcode(cd);
    }

    void SaveTableComponent(JTable table) {
        this.tblCDs = table;
    }

    JTable GetTableComponent() {
        return this.tblCDs;
    }

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
}

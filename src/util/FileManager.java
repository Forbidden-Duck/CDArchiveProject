package util;

import structures.data.CD;
import structures.lists.DoublyLinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Manages the lines of a file
 */
public class FileManager {
    String fileName;

    /**
     * Files are/should be located at the directory of the program
     * File will/must be formatted in
     * Title;Author;Section;X;Y;Barcode;Description;OnLoan
     *
     * @param fileName Name of the file (Will be created if not exists)
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Load each line into a DoublyLinkedList<CD>
     *
     * @return DoublyLinkedList<CD> cds
     */
    public DoublyLinkedList<CD> getCDs() {
        DoublyLinkedList<CD> cds = new DoublyLinkedList<>();
        boolean skipFirst = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if (skipFirst == false) {
                    skipFirst = true;
                } else {
                    String[] lineSplit = currentLine.split(";");
                    CD cd = new CD(
                            lineSplit[0], lineSplit[1],
                            lineSplit[6], lineSplit[2],
                            Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]),
                            Integer.parseInt(lineSplit[5]), Boolean.parseBoolean(lineSplit[7])
                    );
                    cds.append(new DoublyLinkedList.Node(cd));
                }
            }
            br.close();
        } catch (Exception err) {
            System.out.println("Error Reading File: ");
            err.printStackTrace();
        }
        return cds;
    }

    /**
     * Read the first line of the file as columns
     *
     * @return String[] columns
     */
    public String[] getColumns() {
        String[] columns = new String[0];

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String currentLine;

            if ((currentLine = br.readLine()) != null) {
                columns = currentLine.split(";");
            }
            br.close();
        } catch (Exception err) {
            System.out.println("Error Reading File: ");
            err.printStackTrace();
        }
        return columns;
    }

    /**
     * Write the rows within a table into the provided file
     *
     * @param table
     */
    public void tableToFile(JTable table) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            // Write Column Headings
            bw.write("Title;Author;Section;X;Y;BarCode;Description;OnLoan");
            bw.write("\n");

            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    bw.write(table.getValueAt(row, col) + (col == columnCount - 1 ? "" : ";"));
                }
                bw.write(row == rowCount - 1 ? "" : "\n");
            }
            bw.close();
        } catch (Exception err) {
            System.out.println("Error Writing To File:");
            err.printStackTrace();
        }
    }

    /**
     * Load the lines into the JTable
     *
     * @returns JTable table
     */
    public JTable loadIntoJTable() {
        JTable table = new JTable(0, 0);
        DoublyLinkedList.Node[] cds = this.getCDs().toArray();
        DefaultTableModel dm = (DefaultTableModel) table.getModel();

        // Add Columns
        String[] columns = this.getColumns();
        for (int i = 0; i < columns.length; i++) {
            dm.addColumn(columns[i]);
        }
        // Add Rows
        for (int i = 0; i < cds.length; i++) {
            CD cd = (CD) cds[i].getData();
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
     * Load the lines into the JTable
     *
     * @param table Table to load into
     */
    public void loadIntoJTable(JTable table) {
        DoublyLinkedList.Node[] cds = this.getCDs().toArray();
        DefaultTableModel dm = (DefaultTableModel) table.getModel();


        // Remove Rows
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
        if (table.getColumnCount() < 1) {
            // Add Columns
            String[] columns = this.getColumns();
            for (int i = 0; i < columns.length; i++) {
                dm.addColumn(columns[i]);
            }
        }
        // Add Rows
        for (int i = 0; i < cds.length; i++) {
            CD cd = (CD) cds[i].getData();
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
    }
}

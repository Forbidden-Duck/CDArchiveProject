package structures.sorting;

import java.util.List;

import structures.data.CD;

public class Quick {
    /**
     * Sort a List of CDs using the Quick Sort Algorithm
     * By Barcode
     *
     * @param cds
     */
    public static void sortCDsByBarcode(List<CD> cds) {
        sortCDsByBarcode(cds, 0, cds.size() - 1);
    }

    private static void sortCDsByBarcode(List<CD> cds, int low, int high) {
        if (low < high) {
            int index = partitionByBarcode(cds, low, high);
            sortCDsByBarcode(cds, low, index - 1);
            sortCDsByBarcode(cds, index + 1, high);
        }
    }

    private static int partitionByBarcode(List<CD> cds, int low, int high) {
        CD pivot = cds.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (pivot.getBarcode() > cds.get(j).getBarcode()) {
                i++;
                CD temp = cds.get(i);
                cds.set(i, cds.get(j));
                cds.set(j, temp);
            }
        }
        CD temp = cds.get(i + 1);
        cds.set(i + 1, cds.get(high));
        cds.set(high, temp);
        return i + 1;
    }
}

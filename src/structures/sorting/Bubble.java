package structures.sorting;

import java.util.List;

import structures.data.CD;

public class Bubble {
    /**
     * Sort a List of CDs using the Bubble Sort Algorithm
     * By Title
     *
     * @param cds
     */
    public static void sortCDsByTitle(List<CD> cds) {
        int lastSwap = cds.size() - 1;
        for (int i = 0; i < cds.size(); i++) {
            boolean isSorted = true;
            int currentSwap = -1;
            for (int j = 0; j < lastSwap; j++) {
                CD cd1 = cds.get(j);
                CD cd2 = cds.get(j + 1);
                if (cd1.getTitle().compareToIgnoreCase(cd2.getTitle()) > 0) {
                    cds.set(j, cd2);
                    cds.set(j + 1, cd1);
                    isSorted = false;
                    currentSwap = j + 1;
                }
            }
            if (isSorted) {
                return;
            }
            lastSwap = currentSwap;
        }
    }
}

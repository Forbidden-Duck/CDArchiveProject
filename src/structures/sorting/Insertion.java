package structures.sorting;

import java.util.List;

import structures.data.CD;

public class Insertion {
    /**
     * Sort a List of CDs using the Insertion Sort Algorithm
     * By Author
     *
     * @param cds
     */
    public static void sortCDsByAuthor(List<CD> cds) {
        for (int i = 1; i < cds.size(); i++) {
            CD indexRecord = cds.get(i);
            int previousIndex = i - 1;
            while (previousIndex >= 0
                    && cds.get(previousIndex).getAuthor().compareToIgnoreCase(indexRecord.getAuthor()) > 0) {
                cds.set(previousIndex + 1, cds.get(previousIndex));
                previousIndex--;
            }
            cds.set(previousIndex + 1, indexRecord);
        }
    }
}

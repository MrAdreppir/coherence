import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daan on 02-Dec-16.
 */
public class SubsetIterator<E> {
    private final List<E> set;
    private final int max;
    private int index;

    public SubsetIterator(List<E> originalList) {
        set = originalList;
        // Shift 1 (binary) left with value set.size(), e.g 1 << 4 = b10000 = 16
        // This is how many subgroups there are
        max = (1 << set.size());
        //Alternative
        //max = (int) Math.pow(2, set.size());//2^set.size();
        index = 0;
    }

    public boolean hasNext() {
        return index < max;
    }

    /**
     * Returns the next subset
     * @return a list with the next subset
     */
    public ArrayList<E> next() {
        ArrayList<E> newSet = new ArrayList<E>();
        int flag = 1;
        for(E element : set) {
            // AND the index and flag, if it's not null
            // We want that element, i.e. for every 1 in the index we want the element
            if ((index & flag) != 0) {
                newSet.add(element);
            }
            // Shift one bit to the left
            flag <<= 1;
        }
        // Index grows by one, e.g. 1100 to 1101, we get all possible combinations from 0 to max
        index++;
        return newSet;
    }

}

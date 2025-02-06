package ub.cse.algo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Child class of {@link ArrayList}. A resizable-array implementation of a
 * {@link Marriage} list.
 */
@SuppressWarnings("serial")
public class Matching extends ArrayList<Marriage> implements Comparable<Matching> {

    public Matching() {
        super();
    }

    /**
     * Adds all marriages in an {@link ArrayList} into a new Matchings object.
     * 
     * @param marriages An {@link ArrayList} of marriages
     */
    public Matching(ArrayList<Marriage> marriages) {
        this.addAll(marriages);
    }

    @Override
    public boolean equals(Object obj) {
        Matching other = (Matching) obj;
        if (this.size() != other.size())
            return false;
        //Sort by women
        Collections.sort(this);
        Collections.sort(other);
        //Iterate over both lists and check if the pairs match
        for (int marriage = 0; marriage < this.size(); marriage++) {
            if (!this.get(marriage).equals(other.get(marriage))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int compareTo(Matching other) {
        //Sort by women
        Collections.sort(this);
        Collections.sort(other);
        for (int i = 0; i < this.size(); i++) {
            Integer man = get(i).man;
            Integer otherMan = other.get(i).man;
            //Look for the first man who is smaller
            if (!man.equals(otherMan)) {
                return man.compareTo(otherMan);
            }
        }
        return 0;
    }
}

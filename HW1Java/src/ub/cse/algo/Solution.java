package ub.cse.algo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * For use in CSE 331 HW1.
 * This is the class you will be editing and turning in. It will be timed against our implementation
 * NOTE that if you declare this file to be in a package, it will not compile in Autolab
 */
public class Solution {

    private int numberOfMenAndWomen;


    // The following represent the preference list for the men and women.
    // The KEY represents the integer representation of a given man or woman.
    // The VALUE is a list, from most preferred to least, of the member of the opposite gender.
    private HashMap<Integer, ArrayList<Integer>> men;
    private HashMap<Integer, ArrayList<Integer>> women;
    private ArrayList<Matching> stableMatchings = new ArrayList<>();

    /**
     * The constructor simply sets up the necessary data structures.
     * The grader for the homework will first call this class and pass the necessary variables.
     * There is no need to edit this constructor.
     *
     * @param n The number of men/women.
     * @param men A map linking each man (integer value) with their preference list.
     * @param women A map linking each woman (integer value) with their preference list.
     */
    public Solution(int n, HashMap<Integer, ArrayList<Integer>> men, HashMap<Integer, ArrayList<Integer>> women){
        this.numberOfMenAndWomen = n;
        this.men = men;
        this.women = women;
    }

    /**
     * This method must be filled in by you. You may add other methods and subclasses as you see fit,
     * but they must remain within the Solution class.
     * @return Your set of stable matches. Order does not matter.
     */
    public ArrayList<Matching> outputStableMatchings() {

            /* The code below just calls the allPermutations function, and thenjust prints all permutattions*/
            /* To compare your code's output with the sample outpout you need to comment out the part about printing the permutations*/

            ArrayList<ArrayList<Integer>> listOfAllPermutations = new ArrayList<>();
            listOfAllPermutations = allPermutations(numberOfMenAndWomen);

            System.out.println("----------------------------");
            System.out.println("Printing all possible permutations of [1,2,...n] for n ="+numberOfMenAndWomen);
            System.out.println("Total number of permutation generated ="+listOfAllPermutations.size());
            System.out.println("----------------------------");
            for(ArrayList<Integer> set : listOfAllPermutations){
                System.out.println(set);
            }

        System.out.println("Preference List");
        System.out.println("----------------------------");
        /*for (int i = 1; i <= numberOfMenAndWomen; i++) {
            if (women.containsKey(i) && men.containsKey(i))
            {
                System.out.println("Woman " + (i) + " Preferences: " + women.get(i));
                System.out.println("Man " + (i) + " Preferences: " + men.get(i));
            }
        }

         */
        System.out.println("\nAll Permutations");
            /*allPermutations call done*/

        ArrayList<ArrayList<Marriage>> unstable = new ArrayList<>();
        ArrayList<Marriage> unstableMarriage = new ArrayList<>();

        for (int x = 0; x < listOfAllPermutations.size(); x++) { // O(n!)
            ArrayList<Marriage> set = new ArrayList<>();
            for (int y = 1; y <= numberOfMenAndWomen; y++ ) {
                set.add(new Marriage(listOfAllPermutations.get(x).get(y-1), y));
            }
            System.out.println(set);

            boolean breakCheck = false;

            //Go through set and see if there is any woman higher on a man's preference list that also has
            //that man higher on their preference list.
            //if there is then classify this set as unstable
            for (int i = 0; i < set.size(); i++) { //O(n)
                if (breakCheck)
                    break;
                int currMan = set.get(i).man;
                int currWoman = set.get(i).woman;

                for (Marriage match : set) { //checks current set to see if it has an unstable match before doing other processes
                    if (unstableMarriage.contains(match)) {
                        System.out.println(set.get(i));
                        unstable.add(set);
                        breakCheck = true;
                        break;
                    }
                }

                for (Integer pref : men.get(currMan)) { //O(n)
                    if (breakCheck)
                        break;

                    //System.out.println("Current Man: " + currMan);
                    //System.out.println("Current Woman: " + currWoman);

                    if (!pref.equals(currWoman))
                    {
                        int newWoman = pref;
                        //System.out.println("man pref: " + newWoman);


                        ///THIS LOGIC IS FALSE\\\
                        int m = set.get(newWoman - 1).man;
                        //System.out.println("woman pref: " + m);
                        ArrayList<Integer> wPref = women.get(newWoman);
                        for (Integer integer : wPref) { //O(n)
                            if (integer == m) {
                                break;
                            } else if (integer == currMan) {
                                //System.out.println("mNum: " + mNum);
                                unstable.add(set);
                                unstableMarriage.add(set.get(i));
                                breakCheck = true;
                                break;
                            }
                        }


                    }
                    else
                    {
                        break;
                    }
                }


            }

            if (!unstable.isEmpty() && !unstable.contains(set)) {
                stableMatchings.add(new Matching(set));
            }
        }


        /*for (ArrayList<Marriage> set : marriages) {
            System.out.println(set);

            boolean breakCheck = false;

            //Go through set and see if there is any woman higher on a man's preference list that also has
            //that man higher on their preference list.
            //if there is then classify this set as unstable
            for (int i = 0; i < set.size(); i++) {
                if (breakCheck)
                    break;
                int currMan = set.get(i).man;
                int currWoman = set.get(i).woman;

                for (Integer pref : men.get(currMan)) {
                    if (breakCheck)
                        break;

                    //System.out.println("Current Man: " + currMan);
                    //System.out.println("Current Woman: " + currWoman);

                    if (!pref.equals(currWoman))
                    {
                        int newWoman = pref;
                        //System.out.println("man pref: " + newWoman);

                        for (int j = 0; j < set.size(); j++) {
                            if (breakCheck)
                                break;
                            if (j != i) {
                                if (set.get(j).woman == newWoman) {
                                    ///THIS LOGIC IS FALSE\\\
                                    int m = set.get(j).man;
                                    //System.out.println("woman pref: " + m);
                                    int mNum = -1;
                                    ArrayList<Integer> wPref = women.get(newWoman);
                                    for (int w = 0; w < wPref.size(); w++) {
                                        if (wPref.get(w) == m) {
                                            mNum = w;
                                        } else if (wPref.get(w) == currMan) {
                                            if (mNum == -1) {
                                                //System.out.println("mNum: " + mNum);
                                                unstable.add(set);
                                                breakCheck = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        break;
                    }
                }


            }

            if (!unstable.isEmpty() && !unstable.contains(set)) {
                stableMatchings.add(new Matching(set));
            }

        }

        System.out.println("----------------------------");

         */





        return stableMatchings;
    }

    /**
     * Generates all permutations.
     * Just a wrapper function to call permutate
     */
     private ArrayList<ArrayList<Integer>> allPermutations(int n){
            ArrayList<Integer> start = new ArrayList<Integer>();
            for(int k = 1; k<=numberOfMenAndWomen; ++k) {
                start.add(k);
            }
            ArrayList<ArrayList<Integer>> allPermuts= new ArrayList<>();
            permutate(start,allPermuts,n); // Once this call returns the list of all permutations will be in "allPermuts"

            return allPermuts;
     }

    /**
     * This method generates all of the permutations of the input for you.
     * Implements Jeap's algorithm.
     * @param set A complete matching set, not necesarrily stable
     * @param listOfPermut Current of of all opermutations that have been generated so far. This would be updated by ref
     * @param length length of the set
     */
    private void permutate(ArrayList<Integer> set, ArrayList<ArrayList<Integer>> listOfPermut, int length){
        if(length == 1){
            //System.out.println(set);
            //Have to deep copy the current matching so that next call of Heap's does not over-write the current matching
            ArrayList<Integer> cloneSet = new ArrayList<>();
            for(int i = 0; i < set.size(); i++){
                cloneSet.add(set.get(i));
            }
            listOfPermut.add(cloneSet); 
        }
        else{
            for(int i = 0; i < length; i++){
                permutate(set, listOfPermut, length - 1);
                int j = (length % 2 == 0 ) ? i : 0;
                Integer t = set.get(length-1);
                set.set(length-1, set.get(j));
                set.set(j, t);
            }
        }
    }

}

package com.knapsack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        ArrayList<Bag> Bags = new ArrayList<Bag>();
        BitSet DecisionResult = new BitSet();
        BruteForceSolver bfs = new BruteForceSolver();
        int bit_iterator = 0;
        for(int i=1; i<501; i++){
            Bag bag = new Bag();
            bag.readInstance(args[0], i);
            bag.readCorrectResult(args[1], i);
            Bags.add(bag);
        }
        for (Bag b: Bags) {
            b.BagSolution=bfs.SolveSmarter(b);
            b.checkSolution();
            if(b.MinPrice<=b.BagSolution.Price){DecisionResult.set(bit_iterator);}
            bit_iterator++;
        }
        System.out.println("********************   File: " + args[0] + " finished.  ********************");
       // System.out.println(DecisionResult.toString());
    }
}

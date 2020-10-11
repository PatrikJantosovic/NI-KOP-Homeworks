package com.knapsack;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Bag> Bags = new ArrayList<Bag>();
        BruteForceSolver bfs = new BruteForceSolver();
        BBSolver bb = new BBSolver();
        double start;
        double end;
        bb.Constructive=false;
        bfs.Constructive=false;
        /*Load data*/
        for(int i=1; i<=500; i++){
            Bag bag = new Bag();
            bag.readInstance(args[0], i);
            bag.readCorrectResult(args[1], i);
            Bags.add(bag);
        }
        /*Bruteforce*/
        start=System.nanoTime();
        for (Bag b: Bags) {
            b.BagSolution=bfs.Solve(b);
            if(bfs.Constructive) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for BF: "+(end-start)/ 1_000_000_000+ ", Counter: "+bfs.Counter/500+ "******");
        /*BranchBound*/
        start=System.nanoTime();
        for (Bag b : Bags){
            long lastCount=bb.Counter;
            b.BagSolution=bb.solveRecursion(b);
            System.out.println(bb.Counter-lastCount);
            if(bb.Constructive) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for BB: "+(end-start)/ 1_000_000_000+ ", Counter: "+bb.Counter/500+ "******");
        System.out.println("********************   File: " + args[0] + " finished.  ********************");
    }
}

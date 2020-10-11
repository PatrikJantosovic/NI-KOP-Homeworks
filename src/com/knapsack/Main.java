package com.knapsack;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Bag> Bags = new ArrayList<Bag>();
        BruteForceSolver bfs = new BruteForceSolver();
        BBSolver bb = new BBSolver();
        GreedySolver gs = new GreedySolver();
        double start;
        double end;
        bb.Constructive=true;
        bfs.Constructive=true;
        boolean checkSolution = false;
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
            if(checkSolution) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for BF: "+(end-start)/ 1_000_000_000+ ", Counter: "+bfs.Counter/500+ "******");
        /*BranchBound*/
        start=System.nanoTime();
        for (Bag b : Bags){
            b.BagSolution=bb.solveRecursion(b);
            if(checkSolution) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for BB: "+(end-start)/ 1_000_000_000+ ", Counter: "+bb.Counter/500+ "******");
        /*Greedy*/
        start=System.nanoTime();
        for (Bag b : Bags){
            b.BagSolution=gs.Solve(b);
            if(checkSolution) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for GS: "+(end-start)/ 1_000_000_000+ ", Counter: "+gs.Counter/500+ "******");
        /*GreedyRedux*/
        gs.Counter=0;
        start=System.nanoTime();
        for (Bag b : Bags){
            b.BagSolution=gs.SolveRedux(b, null);
            if(checkSolution) { b.checkSolution(); }
        }
        end=System.nanoTime();
        System.out.println("***** Time for GSRedux: "+(end-start)/ 1_000_000_000+ ", Counter: "+gs.Counter/500+ "******");
        System.out.println("********************   File: " + args[0] + " finished.  ********************");
    }
}

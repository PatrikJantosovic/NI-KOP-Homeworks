package com.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static boolean CHECKSOLUTION = false;
    public static boolean CONSTRUCTIVE = true;
    public static boolean CALCERROR = true;

    public static boolean GREEDY = true;
    public static boolean BNB = false;
    public static boolean BF = false;
    public static boolean GREEDYREDUX = true;
    public static boolean DYNAMIC = false;
    public static boolean FPTAS = true;

    public static void main(String[] args) {
        ArrayList<Bag> Bags = new ArrayList<Bag>();
        BruteForceSolver bfs = new BruteForceSolver();
        BBSolver bb = new BBSolver();
        GreedySolver gs = new GreedySolver();
        DynamicSolver ds = new DynamicSolver();
        FPTASSolver fs = new FPTASSolver();
        double greedyError = 0;
        double maxgreedyError = 0;
        double sumgreedyError = 0;
        double greedyReduxError = 0;
        double maxgreedyReduxError = 0;
        double sumgreedyReduxError = 0;
        double fptas0Error = 0;
        double maxFptas0Error = 0;
        double sumFptas0Error = 0;
        double fptas005Error = 0;
        double maxFptas005Error = 0;
        double sumFptas005Error = 0;
        double fptas01Error = 0;
        double maxFptas01Error = 0;
        double sumFptas01Error = 0;
        double fptas02Error = 0;
        double maxFptas02Error = 0;
        double sumFptas02Error = 0;
        double start =0;
        double end =0;
        int numberOfLines = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while (reader.readLine() != null) numberOfLines++;
            reader.close();
        }catch (java.io.IOException ex){
            numberOfLines=500;
        }
        bb.Constructive=CONSTRUCTIVE;
        bfs.Constructive=CONSTRUCTIVE;
        /*Load data*/
        for(int i=1; i<=numberOfLines; i++){
            Bag bag = new Bag();
            bag.Constructive = CONSTRUCTIVE;
            bag.readInstance(args[0], i);
            bag.readCorrectResult(args[1], i, Math.abs(bag.Id));
            Bags.add(bag);
        }
        /*Bruteforce*/
        if(BF) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = bfs.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
            }
            end = System.nanoTime();
            System.out.println("***** Time for BF: " + (end - start) / 1_000_000_000 + ", Counter: "
                + bfs.Counter / 500 + ", Max: "+ bfs.MaxCounter+"******");
        }
        /*BranchBound*/
        if(BNB) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = bb.solveRecursion(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
            }
            end = System.nanoTime();
            System.out.println("***** Time for BB: " + (end - start) / 1_000_000_000 + ", Counter: "
                + bb.Counter / 500 + ", Max: "+ bb.MaxCounter+"******");;
        }
        /*Greedy*/
        if(GREEDY) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = gs.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    greedyError=b.calcError();
                    maxgreedyError=Math.max(greedyError, maxgreedyError);
                    sumgreedyError+=greedyError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for Greedy: "+ sumgreedyError/numberOfLines + " Max Greedy error: "+maxgreedyError+ "******");
            System.out.println("***** Time for GS: " + (end - start) / 1_000_000_000  + "******");
        }
        /*GreedyRedux*/
        if(GREEDYREDUX) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = gs.SolveRedux(b, null);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    greedyReduxError=b.calcError();
                    maxgreedyReduxError=Math.max(greedyReduxError, maxgreedyReduxError);
                    sumgreedyReduxError+=greedyReduxError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for Greedy Redux: "+ sumgreedyReduxError/numberOfLines + " Max Greedy Redux error: "+maxgreedyReduxError+ "******");
            System.out.println("***** Time for GSRedux: "+(end-start)/ 1_000_000_000+ "******");
        }
        /*Dynamic solver*/
        if(DYNAMIC){
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = ds.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
            }
            end = System.nanoTime();
            System.out.println("***** Time for DYNAMIC: "+(end-start)/ 1_000_000_000+ "******");
        }
        /*FPTAS*/
        if(FPTAS){
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = fs.Solve(b, 0);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    fptas0Error=b.calcError();
                    maxFptas0Error=Math.max(fptas0Error, maxFptas0Error);
                    sumFptas0Error+=fptas0Error;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for FPTAS 0: "+ sumFptas0Error/numberOfLines + " Max FPTAS 0 error: "+maxFptas0Error+ "******");
            System.out.println("***** Time for FPTAS 0: "+(end-start)/ 1_000_000_000+ "******");
        }
        /*FPTAS*/
        if(FPTAS){
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = fs.Solve(b, 0.05);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    fptas005Error=b.calcError();
                    maxFptas005Error=Math.max(fptas005Error, maxFptas005Error);
                    sumFptas005Error+=fptas005Error;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for FPTAS 0.05: "+ sumFptas005Error/numberOfLines + " Max FPTAS 0.05 error: "+maxFptas005Error+ "******");
            System.out.println("***** Time for FPTAS 0.05: "+(end-start)/ 1_000_000_000+ "******");
        }
        /*FPTAS*/
        if(FPTAS){
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = fs.Solve(b, 0.1);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    fptas01Error=b.calcError();
                    maxFptas01Error=Math.max(fptas01Error, maxFptas01Error);
                    sumFptas01Error+=fptas01Error;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for FPTAS 0.1: "+ sumFptas01Error/numberOfLines + " Max FPTAS 0.1 error: "+maxFptas01Error+ "******");
            System.out.println("***** Time for FPTAS 0.1: "+(end-start)/ 1_000_000_000+ "******");
        }
        /*FPTAS*/
        if(FPTAS){
            start = System.nanoTime();
            for (Bag b : Bags) {
                b.BagSolution = fs.Solve(b, 0.2);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    fptas02Error=b.calcError();
                    maxFptas02Error=Math.max(fptas02Error, maxFptas02Error);
                    sumFptas02Error+=fptas02Error;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for FPTAS 0.2: "+ sumFptas02Error/numberOfLines + " Max FPTAS0.2 error: "+maxFptas02Error+ "******");
            System.out.println("***** Time for FPTAS 0.2: "+(end-start)/ 1_000_000_000+ "******");
        }
        System.out.println(sumgreedyError/numberOfLines+";"+sumgreedyReduxError/numberOfLines+";"+sumFptas0Error/numberOfLines+";"+sumFptas005Error/numberOfLines+";"+sumFptas01Error/numberOfLines+";"+ sumFptas02Error/numberOfLines);
        System.out.println(maxgreedyError+";"+maxgreedyReduxError+";"+maxFptas0Error+";"+maxFptas005Error+";"+maxFptas01Error+";"+maxFptas02Error);
        System.out.println("********************   File: " + args[0] + " finished.  ********************");
    }
}

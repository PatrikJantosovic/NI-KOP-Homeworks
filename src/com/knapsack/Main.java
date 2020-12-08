package com.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean CHECKSOLUTION = false;
    public static boolean CONSTRUCTIVE = true;
    public static boolean CALCERROR = true;

    public static boolean GREEDY = false;
    public static boolean BNB = false;
    public static boolean BF =  false;
    public static boolean GREEDYREDUX = false;
    public static boolean DYNAMIC = false;
    public static boolean FPTAS = false;
    public static boolean SA = true;

    public static double stdev(List<Double> list){
        double sum = 0.0;
        double mean = 0.0;
        double num=0.0;
        double numi = 0.0;
        double deno = 0.0;

        for (double i : list) {
            sum+=i;
        }
        mean = sum/list.size();

        for (double i : list) {
            numi = Math.pow((i - mean), 2);
            num+=numi;
        }

        return Math.sqrt(num/list.size());
    }

    public static void main(String[] args) {
        ArrayList<Bag> Bags = new ArrayList<Bag>();
        BruteForceSolver bfs = new BruteForceSolver();
        BBSolver bb = new BBSolver();
        GreedySolver gs = new GreedySolver();
        DynamicSolver ds = new DynamicSolver();
        FPTASSolver fs = new FPTASSolver();
        var greedyTime = new ArrayList<Double>(1001);
        var bfTime = new ArrayList<Double>(1001);
        var bbTime = new ArrayList<Double>(1001);
        var dpTime = new ArrayList<Double>(1001);
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
        double sumsaError = 0;
        double maxsaError = 0;
        double saError = 0;
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
                var l_start = System.nanoTime();
                b.BagSolution = bfs.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                var l_end = System.nanoTime();
                bfTime.add((l_end-l_start) / 1_000_000_000d );
            }
            end = System.nanoTime();
            System.out.println("***** Time for BF: " + (end - start) / 1_000_000_000 + ", Counter: "
                + bfs.Counter / 500 + ", Max: "+ bfs.MaxCounter+"******");
        }
        /*BranchBound*/
        if(BNB) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                var l_start = System.nanoTime();
                b.BagSolution = bb.solveRecursion(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                var l_end = System.nanoTime();
                bbTime.add((l_end-l_start) / 1_000_000_000d);
            }
            end = System.nanoTime();
            System.out.println("***** Time for BB: " + (end - start) / 1_000_000_000 + ", Counter: "
                + bb.Counter / 500 + ", Max: "+ bb.MaxCounter+"******");;
        }
        /*Greedy*/
        if(GREEDY) {
            start = System.nanoTime();
            for (Bag b : Bags) {
                var l_start = System.nanoTime();
                b.BagSolution = gs.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    greedyError=b.calcError();
                    maxgreedyError=Math.max(greedyError, maxgreedyError);
                    sumgreedyError+=greedyError;
                }
                var l_end = System.nanoTime();
                greedyTime.add((l_end-l_start) / 1_000_000_000d);
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
                var l_start = System.nanoTime();
                b.BagSolution = ds.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                var l_end = System.nanoTime();
                dpTime.add((l_end-l_start) / 1_000_000_000d);
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
        if(SA){
            start = System.nanoTime();
            for(Bag b : Bags) {
                SimulationSolver sas = new SimulationSolver(100,1000000,1,0.85);
                b.BagSolution = sas.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    saError=b.calcError();
                    maxsaError=Math.max(saError, maxsaError);
                    sumsaError+=saError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for SA: "+ sumsaError/numberOfLines + " Max SA error: "+maxsaError+ "******");
            System.out.println("***** Time for SA: "+(end-start)/ 1_000_000_000+ "******");
        }
        sumsaError = 0;
        maxsaError = 0;
        saError = 0;
        if(SA){
            start = System.nanoTime();
            for(Bag b : Bags) {
                SimulationSolver sas = new SimulationSolver(500,1000000,1,0.85);
                b.BagSolution = sas.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    saError=b.calcError();
                    maxsaError=Math.max(saError, maxsaError);
                    sumsaError+=saError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for SA: "+ sumsaError/numberOfLines + " Max SA error: "+maxsaError+ "******");
            System.out.println("***** Time for SA: "+(end-start)/ 1_000_000_000+ "******");
        }
       sumsaError = 0;
        maxsaError = 0;
        saError = 0;
        if(SA){
            start = System.nanoTime();
            for(Bag b : Bags) {
                SimulationSolver sas = new SimulationSolver(1000,1000000,1,0.85);
                b.BagSolution = sas.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    saError=b.calcError();
                    maxsaError=Math.max(saError, maxsaError);
                    sumsaError+=saError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for SA: "+ sumsaError/numberOfLines + " Max SA error: "+maxsaError+ "******");
            System.out.println("***** Time for SA: "+(end-start)/ 1_000_000_000+ "******");
        }
        sumsaError = 0;
        maxsaError = 0;
        saError = 0;
        if(SA){
            start = System.nanoTime();
            for(Bag b : Bags) {
                SimulationSolver sas = new SimulationSolver(4000,1000000,1,0.85);
                b.BagSolution = sas.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    saError=b.calcError();
                    maxsaError=Math.max(saError, maxsaError);
                    sumsaError+=saError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for SA: "+ sumsaError/numberOfLines + " Max SA error: "+maxsaError+ "******");
            System.out.println("***** Time for SA: "+(end-start)/ 1_000_000_000+ "******");
        }
        sumsaError = 0;
        maxsaError = 0;
        saError = 0;
        if(SA){
            start = System.nanoTime();
            for(Bag b : Bags) {
                SimulationSolver sas = new SimulationSolver(10000,1000000,1,0.85);
                b.BagSolution = sas.Solve(b);
                if (CHECKSOLUTION) {
                    b.checkSolution();
                }
                if(CALCERROR){
                    saError=b.calcError();
                    maxsaError=Math.max(saError, maxsaError);
                    sumsaError+=saError;
                }
            }
            end = System.nanoTime();
            System.out.println("***** Average Error for SA: "+ sumsaError/numberOfLines + " Max SA error: "+maxsaError+ "******");
            System.out.println("***** Time for SA: "+(end-start)/ 1_000_000_000+ "******");
        }

//        System.out.println(sumgreedyError/numberOfLines+";"+sumgreedyReduxError/numberOfLines+";"+sumFptas0Error/numberOfLines+";"+sumFptas005Error/numberOfLines+";"+sumFptas01Error/numberOfLines+";"+ sumFptas02Error/numberOfLines);
//        System.out.println(maxgreedyError+";"+maxgreedyReduxError+";"+maxFptas0Error+";"+maxFptas005Error+";"+maxFptas01Error+";"+maxFptas02Error);
//        System.out.println(Collections.min(bfTime) + "   " + Collections.min(bbTime) + "   " + Collections.min(greedyTime) + "   " + Collections.min(dpTime) );
//        System.out.println(Collections.max(bfTime) + "   " + Collections.max(bbTime) + "   " + Collections.max(greedyTime) + "   " + Collections.max(dpTime) );
//        System.out.println(bfTime.stream().mapToDouble(d -> d).average().getAsDouble() + "   " + bbTime.stream().mapToDouble(d -> d).average().getAsDouble() + "   " + greedyTime.stream().mapToDouble(d -> d).average().getAsDouble() + "   " + dpTime.stream().mapToDouble(d -> d).average().getAsDouble() );
//        System.out.println(stdev(bfTime) + "   " + stdev(bbTime) + "   " + stdev(greedyTime) + "   " + stdev(dpTime) );
        System.out.println("********************   File: " + args[0] + " finished.  ********************");
    }
}

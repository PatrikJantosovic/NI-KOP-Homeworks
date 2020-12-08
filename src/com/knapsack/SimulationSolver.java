package com.knapsack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationSolver {

  public double initTemp;
  public double finalTemp;
  public int steps;
  public double cooling;

  private int currentSteps;

  public SimulationSolver(int steps, double initTemp, double finalTemp, double cooling){
    this.steps = steps;
    this.initTemp = initTemp;
    this.finalTemp = finalTemp;
    this.cooling = cooling;
  }

  private boolean frozen(double temp) {
    return (temp < finalTemp);
  }

  private double cool(double temp) {
    double v = temp * cooling;
    return v;
  }

  private boolean equilibrium() {
    currentSteps--;
    return currentSteps < 0;
  }

  private Solution better(Solution state, Solution best, int W) {
    if(state.Weight > W) return best; // new is too heavy
    if (state.Price > best.Price) {
      return state;
    }
    return best;
  }

  private Solution getRandomNeighbour(Solution state, Bag bag){
    Random index = new Random();
    Solution solution = new Solution();
    solution.Items = (BitSet)state.Items.clone();
    solution.Price = state.Price;
    solution.Weight = state.Weight;
    int random = index.nextInt(bag.NumberOfItems);
    solution.Items.flip(random);
    if (solution.Items.get(random)) { //if true, it means i just added this
      solution.Weight += bag.Items.get(random).Weight;
      solution.Price += bag.Items.get(random).Price;
    } else {
      solution.Weight -= bag.Items.get(random).Weight;
      solution.Price -= bag.Items.get(random).Price;
    }
    return solution;
  }

//  private Solution getRandomNeighbour(Solution state, Bag bag){
//    Random gen = new Random();
//    Solution solution = new Solution();
//    do {
//        int random = gen.nextInt(bag.NumberOfItems);
//        solution.Items = (BitSet) state.Items.clone();
//        solution.Price = state.Price;
//        solution.Weight = state.Weight;
//        solution.Items.flip(random);
//        if (solution.Items.get(random)) { //if true, it means i just added this
//          solution.Weight += bag.Items.get(random).Weight;
//          solution.Price += bag.Items.get(random).Price;
//        } else {
//          solution.Weight -= bag.Items.get(random).Weight;
//          solution.Price -= bag.Items.get(random).Price;
//        }
//    } while(solution.Weight>bag.MaxWeight);
//    return solution;
//  }

  private Solution tryNext(Solution state, double temp, Bag bag) {
    Random random = new Random();
    Solution randomNeighbour = getRandomNeighbour(state, bag);
    if(randomNeighbour.Price > state.Price) return randomNeighbour;
    double delta = randomNeighbour.Price - state.Price;
    if(random.nextDouble() < Math.exp(- delta / temp))
      return randomNeighbour;
    return state;
  }

  public Solution Solve(Bag bag){
    Solution state = new Solution();
    Solution best = new Solution();
    Double temp = this.initTemp;
    while(!frozen(temp)){
      this.currentSteps = this.steps;
      while(!equilibrium()){
        state = tryNext(state, temp, bag);
        best = better(state, best, bag.MaxWeight);
      }
      temp = cool(temp);
    }
    best.ItemsIncluded=true;
    best.Type="SA";
    return best;
  }

  @Override
  public String toString() {
    return "SimulationSolver{"
        + "initTemp=" + initTemp
        + ", finalTemp=" + finalTemp
        + ", steps=" + steps
        + ", cooling=" + cooling
        + '}';
  }
}

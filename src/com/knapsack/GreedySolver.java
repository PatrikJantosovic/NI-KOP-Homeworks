package com.knapsack;

import java.util.Comparator;
import java.util.List;

public class GreedySolver {

  //Zoradime podla pomeru cena/vaha
  //A vo for loope prechadzame a vkladame ked sa vojde

  public Solution Solve(Bag bag){
    Solution solution = new Solution();
    solution.Type="Greedy";
    List<Item> itemsSorted = bag.Items;
    itemsSorted.sort(new Comparator<Item>() {
      public int compare(Item i1, Item i2) {
        if (i1.Price/(double)i1.Weight > i2.Price/(double)i2.Weight) return -1;
        if (i1.Price/(double)i1.Weight < i2.Price/(double)i2.Weight) return 1;
        return 0;
      }});
    int combWeight=0;
    int combPrice=0;
    for(int i=0; i<bag.NumberOfItems; i++)
    {
       if(combWeight+itemsSorted.get(i).Weight<=bag.MaxWeight) {
         combWeight = combWeight + itemsSorted.get(i).Weight;
         combPrice = combPrice + itemsSorted.get(i).Price;
       }
    }
    solution.Price=combPrice;
    solution.Weight=combWeight;
    return solution;
  }

  //najdem jedinu najlepsiu vec ktora sa do batohu vojde a porovnam s greedy solution
  public Solution SolveRedux(Bag bag, Solution greedySolution){
    if(greedySolution==null) greedySolution=this.Solve(bag);
    Solution solution = new Solution();
    solution.Type="GreedyRedux";
    int bestWeight=0;
    int bestPrice=0;
    for(int i=0; i<bag.NumberOfItems; i++)
    {
      if(bag.Items.get(i).Weight<=bag.MaxWeight && bag.Items.get(i).Price>bestPrice) {
        bestWeight = bag.Items.get(i).Weight;
        bestPrice = bag.Items.get(i).Price;
      }
    }
    if(bestPrice>greedySolution.Price){
      solution.Price=bestPrice;
      solution.Weight=bestWeight;
    }
    else{
      solution.Weight=greedySolution.Weight;
      solution.Price=greedySolution.Price;
    }
    return solution;
  }

}

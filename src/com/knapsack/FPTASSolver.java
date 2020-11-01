package com.knapsack;

import java.util.ArrayList;
import java.util.List;

public class FPTASSolver {

  // e je nasa odchylka (podla zadania nas pocet zanedbanych bitov vlastne nezaujima takze mozme tam dat rovno rozne e)
  public Solution Solve(Bag bag, double e){
    Solution solution = new Solution();
    Solution dynamicSolution;
    DynamicSolver dynamicSolver = new DynamicSolver();
    // Prejdeme veci v batohu - prilis tazke vyhodim a zmensime ich o K
    double K = bag.MaxItemPrice*e/bag.NumberOfItems;
    if(e==0) K=1; // test ze to funguje spravne -> je to defacto obalka na dynamicsolver s odhodenim tazkych itemov
    int totalPrice = 0;
    List<Item> items = new ArrayList<Item>();
    for (Item i: bag.Items) {
      if(i.Weight>bag.MaxWeight) continue;

      Item item = new Item();
      item.Weight=i.Weight;
      item.Id=i.Id;
      item.Price= (int) Math.floor(i.Price/K);
      totalPrice+=item.Price;
      items.add(item);
    }
    //v najlepsom pripade(?) su vsetky itemy tazke takze je to 0
    if(items.size()==0){
      solution.Price=0;
      solution.Weight=0;
      return solution;
    }
    dynamicSolution=dynamicSolver.Solve(items, items.size(), totalPrice, bag.MaxWeight);
    // teraz viem ktore itemy berem a potrebujem len dohladat realne ceny
    int j = 0;
    for(int i = 0; i<bag.NumberOfItems; i++){
      if(bag.Items.get(i).Weight>bag.MaxWeight) continue;
      if(dynamicSolution.Items.get(j)){
        solution.Weight+=bag.Items.get(i).Weight;
        solution.Price+=bag.Items.get(i).Price;
        solution.Items.set(i, true);
      }
      j++;
    }
    solution.Type="FPTAS"+e;
    return solution;
  }

}

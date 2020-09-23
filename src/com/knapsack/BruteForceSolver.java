package com.knapsack;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSolver {

    //DEFACTO ide len o FOR-LOOP cez PowerSet
    //https://www.techiedelight.com/generate-powerset-set-java/
    // jednoduche riesenie cez binarne cisla - ani ho nemusim generovat len nascitavam weight a price
    public Solution Solve(Bag bag){
      Solution solution = new Solution();
      double numberOfCombinations= Math.pow(2, bag.NumberOfItems);
      for(int i=0; i<numberOfCombinations; i++)
      {
        int combWeight=0;
        int combPrice=0;
        for (int j = 0; j < bag.NumberOfItems; j++)
        {
          if ((i & (1 << j)) == 0){
            continue;
          }

          combWeight=combWeight+bag.Items.get(j).Weight;
          combPrice=combPrice+bag.Items.get(j).Price;

        }
        if(combWeight<=bag.MaxWeight && combPrice>solution.Price){
          solution.Price=combPrice;
          solution.Weight=combWeight;
        }
      }
      return solution;
    }

  public Solution SolveSmarter(Bag bag){
    Solution solution = new Solution();
    double numberOfCombinations= Math.pow(2, bag.NumberOfItems);
    for(int i=0; i<numberOfCombinations; i++)
    {
      int combWeight=0;
      int combPrice=0;
      for (int j = 0; j < bag.NumberOfItems; j++)
      {
        if ((i & (1 << j)) == 0){
          continue;
        }

        combWeight=combWeight+bag.Items.get(j).Weight;
        combPrice=combPrice+bag.Items.get(j).Price;

        //SMARTER - ale so zlozitostou to nic nerobi, len pre zaujimavost
        if(combWeight>bag.MaxWeight) break;

      }
      if(combWeight<=bag.MaxWeight && combPrice>solution.Price){
        solution.Price=combPrice;
        solution.Weight=combWeight;
      }
    }
    return solution;
  }

}
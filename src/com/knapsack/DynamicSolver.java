package com.knapsack;

import java.util.List;

public class DynamicSolver {

  public Solution Solve(Bag bag){
    return Solve(bag.Items, bag.NumberOfItems, bag.TotalPrice, bag.MaxWeight);
  }

  //potrebujeme to vediet volat takto kvoli FPTAS
  public Solution Solve(List<Item> items, int n, int T, int W){
    Solution solution = new Solution();
    solution.Type="DYNAMIC";
    solution.ItemsIncluded=true;
    int[][] dynamicTable = new int[n+1][T+1];
    // naplnim zname hodnoty, defacto prve dva stlpce
    for(int j=0; j<=T; j++){
      dynamicTable[0][j] = Integer.MAX_VALUE;
      dynamicTable[1][j] = Integer.MAX_VALUE;
    }
    dynamicTable[1][items.get(0).Price]=items.get(0).Weight;
    dynamicTable[0][0] = 0;
    dynamicTable[1][0] = 0;
    // spocitam zvysok tabulky
    for(int i = 2; i <= n; i++){
      for(int j = 0; j <= T; j++){
        if(j>=items.get(i-1).Price && dynamicTable[i-1][j - items.get(i-1).Price] != Integer.MAX_VALUE)  {
          dynamicTable[i][j] = Integer.min(dynamicTable[i-1][j],
              dynamicTable[i-1][j - items.get(i-1).Price] + items.get(i-1).Weight);
        }
        else{
          dynamicTable[i][j]=dynamicTable[i-1][j];
        }
      }
    }
    // najlepsie riesenie je v poslednom stlpci s co najvecsim j
    for(int j = T; j>=0; j--){
      if(dynamicTable[n][j]<=W){
        solution.Price=j;
        solution.Weight=dynamicTable[n][j];
        break;
      }
    }
    // a pozbieram si Itemy
    int j = solution.Price;
    for (int i = n; i >= 1; i--) {
      if (dynamicTable[i][j] != dynamicTable[i-1][j]) {
        solution.Items.set(i-1, true);
        j -= items.get(i-1).Price;
      }
    }

    return solution;
  }



}

package com.knapsack;

public class Node {

  public int Level; // level v strome
  public int Price; // nasumovana cena od rootu k tomuto uzlu
  public int Weight; // nasummovana vaha od rootu k tomuto uzlu
  public int Bound; // maximum ceny ktoru dosiahnem ked budem pokracovat v podstrome

  public Node(){
    this.Weight=0;
    this.Price=0;
    this.Bound=0;
    this.Level=0;
  }

  public int calcBound(int MaxWeight){
    return 0;
  }


}

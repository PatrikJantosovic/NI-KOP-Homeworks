package com.knapsack;

import java.util.Comparator;

public class Node {

  public int Level; // level v strome
  public int Price; // nasumovana cena od rootu k tomuto uzlu
  public int Weight; // nasummovana vaha od rootu k tomuto uzlu
  public double Bound; // maximum ceny ktoru dosiahnem ked budem pokracovat v podstrome defacto je to pre root celkova cena

  public Node(){
    this.Weight=0;
    this.Price=0;
    this.Bound=0;
    this.Level=0;
  }

  public Node(Node other){
    this.Weight=other.Weight;
    this.Price=other.Price;
    this.Bound=other.Bound;
    this.Level=other.Level;
  }

}

package com.knapsack;

import java.util.Comparator;

public class Node implements Comparable<Node> {

  public int Level; // level v strome
  public int Price; // nasumovana cena od rootu k tomuto uzlu
  public int Weight; // nasummovana vaha od rootu k tomuto uzlu
  public double Bound; // maximum ceny ktoru dosiahnem ked budem pokracovat v podstrome

  public Node(){
    this.Weight=0;
    this.Price=0;
    this.Bound=0;
    this.Level=0;
  }

  /*@Override
  public int compareTo(Node other) {
    if(this.Bound<other.Bound)
      return -1;
    else if(other.Bound<this.Bound)
      return 1;
    return 0;
  }*/
  @Override
      public int compareTo(Node a) {
        return Double.compare(a.Bound, this.Bound);
      }

}

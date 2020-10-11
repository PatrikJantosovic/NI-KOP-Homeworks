package com.knapsack;

import java.util.BitSet;

public class Solution {
  public int Price;
  public int Weight;
  public boolean ItemsIncluded;
  public BitSet Items;
  public String Type;

  public Solution(){
    this.Price=0;
    this.Weight=0;
    this.ItemsIncluded=false;
    this.Items = new BitSet();
    this.Type = "";
  }

}

package com.knapsack;

import java.util.Comparator;

public class Item{

  public int Price;
  public int Weight;
  public int Id;

  public Item(){};

  public Item(int price, int weight, int id){
    this.Price=price;
    this.Weight=weight;
    this.Id=id;
  }

  public static Comparator<Item> byPriceToWeightRatio() {
    return new Comparator<Item>() {
      public int compare(Item a, Item b) {
        return Double.compare(b.Price/b.Weight, a.Price/a.Weight);
      }
    };
  }

}

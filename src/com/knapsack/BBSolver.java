package com.knapsack;

import java.util.Collections;
import java.util.PriorityQueue;

//Implementacia na zaklade pseudo kodu z: https://www.geeksforgeeks.org/implementation-of-0-1-knapsack-using-branch-and-bound/?ref=rp
public class BBSolver {

  double calcBound(Bag bag, Node node){
    if(node.Weight>bag.MaxWeight) return 0;
    if(node.Level==8) {
      int x = 0;
    };
    double bound = node.Price;
    int level = node.Level+1;
    int weight = node.Weight;

    while(level<bag.NumberOfItems && weight+bag.Items.get(level).Weight<=bag.MaxWeight){
      weight=weight+bag.Items.get(level).Weight;
      bound=bound+bag.Items.get(level).Price;
      level++;
    }
    if(level<bag.NumberOfItems){
      bound=bound+((bag.MaxWeight-weight)*(bag.Items.get(level).Price/(double)bag.Items.get(level).Weight));
    }

    return bound;
  }

  public Solution Solve(Bag bag){
    Solution solution = new Solution();
    Collections.sort(bag.Items, Item.byPriceToWeightRatio());
    int maxPrice = 0;
    int maxWeight = 0;

    PriorityQueue<Node> queue = new PriorityQueue<Node>();
    Node root = new Node();
    root.Level=-1;
    queue.add(root);

    while(!queue.isEmpty()){
      Node node = queue.remove();
      Node included = new Node();
      Node excluded = new Node();

      if(node.Level==bag.NumberOfItems-1) continue;

      included.Level=node.Level+1;
      included.Price=node.Price+bag.Items.get(included.Level).Price;
      included.Weight=node.Weight+bag.Items.get(included.Level).Weight;

      if (included.Weight <= bag.MaxWeight && included.Price > maxPrice) {
        solution.Items.set(bag.Items.get(included.Level).Id);
        maxPrice = included.Price;
        maxWeight = included.Weight;
      }

      included.Bound = calcBound(bag, included);

      // VETVA STROMU KDE ITEM VLOZIM DO BATOHA
      if (included.Bound > maxPrice)
        queue.add(included);

      // VETVA STROMU KDE ITEM NEVLOZIM DO BATOHA
      excluded.Weight=node.Weight;
      excluded.Price=node.Price;
      excluded.Level= included.Level;
      excluded.Bound = calcBound(bag, excluded);
      if (excluded.Bound > maxPrice)
        queue.add(excluded);
    }
    solution.Price=maxPrice;
    solution.Weight=maxWeight;
    solution.ItemsIncluded=true;
    return solution;
  }
}

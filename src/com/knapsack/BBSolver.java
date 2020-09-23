package com.knapsack;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

//Implementacia na zaklade pseudo kodu z: https://www.geeksforgeeks.org/implementation-of-0-1-knapsack-using-branch-and-bound/?ref=rp
public class BBSolver {

  public Solution Solve(Bag bag){
    Solution solution = new Solution();
    Collections.sort(bag.Items, Item.byPriceToWeightRatio());
    int maxPrice = 0;

    PriorityQueue<Node> queue = new PriorityQueue<Node>();
    Node root = new Node();
    queue.add(root);

    while(!queue.isEmpty()){
      Node node = queue.remove();
      Node subnode = new Node();

      if(node.Level==bag.NumberOfItems-1) continue;

      subnode.Level=node.Level+1;
      subnode.Price=node.Price+bag.Items.get(subnode.Level).Price;
      subnode.Weight=node.Weight+bag.Items.get(subnode.Level).Weight;

      if (subnode.Weight <= bag.MaxWeight && subnode.Price > maxPrice)
        maxPrice = subnode.Price;

      subnode.Bound = subnode.calcBound();

      // VETVA STROMU KDE ITEM VLOZIM DO BATOHA
      if (subnode.Bound > maxPrice)
        queue.add(subnode);

      // VETVA STROMU KDE ITEM NEVLOZIM DO BATOHA
      subnode.Weight = node.Weight;
      subnode.Price = node.Price;
      subnode.Bound = subnode.calcBound();
      if (subnode.Bound > maxPrice)
        queue.add(subnode);
    }
    solution.Price=maxPrice;
    return solution;
  }
}

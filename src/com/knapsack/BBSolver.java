package com.knapsack;

//rekurzivna implementacia brute-force like s pridanymi podmienkami na orezavanie stromu
public class BBSolver {

  public Node bestNode;
  public boolean Constructive;
  public long Counter=1;
  public long MaxCounter=0;
  public long PreviousCounter=0;

  public void solveRecursionNode(Node node, Bag bag){
    if(node.Price + node.Bound < bag.MinPrice && !this.Constructive){
      return; // do minima nedojdem = len pri DECISION, pri constructive verzii ma to netrapi
    }
    if (node.Level == bag.NumberOfItems) {
      if (node.Weight <= bag.MaxWeight && node.Price > bestNode.Price && (node.Price >= bag.MinPrice || this.Constructive)) {
        bestNode = new Node(node);
      }
      return;
    }
    else if (node.Weight > bag.MaxWeight)
      return;
    else if (node.Price + node.Bound < bestNode.Price)
      return;
    this.Counter++;
    //raz pridam item, raz nepridam item
    Node other = new Node(node);
    other.Level++;
    other.Weight += bag.Items.get(node.Level).Weight;
    other.Price += bag.Items.get(node.Level).Price;
    other.Bound -= bag.Items.get(node.Level).Price;
    solveRecursionNode(other, bag);
    Node next = new Node(node);
    next.Level++;
    solveRecursionNode(next, bag);
  }

  public Solution solveRecursion(Bag bag){
    Solution solution = new Solution();
    solution.Type="BranchBound";
    Node root = new Node();
    root.Level=0;
    root.Bound=bag.TotalPrice;
    root.Price=0;
    root.Weight=0;
    this.bestNode=new Node(root);
    solveRecursionNode(root, bag);
    solution.Weight=this.bestNode.Weight;
    solution.Price=this.bestNode.Price;
    if(this.Counter - this.PreviousCounter > this.MaxCounter) this.MaxCounter=this.Counter - this.PreviousCounter;
    this.PreviousCounter = this.Counter;
    return solution;
  }

}

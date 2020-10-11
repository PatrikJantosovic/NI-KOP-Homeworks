   if(node.Price + node.Bound < bag.MinPrice && !this.Constructive){
      return; // do minima nedojdem = len pri DECISION, pri constructive verzii ma to netrapi
    }
    if (node.Level == bag.NumberOfItems) {
      if (node.Weight <= bag.MaxWeight && node.Price > bestNode.Price 
      && (node.Price >= bag.MinPrice || this.Constructive)) {
        bestNode = new Node(node);
      }
      return;
    }
    else if (node.Weight > bag.MaxWeight)
      return;
    else if (node.Price + node.Bound < bestNode.Price)
      return;

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
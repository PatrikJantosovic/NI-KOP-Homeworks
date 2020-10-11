      double numberOfCombinations= Math.pow(2, bag.NumberOfItems);
      for(int i=0; i<numberOfCombinations; i++)
      {
        int combWeight=0;
        int combPrice=0;
        for (int j = 0; j < bag.NumberOfItems; j++)
        {
          if ((i & (1 << j)) == 0){
            continue;
          }
          combWeight=combWeight+bag.Items.get(j).Weight;
          combPrice=combPrice+bag.Items.get(j).Price;
        }
        if(combWeight<=bag.MaxWeight && combPrice>solution.Price 
        && (combPrice >= bag.MinPrice || this.Constructive)){
          solution.Price=combPrice;
          solution.Weight=combWeight;
        }
      }
package com.knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Bag {

  public List<Item>  Items;

  public int MaxWeight;

  public int MinPrice;

  public int TotalPrice;

  public int Id;

  public int NumberOfItems;

  public Solution BagSolution;

  public Solution CorrectSolution;

  public int MaxItemPrice = 0;

  public boolean Constructive = true;

  public void readInstance(String filename, int line){
    int currentLine = 0;
    try {
      File instance = new File(filename);
      Scanner myReader = new Scanner(instance);
      while (myReader.hasNextLine()) {
        currentLine++;
        if(line>currentLine){
          String data = myReader.nextLine();
          continue;
        }
        this.Id=myReader.nextInt();
        this.NumberOfItems=myReader.nextInt();
        this.MaxWeight=myReader.nextInt();
        if(!this.Constructive) this.MinPrice=myReader.nextInt();
        this.Items=new ArrayList<Item>();
        for(int i=0; i<this.NumberOfItems; i++){
          int weight=myReader.nextInt();
          int price=myReader.nextInt();
          this.TotalPrice+=price;
          // zide sa v FPTAS
          this.MaxItemPrice=Integer.max(price,this.MaxItemPrice);
          Item item = new Item(price,weight,i);
          this.Items.add(item);
        }
        return;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred while reading file.");
      e.printStackTrace();
    }
  }

  public void checkSolution(){
    if(this.CorrectSolution.Price==this.BagSolution.Price) return;
    System.out.println("Different results ("+this.BagSolution.Type+") for bag: "+ this.Id + ", correct result: "+this.CorrectSolution.Price+ ", your result: "+this.BagSolution.Price+ " and your weight: "+this.BagSolution.Weight);
    if(this.BagSolution.ItemsIncluded) System.out.println("Correct items: "+this.CorrectSolution.Items.toString()+" your items: " +this.BagSolution.Items.toString());
  }

  public double calcError(){
    if(this.CorrectSolution.Price==this.BagSolution.Price || this.CorrectSolution.Price==0 || this.BagSolution.Price==0) return 0d;
    double error = 0d;
    error = Math.abs(this.CorrectSolution.Price - this.BagSolution.Price);
    error = error / Math.max(this.CorrectSolution.Price, this.BagSolution.Price);
    if(error==1){

    }
    return error;
  }

  public void checkItems(){
    if(!this.BagSolution.ItemsIncluded) return;
    if(!this.CorrectSolution.Items.toString().equals(this.BagSolution.Items.toString())) System.out.println("Correct items: "+this.CorrectSolution.Items.toString()+" your items: " +this.BagSolution.Items.toString());
  }

  public void readCorrectResult(String filename, int line, int bagId){
    int currentLine = 0;
    try {
      File instance = new File(filename);
      Scanner myReader = new Scanner(instance);
      while (myReader.hasNextLine()) {
        currentLine++;
        if(line>currentLine){
          String data = myReader.nextLine();
          continue;
        }
        int id = myReader.nextInt();
        if(bagId!=id){
          String data = myReader.nextLine();
          continue;
        }
        this.CorrectSolution = new Solution();
        this.CorrectSolution.ItemsIncluded=true;

        int n = myReader.nextInt();
        this.CorrectSolution.Price=myReader.nextInt();
        for(int i=0; i<this.NumberOfItems; i++){
          if(myReader.nextInt()==1){
            this.CorrectSolution.Items.set(i);
          }
        }
        return;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred while reading file.");
      e.printStackTrace();
    }
  }

  public void printInstance(){
    System.out.println("Bag MaxWeight: "+this.MaxWeight+" MinPrice: "+this.MinPrice);
    for ( Item x : this.Items) {
      System.out.println("Weight: "+x.Weight+" Price: "+x.Price);
    }
  }

  public void printToFile(String filename){
    try {
      File resultFile = new File(filename);
      FileWriter fileWriter = new FileWriter(filename, true);
      fileWriter.write(this.formatSolution());
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred writing to a file.");
      e.printStackTrace();
    }
  }

  public String formatSolution(){
    if(this.BagSolution != null){
      String result="";
      result=-this.Id+" "+this.NumberOfItems+" "+this.BagSolution.Price+' '+this.BagSolution.Weight;
      return result+" \n";
    }
    return "Not solved yet!";
  }

}

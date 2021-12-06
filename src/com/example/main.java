package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class main {

    public static void main(String[] args) throws FileNotFoundException {

        database db = database.getInstance();

        //reading cards
        Scanner sc = new Scanner(new File("Data/Cards.csv"));
        String a;
        a = sc.nextLine();
        if (a.equals("CardNumber")) {
        } else {
            System.out.println("Error format");
        }
        while (sc.hasNextLine()) {
            a = sc.nextLine();
            a = a.replace(" ", "");
            db.CreditCard_Set.add(a);
        }
        sc.close();

        //reading dataset
        sc = new Scanner(new File("Data/Dataset.csv"));
        a = sc.nextLine();
        if (a.equals("Category,Item,Quantity,Price/ Piece")) {
        } else {
            System.out.println("Error format");
        }
        while (sc.hasNextLine()) {
            a = sc.nextLine();
            String words[] = a.split(",");
            db.category.put(words[1], words[0]);
            db.quantity.put(words[1], Integer.parseInt(words[2]));
            db.price.put(words[1], Double.parseDouble(words[2]));
        }
        sc.close();

        //reading order
        double total = 0.0;
        Integer numLuxury = 0;
        Integer numEssential = 0;
        Integer numMisc = 0;

        //change input file
        sc = new Scanner(new File("Data/input3.csv"));
        a = sc.nextLine();
        if (a.equals("Item,Quantity,Card number")) {
        } else {
            System.out.println("Error format");
        }
        while (sc.hasNextLine()) {
            a = sc.nextLine();

            String words[] = a.split(",");

            String cate = db.category.get(words[0]);
            if (db.quantity.get(words[0]) < Integer.parseInt(words[1])){
                System.out.println("item:" + words[0] + " don't have enough stock");
                System.exit(0);
            }
            db.quantity.put(words[0],db.quantity.get(words[0]) - Integer.parseInt(words[1]));

            if(cate.equals("Luxury")){
                numLuxury += Integer.parseInt(words[1]);
                if(numLuxury > db.limit.get("Luxury"))
                {
                    System.out.println("Please correct quantities");
                    System.out.println("item:"+ words[0] + " in Category Luxury exceeds buying limit" + db.limit.get("Luxury"));
                    System.exit(0);
                }

            }
            else if(cate.equals("Essential")){
                numEssential += Integer.parseInt(words[1]);
                if(numEssential > db.limit.get("Essential"))
                {
                    System.out.println("Please correct quantities");
                    System.out.println("item:"+ words[0] + " in Category Essential exceeds buying limit" + db.limit.get("Essential"));
                    System.exit(0);
                }
            }
            else if(cate.equals("Misc")){
                numMisc += Integer.parseInt(words[1]);
                if(numMisc > db.limit.get("Misc"))
                {
                    System.out.println("Please correct quantities");
                    System.out.println("item:"+ words[0] + " in Category Essential exceeds buying limit" + db.limit.get("Misc"));
                    System.exit(0);
                }
            }

            String card = words[2];
            card = card.replace(" ", "");
            if (db.CreditCard_Set.contains(card)) {

            } else {
                db.CreditCard_Set.add(card);
                System.out.println("Card:" + card + " Added to the database");
            }
            total += db.price.get(words[0]);
        }
        PrintWriter writer = new PrintWriter("output.csv");
        writer.write("Amount paid, " + total);
        writer.close();
        System.out.println("Amount paid:" + total);
        sc.close();

    }
}

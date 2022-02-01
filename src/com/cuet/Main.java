package com.cuet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private ArrayList<Art> ArtList;
    private final String FileName = "Database.txt";

    private static Scanner scanner = new Scanner(System.in);

    public static void printConsole(String st) {
        System.out.println(st);
    }

    public static String readConsole() {
        String text = scanner.nextLine();
        return text;
    }

    public static float readFloatConsole() {
        float text = -100f;
        while (text == -100f) {
            try {
                text = scanner.nextFloat();
            } catch (Exception e) {
                printConsole("Invalid Input. Enter Again.");
                scanner.nextLine();
            }
        }
        return text;
    }

    public static int readIntConsole() {
        int text = -100;
        while (text == -100) {
            try {
                text = scanner.nextInt();
            } catch (Exception e) {
                printConsole("Invalid Input. Enter Again.");
                scanner.nextLine();
            }
        }
        return text;
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void save() {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(FileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(ArtList);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(FileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArtList = (ArrayList<Art>) ois.readObject();
        fin.close();
    }


    private void initData() {
        try {
            read();
        } catch (Exception e) {
            ArtList = new ArrayList<Art>();
        }
    }

    private void showOptions() {
        clearScreen();
        printConsole("\t\t\tWelcome To TRACKER\n");
        printConsole("1. Add a Consumable\n" +
                "2. Edit a Consumable\n" +
                "3. Delete a Consumable\n" +
                "4. See the list of consumables and individually\n" +
                "5. See overall info\n" +
                "Press q to Quit");
        printConsole("Enter Your Choice: ");
    }

    private Art getArt(ArtType artType) {
        printConsole("Enter Name: ");
        String name = readConsole();
        printConsole("Enter Rating: ");
        float rating = readFloatConsole();
        printConsole("Start Date: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        printConsole("End Date: ");
        printConsole("Total Consumption in Hours: 0");
        printConsole("Total Consumption in Days: 0");
        return new Art(name, rating, artType);
    }

    private void addConsumable() {
        String option = "";
        while (!option.equals("0")) {
            clearScreen();
            printConsole("Add Consumable\n\n");
            printConsole("1. Books\n" +
                    "2. Movies\n" +
                    "3. Series\n");
            printConsole("press 0 to go back");
            printConsole("\nEnter Your Choice: ");
            option = readConsole();
            Art art = null;
            switch (option) {
                case "1":
                    art = getArt(ArtType.BOOKS);
                    break;
                case "2":
                    art = getArt(ArtType.MOVIES);
                    break;
                case "3":
                    art = getArt(ArtType.SERIES);
                    break;
                case "0":
                    return;
                default:
                    break;
            }
            if (art != null) {
                ArtList.add(art);
                save();
                printConsole("Added!\n press any key to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void editConsumable() {
    }

    private void deleteConsumable() {
    }

    private void showIndividualConsumable(Art art) {
        clearScreen();
        art.showDetails();
        printConsole("Press Any Key to Go Back...");
        try {
            System.in.read();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConsumables(ArtType artType) {
        clearScreen();
        int cnt = 0;
        printConsole("----------------------------------------------");
        System.out.format("%6s%20s%6s%6s%8s", "Index", "Name", "Day", "Hour", "Rating");
        printConsole("\n----------------------------------------------");
        for (int i = 0; i < ArtList.size(); i++) {
            cnt = cnt + ArtList.get(i).showIfTypeMatch(i + 1, artType);
        }
        int option = -1;
        printConsole("Enter Index To View. Enter 0 To Go Back");
        while (true) {
            option = readIntConsole();
            if (option > 0 && option <= cnt) {
                showIndividualConsumable(ArtList.get(option - 1));
                showConsumables(artType);
            } else if (option == 0) {
                return;
            }
            else
            {
                printConsole("Invalid Input. Try Again.");
            }
        }
    }

    private void seeConsumable() {
        String option = "";
        while (!option.equals("0")) {
            clearScreen();
            printConsole("See Consumable\n\n");
            printConsole("1. Books\n" +
                    "2. Movies\n" +
                    "3. Series\n");
            printConsole("press 0 to go back");
            printConsole("\nEnter Your Choice: ");
            option = readConsole();
            ArtType artType = null;
            switch (option) {
                case "1":
                    artType = ArtType.BOOKS;
                    break;
                case "2":
                    artType = ArtType.MOVIES;
                    break;
                case "3":
                    artType = ArtType.SERIES;
                    break;
                case "0":
                    return;
                default:
                    break;
            }
            if (artType != null)
                showConsumables(artType);
        }
    }

    private void seeStat() {
    }


    public static void main(String[] args) {
        Main instance = new Main();
        instance.initData();
        String option = "";
        while (!option.equals("q")) {
            clearScreen();
            instance.showOptions();
            option = readConsole();
            switch (option) {
                case "1":
                    instance.addConsumable();
                    break;
                case "2":
                    instance.editConsumable();
                    break;
                case "3":
                    instance.deleteConsumable();
                    break;
                case "4":
                    instance.seeConsumable();
                    break;
                case "5":
                    instance.seeStat();
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }

}

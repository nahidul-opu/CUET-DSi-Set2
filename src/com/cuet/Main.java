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
    public static TimeTracker timeTracker;
    private static Scanner scanner = new Scanner(System.in);

    public static void printConsole(String st) {
        System.out.println(st);
    }

    public static String readConsole() {
        String text = scanner.nextLine();
        if (text.isEmpty()) return readConsole();
        else return text;
    }

    public static float readFloatConsole() {
        String text = "";
        while (true) {
            try {
                text = scanner.nextLine();
                if (text.isEmpty()) return 0;
                float val = Float.parseFloat(text);
                return val;
            } catch (Exception e) {
                printConsole("Invalid Input. Enter Again.");
                scanner.nextLine();
            }
        }
    }

    public static int readIntConsole() {
        String text = "";
        while (true) {
            try {
                text = scanner.nextLine();
                if (text.isEmpty()) return 0;
                int val = Integer.parseInt(text);
                return val;
            } catch (Exception e) {
                printConsole("Invalid Input. Enter Again.");
                scanner.nextLine();
            }
        }
    }

    public static Date readDateConsole() {
        String text = "";
        Date date;
        text = scanner.nextLine();
        if (text.isEmpty()) return null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(text);
            return date;
        } catch (Exception e) {
            return readDateConsole();
        }
    }

    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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
        timeTracker = TimeTracker.getInstance();
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
                "\nPress q to Quit");
        printConsole("Enter Your Choice: ");
    }

    private Art getArt(ArtType artType) {
        printConsole("Enter Name: ");
        String name = readConsole();
        printConsole("Enter Rating: ");
        float rating = readFloatConsole();
        printConsole("Enter Start Date(dd/mm/yyyy): ");
        Date sd = readDateConsole();
        if (sd == null) sd = new Date();
        printConsole("Enter End Date(dd/mm/yyyy): ");
        Date ed = readDateConsole();
        printConsole("Enter Total Consumption in Hours: 0");
        printConsole("Total Consumption in Days: 0");
        return new Art(name, rating, artType, sd, ed);
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

    private void editItem(int indx, Art art) {
        int option = -1;
        while (true) {
            clearScreen();
            printConsole("----------------------------------------------------------------------------------------");
            System.out.format("%6s%20s%8s%15s%6s%6s%8s%15s", "Index", "Name", "Type", "Start Date", "Day", "Hour", "Rating", "End Date");
            printConsole("\n----------------------------------------------------------------------------------------");
            art.showAll(1);
            printConsole("\n\n1. Add Time in Hours\n" +
                    "2. Add A Day\n" +
                    "3. Change rating\n" +
                    "4. Update Consumption Date\n" +
                    "Enter 0 to Go Back");
            option = readIntConsole();
            if (option == 0) {
                ArtList.set(indx, art);
                save();
                timeTracker.save();
                return;
            } else if (option == 1) {
                printConsole("Enter Hours to Add: ");
                int hours = readIntConsole();
                art.addHours(hours);
                timeTracker.updateTime(hours, art.getType());
            } else if (option == 2) {
                art.addDay();
                timeTracker.updateTime(24, art.getType());
            } else if (option == 3) {
                printConsole("Enter Rating: ");
                float rating = readFloatConsole();
                art.updateRating(rating);
            } else if (option == 4) {
                printConsole("Enter EndDate (dd/mm/yyyy): ");
                Date ed = readDateConsole();
                art.updateEndDate(ed);
            } else {
                printConsole("Invalid Input. Try Again");
            }
        }
    }

    private void editConsumable() {
        int option = -1;
        while (option != 0) {
            clearScreen();
            printConsole("Edit Consumable\n\n");
            printConsole("----------------------------------------------------------------------------------------");
            System.out.format("%6s%20s%8s%15s%6s%6s%8s%15s", "Index", "Name", "Type", "Start Date", "Day", "Hour", "Rating", "End Date");
            printConsole("\n----------------------------------------------------------------------------------------");
            ArrayList<Art> temp = new ArrayList<Art>();
            for (int i = 0; i < ArtList.size(); i++) {
                ArtList.get(i).showAll(i + 1);
            }
            printConsole("Enter Index To Edit. Enter 0 To Go Back");
            option = readIntConsole();
            if (option == 0)
                return;
            if (option > 0 && option <= ArtList.size()) {
                if (ArtList.get(option - 1).isEditable())
                    editItem(option - 1, ArtList.get(option - 1));
                else
                    printConsole("Not Editable!");
            } else {
                printConsole("Invalid Input. Try Again.");
                option = readIntConsole();
            }
        }
    }


    private void deleteConsumable() {
        int option = -1;
        while (option != 0) {
            clearScreen();
            printConsole("Edit Consumable\n\n");
            printConsole("----------------------------------------------------------------------------------------");
            System.out.format("%6s%20s%8s%15s%6s%6s%8s%15s", "Index", "Name", "Type", "Start Date", "Day", "Hour", "Rating", "End Date");
            printConsole("\n----------------------------------------------------------------------------------------");
            ArrayList<Art> temp = new ArrayList<Art>();
            for (int i = 0; i < ArtList.size(); i++) {
                ArtList.get(i).showAll(i + 1);
            }
            printConsole("Enter Index To Delete. Enter 0 To Go Back");
            option = readIntConsole();
            if (option == 0)
                return;
            if (option > 0 && option <= ArtList.size()) {
                /*printConsole("Are you sure? enter y/n");
                String op = "";
                while (true) {
                    op = readConsole();
                    if (op.equals("y")) {*/
                ArtList.remove(option - 1);
                save();
                    /*}
                    else if (op.equals("n")) break;
                    else printConsole("Invalid Input. Try Again.");
                }*/
            } else {
                printConsole("Invalid Input. Try Again.");
                option = readIntConsole();
            }
        }
    }

    private void showIndividualConsumable(Art art) {
        clearScreen();
        art.showDetails();
        printConsole("\nPress 0 Key to Go Back...");
        int option = readIntConsole();
        while (option != 0) {
            option = readIntConsole();
        }
    }

    private void showConsumables(ArtType artType) {

        int option = -1;
        while (option != 0) {
            clearScreen();
            printConsole("----------------------------------------------");
            System.out.format("%6s%20s%6s%6s%8s", "Index", "Name", "Day", "Hour", "Rating");
            printConsole("\n----------------------------------------------");
            ArrayList<Art> temp = new ArrayList<Art>();
            for (int i = 0; i < ArtList.size(); i++) {
                Art t = ArtList.get(i);
                if (t.getType() == artType) {
                    temp.add(t);
                    t.show(temp.size());
                }
            }
            printConsole("Enter Index To View. Enter 0 To Go Back");
            int cnt = temp.size();
            option = readIntConsole();
            if (option == 0)
                return;
            if (option > 0 && option <= cnt) {
                showIndividualConsumable(temp.get(option - 1));
            } else {
                printConsole("Invalid Input. Try Again.");
                option = readIntConsole();
            }
        }
    }

    private void seeConsumable() {
        int option = -1;
        while (option != 0) {
            clearScreen();
            printConsole("See Consumable\n\n");
            printConsole("1. Books\n" +
                    "2. Movies\n" +
                    "3. Series\n");
            printConsole("press 0 to go back");
            printConsole("\nEnter Your Choice: ");
            option = readIntConsole();
            ArtType artType = null;
            switch (option) {
                case 0:
                    return;
                case 1:
                    artType = ArtType.BOOKS;
                    break;
                case 2:
                    artType = ArtType.MOVIES;
                    break;
                case 3:
                    artType = ArtType.SERIES;
                    break;
                default:
                    break;
            }
            if (artType != null)
                showConsumables(artType);
        }
    }

    private void seeStat() {
        int option = -1;
        while (true) {
            clearScreen();
            int thour = timeTracker.getBookHour() + timeTracker.getMovieHour() + timeTracker.getSeriesHour();
            printConsole("Total Consumption of Time: " + String.valueOf(thour % 24) + "h");
            printConsole("Individual Consumption of Time:\n" +
                    "\t1. Books: " + String.valueOf(timeTracker.getBookHour() % 24) + "h\n" +
                    "\t2. Movies: " + String.valueOf(timeTracker.getMovieHour() % 24) + "h\n" +
                    "\t2. Series: " + String.valueOf(timeTracker.getSeriesHour() % 24) + "h\n");

            printConsole("\nTotal Consumption of Day: " + String.valueOf(thour / 24) + "d");
            printConsole("Individual Consumption of Day:\n" +
                    "\t1. Books: " + String.valueOf(timeTracker.getBookHour() / 24) + "d\n" +
                    "\t2. Movies: " + String.valueOf(timeTracker.getMovieHour() / 24) + "d\n" +
                    "\t2. Series: " + String.valueOf(timeTracker.getSeriesHour() / 24) + "d\n");
            float avgRating = 0, avgRatingB = 0, avgRatingM = 0, avgRatingS = 0;
            int tc = 0, tcB = 0, tcM = 0, tcS = 0;
            for (int i = 0; i < ArtList.size(); i++) {
                avgRating += ArtList.get(i).getRating();
                tc++;
                if (ArtList.get(i).getType() == ArtType.BOOKS) {
                    avgRatingB += ArtList.get(i).getRating();
                    tcB++;
                } else if (ArtList.get(i).getType() == ArtType.MOVIES) {
                    avgRatingM += ArtList.get(i).getRating();
                    tcM++;
                } else if (ArtList.get(i).getType() == ArtType.SERIES) {
                    avgRatingS += ArtList.get(i).getRating();
                    tcS++;
                }
            }
            printConsole("\nAverage Rating: " + String.valueOf(avgRating / ArtList.size()));
            printConsole("Average Rating of Each Type:\n" +
                    "\t1. Books: " + String.valueOf(avgRatingB / tcB) + "\n" +
                    "\t2. Movies: " + String.valueOf(avgRatingM / tcM) + "\n" +
                    "\t2. Series: " + String.valueOf(avgRatingS / tcS) + "\n");

            printConsole("\nTotal Consumables: " + String.valueOf(tc));
            printConsole("Total Consumables of Each Type:\n" +
                    "\t1. Books: " + String.valueOf(tcB) + "\n" +
                    "\t2. Movies: " + String.valueOf(tcM) + "\n" +
                    "\t2. Series: " + String.valueOf(tcS) + "\n");
            printConsole("\nEnter 0 to Go Back");
            option = readIntConsole();
            if (option == 0) return;
        }
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

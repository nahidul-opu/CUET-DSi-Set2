package com.cuet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Art implements Serializable {
    private static final long serialVersionUID = 42L;
    private String name;
    private Date startDate, endDate;
    private int consumptionInHour, consumptionInDay;
    private float rating;
    private ArtType artType;
    public static int BookHour, MovieHour, SeriesHour;

    Art(String name, float rating, ArtType artType) {
        this.name = name;
        startDate = new Date();
        endDate = null;
        consumptionInDay = 0;
        consumptionInHour = 0;
        this.rating = rating;
        this.artType = artType;
    }

    public String getName() {
        return name;
    }

    public void show(int i) {
        System.out.format("%6d%20s%6d%6d%8s", i, name, consumptionInDay, consumptionInHour, String.valueOf(rating));
        Main.printConsole("\n");
    }
    public void showAll(int i) {
        String ed = "";
        if(endDate!=null)
        {
            ed = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        }
        System.out.format("%6s%20s%8s%15s%6s%6s%8s%15s", i, name, artType.toString(),new SimpleDateFormat("dd/MM/yyyy").format(startDate), consumptionInDay, consumptionInHour, String.valueOf(rating),ed);
        Main.printConsole("\n");
    }
    public ArtType getType()
    {
        return artType;
    }

    public void showDetails() {
        Main.printConsole("Name: " + name);
        Main.printConsole("Rating: " + String.valueOf(rating));
        Main.printConsole("Start Date: " + new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        String ed = "";
        if (endDate != null) {
            ed = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        }
        Main.printConsole("End Date: " + ed);
        Main.printConsole("Total Consumption in Hours: " + String.valueOf(consumptionInHour));
        Main.printConsole("Total Consumption in Days: " + String.valueOf(consumptionInDay));
    }
    public boolean isEditable()
    {
        return endDate==null;
    }
    public void addHours(int hours)
    {
        updateTypeHour(hours);
        consumptionInHour = consumptionInHour +hours;
        consumptionInDay = consumptionInDay + consumptionInHour/24;
        consumptionInHour = consumptionInHour%24;
    }
    public  void  addDay()
    {
        consumptionInDay ++;
        updateTypeHour(24);
    }
    public  void  updateRating(float rating)
    {
        this.rating = rating;
    }
    private void updateTypeHour(int hour)
    {
        if(this.artType==ArtType.BOOKS) BookHour+=hour;
        else if(this.artType==ArtType.MOVIES) MovieHour+=hour;
        else if(this.artType==ArtType.SERIES) SeriesHour+=hour;
    }
}

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
    Art(String name, float rating, ArtType artType)
    {
        this.name = name;
        startDate = new Date();
        endDate = null;
        consumptionInDay = 0;
        consumptionInHour = 0;
        this.rating = rating;
        this.artType = artType;
    }

    public String getName()
    {
        return name;
    }

    public int showIfTypeMatch(int i, ArtType artType)
    {
        if(artType==this.artType)
        {
            System.out.format("%6d%20s%6d%6d%8s", i, name, consumptionInDay,consumptionInHour,String.valueOf(rating));
            Main.printConsole("\n");
            return 1;
        }
        return 0;
    }

    public void showDetails()
    {
        Main.printConsole("Name: "+name);
        Main.printConsole("Rating: "+String.valueOf(rating));
        Main.printConsole("Start Date: " + new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        String ed = "";
        if(endDate!=null)
        {
            ed = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
        }
        Main.printConsole("End Date: "+ed);
        Main.printConsole("Total Consumption in Hours: "+String.valueOf(consumptionInHour));
        Main.printConsole("Total Consumption in Days: "+String.valueOf(consumptionInDay));
    }
}

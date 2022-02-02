package com.cuet;

import java.io.*;

public class TimeTracker implements Serializable {
    private int BookHour, MovieHour, SeriesHour;
    private static final String FileName1 = "Database1.txt";

    public static TimeTracker getInstance() {
        return readTimeData();
    }



    public void updateTime(int hour, ArtType artType) {
        if (artType == ArtType.BOOKS) BookHour += hour;
        else if (artType == ArtType.MOVIES) MovieHour += hour;
        else if (artType == ArtType.SERIES) SeriesHour += hour;
    }

    private static TimeTracker readTimeData() {
        TimeTracker tt;
        try {
            FileInputStream fin = new FileInputStream(FileName1);
            ObjectInputStream ois = new ObjectInputStream(fin);
            tt = (TimeTracker) ois.readObject();
            fin.close();
        } catch (Exception e) {
            tt = new TimeTracker();
        }
        return tt;
    }

    public void save() {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(FileName1);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBookHour() {
        return BookHour;
    }

    public int getMovieHour() {
        return MovieHour;
    }

    public int getSeriesHour() {
        return SeriesHour;
    }
}

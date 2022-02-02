package com.cuet;

public class ArtFactory {
    public static Art getArt(String name, float rating, ArtType artType) {
        Art art = null;
        if (artType == ArtType.BOOKS)
            art = new Books(name, rating, artType);
        else if (artType == ArtType.MOVIES)
            art = new Movies(name, rating, artType);
        else if (artType == ArtType.SERIES)
            art = new Series(name, rating, artType);
        return art;
    }
}

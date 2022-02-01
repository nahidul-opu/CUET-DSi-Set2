package com.cuet;

public class ArtFactory {
    public Art getArt(ArtType artType)
    {
        Art art;
        switch (artType){
            case BOOKS:
                art = new Books();
                break;
            case MOVIES:
                art =   new Movies();
                break;
            case SERIES:
                art =  new Series();
                break;
            default:
                art=null;
                break;
        }
        return art;
    }
}

package com.dom_gallo;

import java.util.ArrayList;

public class TwoDimPoint {
    private int x;
    private int y;
    private String value;
    public boolean isBomb;
    public boolean isMarked;
    public boolean isExplored;

    public TwoDimPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.value = ".";
        this.isBomb = false;
        this.isMarked = false;
        this.isExplored = true;
    }
//    public TwoDimPoint(int x, int y, String value)
//    {
//        this(x,y);
//        if (value != null)
//        {
//            this.value = value;
//        } else
//        {
//            this.value  = "";
//        }
//
//    }
//    public TwoDimPoint(int x, int y, int value)
//    {
//        this(x,y);
//        this.value = String.valueOf(value);
//    }

    public ArrayList<TwoDimPoint> getPointsSurrounding(Board gameBoard)
    {
        ArrayList<TwoDimPoint> pointsSurrounding = new ArrayList<TwoDimPoint>();

        // Find raw coordinates of points surround this object
        // Search Board for those point objects
        // add to array list and return
        return pointsSurrounding;
    }

    public int getX() {
        return x + 1;
    }

    public int getY() {
        return y + 1;
    }
    public int getXIndex()
    {
        return x;
    }
    public int getYIndex()
    {
        return y;
    }

    public String getValue() {
        return value;
    }
    public boolean isMarked()
    {
        return this.isMarked;
    }
    public boolean isBomb()
    {
        return this.isBomb;
    }
    public boolean isBombAndMarked()
    {
        return this.isMarked() && this.isBomb();
    }
    public void toggleIsMarked()
    {
        this.isMarked = !this.isMarked;
    }
    public void toggleIsExplored()
    {
        this.isExplored = !this.isExplored;
        this.setValue("/");

    }
    private void setValue(String val)
    {
        this.value = val;
    }
    public void setBomb(){
        this.setValue("X");
        this.isMarked = true;
    }
}

package com.dom_gallo;

import java.util.ArrayList;

public class TwoDimPoint
{
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

    public ArrayList<TwoDimPoint> getPointsSurrounding(Board gameBoard)
    {
        ArrayList<TwoDimPoint> pointsSurrounding = new ArrayList<TwoDimPoint>();

        int startingX = this.getXIndex() - 1; // CONSTANT
        int startingY = this.getYIndex() - 1; // CONSTANT

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int newX = startingX + i;
                int newY = startingY + j;

                // We do not need to check ourselves.
                if (newX == this.getX() - 1 && newY == this.getY() - 1)
                {
                    continue;
                }


                // this protects against going out of bounds when finding surrounding points.
                if (this.isIndexOutOfBounds(gameBoard.getNumOfRows(), gameBoard.getNumOfColumns(), newX, newY))
                {
                    continue;
                }
                TwoDimPoint adjacentPoint = gameBoard.getPointAt(newX, newY);
                pointsSurrounding.add(adjacentPoint);
            }
        }
        return pointsSurrounding;
    }

    private boolean isIndexOutOfBounds(int numOfRows, int numOfColumns, int x, int y)
    {
        if (x < 0 || x > numOfRows - 1 || y < 0 || y > numOfColumns - 1)
        {
            return true;
        }
        return false;
    }

    public int getX()
    {
        return x + 1;
    }

    public int getY()
    {
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

    public String getValue()
    {
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
    public void setAsBomb(){
        this.setValue("X");
        this.isMarked = true;
    }
}

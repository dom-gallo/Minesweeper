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
    private int numOfBombsSurrounding = 0;
    private boolean isInitialMove = false;
    public TwoDimPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.value = ".";
        this.isBomb = false;
        this.isMarked = false;
        this.isExplored = false;
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
    public boolean getIsMarked()
    {
        return this.isMarked;
    }
    public void toggleIsBomb()
    {
        this.isBomb = true;
    }
    public boolean isBombAndMarked()
    {
        return this.isMarked && this.isBomb;
    }
    public void toggleIsMarked()
    {
        System.out.println("isMarked: " + this.isMarked);
        this.isMarked = !this.isMarked;
        if (this.isMarked)
        {
            this.setValue("*");
        } else {
            this.setValue(".");
        }
        System.out.println("Value of this point is: " + this.getValue());
    }
    public void toggleIsExplored()
    {
        this.isExplored = true;
        int numOfSurroundingBombs = this.getNumOfBombsSurrounding();
        if (isExplored && numOfSurroundingBombs > 0)
        {
            this.setValue(String.valueOf(numOfSurroundingBombs));
            return;
        }

        this.setValue("/");

    }
    private void setValue(String val)
    {
        this.value = val;
    }
    public void setAsBomb(){
        this.toggleIsBomb();
        this.setValue(".");
//        this.isMarked = true;
    }
    public int getNumOfBombsSurrounding() {
        return numOfBombsSurrounding;
    }

    public void setNumOfBombsSurrounding(int numOfBombsSurrounding) {
        this.numOfBombsSurrounding = numOfBombsSurrounding;
    }
    public String toString()
    {
        return String.valueOf("X:"+this.getX()+" Y:"+this.getY()+" isBomb:"+this.isBomb+" numOfBombsSurround:"+this.getNumOfBombsSurrounding());
    }
    public void setIsInitialMove()
    {
        this.isInitialMove = true;
    }
    public boolean getIsInitialMove()
    {
        return this.isInitialMove;
    }
    public void revealBombForLose()
    {
        if (this.isBomb)
        {
            this.setValue("X");
        }
    }
    public void incrementNumOfBombsSurrounding()
    {
        this.setNumOfBombsSurrounding(this.getNumOfBombsSurrounding() + 1);
    }
}

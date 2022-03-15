package com.dom_gallo;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<TwoDimPoint>> gameBoard = new ArrayList<ArrayList<TwoDimPoint>>();
    private ArrayList<TwoDimPoint> bombLocations = new ArrayList<>();


    private int numOfRows;
    private int numOfColumns;
    private int numOfMines;

    public Board(int numOfRows, int numOfColumns)
    {
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        this.buildGameBoard(numOfRows, numOfColumns);
    }

    public void buildGameBoard(int numOfRows, int numOfColumns)
    {
        for (int i = 0; i < numOfColumns; i++)
        {
            this.gameBoard.add(i, new ArrayList<>());
            for (int j = 0; j < numOfRows; j++)
            {
                TwoDimPoint newPoint = new TwoDimPoint(j, i);
                this.gameBoard.get(i).add(j, newPoint);
            }
        }
    }
    public void addBombsToBoard()
    {
        int minesAdded = 0;
        outerloop:
        while (getNumOfMines() > minesAdded)
        {
            for (int i = 0; i < this.numOfRows; i++)
            {
                for (int j =0; j < this.numOfColumns; j++)
                {
                    TwoDimPoint currentPoint = this.getPointAt(j, i);
                    if (Math.random() * 10 < 1 && !currentPoint.getValue().equalsIgnoreCase("X"))
                    {
                        if (getNumOfMines() == minesAdded)
                        {
                            break outerloop;
                        }
                        currentPoint.setAsBomb();
                        this.bombLocations.add(currentPoint);




                        minesAdded++;
                    }
                }
            }
        }
    }
    public void print()
    {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < this.gameBoard.size(); i++)
        {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < this.gameBoard.get(0).size(); j++)
            {
                System.out.print(this.gameBoard.get(i).get(j).getValue());
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("-|---------|");
    }
    public void doCommandAt(PlayerAction command, int xCoordinateIndex, int yCoordinateIndex)
    {
        TwoDimPoint point = this.getPointAt(xCoordinateIndex, yCoordinateIndex);

        if (command == PlayerAction.FREE)
        {
            point.toggleIsExplored();
        } else if (command == PlayerAction.MINE )
        {

        }
    }

    public int getNumOfMines()
    {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines)
    {
        this.numOfMines = numOfMines;
    }
    public TwoDimPoint getPointAt(int x, int y)
    {
        return this.gameBoard.get(y).get(x);
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfColumns() {
        return numOfColumns;
    }
}

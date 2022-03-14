package com.dom_gallo;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<TwoDimPoint>> gameBoard = new ArrayList<ArrayList<TwoDimPoint>>();
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
                TwoDimPoint newPoint = new TwoDimPoint(i, j);
                this.gameBoard.get(i).add(j, newPoint);
            }
        }
    }
    public void addBombsToBoard()
    {

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

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }
    public TwoDimPoint getPointAt(int x, int y)
    {
        return this.gameBoard.get(x).get(y);
    }
}

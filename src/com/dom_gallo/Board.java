package com.dom_gallo;

import java.util.ArrayDeque;
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

    /**
     * TODO: Change method so a bomb cannot be placed at the initial moves location
     *
     */
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

                    if (currentPoint.getIsInitialMove())
                    {
                        continue;
                    }

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
        this.identifyAndUpdateLocationsSurroundingBombs();
    }
    private void identifyAndUpdateLocationsSurroundingBombs()
    {
        // For all bombs on the board
        for (TwoDimPoint bomb: this.bombLocations)
        {
            ArrayList<TwoDimPoint> pointsAroundBomb = bomb.getPointsSurrounding(this);
            // Find the points around that bomb
            // If a surrounding point is a bomb, do nothing
            // If a surrounding point is a free space, update the value at that point
            // to the number of bombs that surround it
            for (TwoDimPoint adjacentPoint: pointsAroundBomb)
            {
                // If a surrounding point is a bomb
                if (adjacentPoint.isBomb)
                {
                    continue;
                } else
                {
                    adjacentPoint.setNumOfBombsSurrounding(adjacentPoint.getNumOfBombsSurrounding() + 1);
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
    public GameState doCommandAt(PlayerAction command, int xCoordinateIndex, int yCoordinateIndex)
    {
        GameState newGameState = GameState.RUNNING;

        TwoDimPoint point = this.getPointAt(xCoordinateIndex, yCoordinateIndex);

        System.out.println("Executing command for point at X:"+point.getX()+" Y:"+point.getY()+" ISBOMB:"+point.isBomb);
        switch (command)
        {
            case FREE ->
                    {
                        if (point.isExplored)
                        {
                            break;
                        }
                        if (point.isBomb)
                        {
                            newGameState = GameState.LOSE;
                            break;
                        }
                        if (point.getNumOfBombsSurrounding() > 0)
                        {
                            point.toggleIsExplored();
                        }
//                        point.toggleIsExplored();
                        exploreBoard(point);
                        break;
                    }
            case MINE ->
                    {
                        point.toggleIsMarked();
                        break;
                    }
        }
        return newGameState;

    }
    private void exploreBoard(TwoDimPoint startingPoint)
    {
        ArrayDeque<TwoDimPoint> pointQueue = new ArrayDeque<TwoDimPoint>();
        pointQueue.add(startingPoint);

        while (pointQueue.size() > 0 )
        {
            // Take next point out of queue
            // check if the point is a location surrounding a bomb, or a free space.
            // if it is a location surrounding a bomb, then toggleIsExplored, and do not add any more points to the queue.
            // if the location is a free space, toggleIsExplored and add surrounding points to the queue.

            TwoDimPoint p = pointQueue.remove();

            if (p.isBomb || p.isExplored)
            {

                continue;
            }
            if (p.getNumOfBombsSurrounding() > 0)
            {
                p.toggleIsExplored();
                continue;
            }

            p.toggleIsExplored();

            ArrayList<TwoDimPoint> surroundingPointArray = p.getPointsSurrounding(this);

            for (TwoDimPoint sPoint: surroundingPointArray)
            {
                pointQueue.add(sPoint);
            }
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
    public ArrayList<TwoDimPoint> getBombLocations()
    {
        return this.bombLocations;
    }
    public void revealBombLocations(){
        for (TwoDimPoint bomb: this.getBombLocations())
        {
            bomb.revealBombForLose();
        }

    }
}

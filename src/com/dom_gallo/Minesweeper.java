package com.dom_gallo;

import java.util.Scanner;

public class Minesweeper {
    private Board gameBoard;
    protected GameState CurrentState;
    private Scanner sc;
    public Minesweeper(int numOfRows, int numOfColumns)
    {
        this.gameBoard = new Board(numOfRows, numOfColumns);
        this.CurrentState = GameState.START;
        this.sc = new Scanner(System.in);
    }

    public void Start()
    {
        while (this.getCurrentState() != GameState.EXIT)
        {
            switch (this.getCurrentState())
            {
                case START -> {
                    System.out.println("How many mines do you want on the field?");
                    int numOfMinesToAdd = sc.nextInt();
                    this.gameBoard.setNumOfMines(numOfMinesToAdd);
                    this.gameBoard.print();
                    this.handleFirstMove();
                    this.gameBoard.print();
//                    System.out.println("FIRST::: Current Game State is : " + this.getCurrentState().toString());
//                    this.setCurrentState(GameState.RUNNING);
                    break;
                }
                case RUNNING -> {
                    this.handleMove();
                    this.checkWin();
                    this.gameBoard.print();
//                    System.out.println("RUNNING::: Current Game State is : " + this.getCurrentState().toString());
                    break;
                }
                case WIN -> {
                    System.out.print("Congratulations! You found all the mines!");
                    this.setCurrentState(GameState.EXIT);
                }
                case LOSE -> {
                    this.doLose();
                    this.setCurrentState(GameState.EXIT);
                }
                case EXIT -> {
                    this.setCurrentState(GameState.EXIT);
                    break;
                }
            }
        }
    }

    /**
     * Checking for win conditions of the game
     * WIN CONDITIONS:
     * 1. All cells containing mines have been marked
     * 2. All cells not containing mines are explored.
     */

    public void doLose()
    {
        this.gameBoard.revealBombLocations();
        this.gameBoard.print();
        System.out.print("You stepped on a mine and failed");
    }

    public void checkWin()
    {
        boolean didWin = true;
        // Check that all mines have been marked
        for (TwoDimPoint bomb: this.gameBoard.getBombLocations())
        {
            if (!bomb.isMarked)
            {
                didWin = false;
            }
        }

        // Check to see if non-bomb locations have been marked
        for (int i = 0; i < this.gameBoard.getNumOfRows(); i++)
        {
            for (int j = 0; j < this.gameBoard.getNumOfColumns(); j++)
            {
                TwoDimPoint anyPoint = this.gameBoard.getPointAt(i , j );

                // current point is a bomb, skip this iteration.
                if (anyPoint.isBomb)
                {
                    continue;
                }
                // if a non-bomb location hasn't been safely explored, then we have not reached a
                // win condition
                if (!anyPoint.isExplored)
                {
                    didWin = false;
                }
            }
        }
        if (didWin)
        {
            this.setCurrentState(GameState.WIN);
        }
    }
    /**
     * We need to ensure that the first move never lands on a bomb,
     * so this method will take the user indicated coordinate and
     * generate the minefield such that no mines are on that specific
     * point.
     */

    public void handleFirstMove()
    {
        this.printInputMessage();
        int xCoordinateIndex = sc.nextInt() - 1;
        int yCoordinateIndex = sc.nextInt() - 1;

        String commandString = sc.next().toUpperCase();
        PlayerAction command = PlayerAction.valueOf(commandString);

        this.gameBoard.getPointAt(xCoordinateIndex, yCoordinateIndex).setIsInitialMove();

        this.gameBoard.addBombsToBoard();
        GameState newState = this.gameBoard.doCommandAt(command, xCoordinateIndex, yCoordinateIndex);


        this.setCurrentState(newState);
        // Put bombs on the board
        // this.gameBoard.addBombsToBoard();
    }

    public void handleMove()
    {
        this.printInputMessage();
        int xCoordinateIndex = sc.nextInt() - 1;

        int yCoordinateIndex = sc.nextInt() - 1;

        String commandString = sc.next().toUpperCase();
        PlayerAction command = PlayerAction.valueOf(commandString);

        GameState newState = this.gameBoard.doCommandAt(command, xCoordinateIndex, yCoordinateIndex);
        this.setCurrentState(newState);
    }
    private void printInputMessage()
    {
        System.out.println("Set/unset mines marks or claim a cell as free: ");
    }
    public Board getGameBoard() {
        return gameBoard;
    }

    public GameState getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(GameState currentState) {
        CurrentState = currentState;
    }

}

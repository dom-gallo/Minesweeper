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
                    System.out.println("FIRST::: Current Game State is : " + this.getCurrentState().toString());
//                    this.setCurrentState(GameState.RUNNING);
                    break;
                }
                case RUNNING -> {
                    this.handleMove();
                    this.gameBoard.print();
                    System.out.println("RUNNING::: Current Game State is : " + this.getCurrentState().toString());
                    break;
                }
                case EXIT -> {
                    this.setCurrentState(GameState.EXIT);
                    break;
                }
            }
        }
    }

    /**
     * We need to ensure that the first move never lands on a bomb,
     * so this method will take the user indicated coordinate and
     * generate the mine field such that no mines are on that specific
     * point.
     */

    public void handleFirstMove()
    {
        this.printInputMessage();
        int xCoordinateIndex = sc.nextInt() - 1;
        int yCoordinateIndex = sc.nextInt() - 1;

        String commandString = sc.next().toUpperCase();
        PlayerAction command = PlayerAction.valueOf(commandString);

        GameState newState = this.gameBoard.doCommandAt(command, xCoordinateIndex, yCoordinateIndex);
        this.gameBoard.addBombsToBoard();

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

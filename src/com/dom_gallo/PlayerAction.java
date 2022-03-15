package com.dom_gallo;

public enum PlayerAction {
    FREE("free"),
    MINE("mine");

    String inputName;
    PlayerAction(String s)
    {
        inputName = s;
    }
}
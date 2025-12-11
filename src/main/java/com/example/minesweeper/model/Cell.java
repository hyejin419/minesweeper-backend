package com.example.minesweeper.model;


public class Cell {
    public boolean mine;
    public boolean revealed;
    public boolean flagged;
    public int adjacent;


    public Cell() {}


    public Cell(boolean mine) {
        this.mine = mine;
    }
}
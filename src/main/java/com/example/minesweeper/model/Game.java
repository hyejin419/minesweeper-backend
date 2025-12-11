package com.example.minesweeper.model;


import java.time.Instant;
import java.util.UUID;


public class Game {
    public enum Status { PLAYING, WON, LOST }


    private UUID id;
    private int rows;
    private int cols;
    private int mines;
    private Cell[][] board;
    private Status status = Status.PLAYING;
    private Instant createdAt = Instant.now();
    private boolean firstMove = true;


    public Game(UUID id, int rows, int cols, int mines, Cell[][] board) {
        this.id = id; this.rows = rows; this.cols = cols; this.mines = mines; this.board = board;
    }


    public UUID getId() { return id; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getMines() { return mines; }
    public Cell[][] getBoard() { return board; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public boolean isFirstMove() { return firstMove; }
    public void setFirstMove(boolean firstMove) { this.firstMove = firstMove; }
    public Instant getCreatedAt() { return createdAt; }
}
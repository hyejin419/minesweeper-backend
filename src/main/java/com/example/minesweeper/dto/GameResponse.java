package com.example.minesweeper.dto;


import com.example.minesweeper.model.Cell;
import com.example.minesweeper.model.Game;


import java.util.UUID;


public class GameResponse {
    public UUID id;
    public int rows;
    public int cols;
    public int mines;
    public String status; // PLAYING | WON | LOST
    public Cell[][] board; // 서버에서 공개 상태 그대로 전달


    public GameResponse(Game g){
        this.id = g.getId();
        this.rows = g.getRows();
        this.cols = g.getCols();
        this.mines = g.getMines();
        this.status = g.getStatus().name();
        this.board = g.getBoard();
    }
}
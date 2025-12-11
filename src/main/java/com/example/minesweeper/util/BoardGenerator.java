package com.example.minesweeper.util;


import com.example.minesweeper.model.Cell;


import java.util.Random;


public class BoardGenerator {
    public static Cell[][] createEmpty(int rows, int cols) {
        Cell[][] b = new Cell[rows][cols];
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) b[r][c] = new Cell(false);
        return b;
    }


    public static void placeMines(Cell[][] b, int mines, int safeR, int safeC) {
        int rows = b.length, cols = b[0].length, placed = 0;
        Random rand = new Random();
        while (placed < mines) {
            int r = rand.nextInt(rows), c = rand.nextInt(cols);
            if (b[r][c].mine) continue;
// 첫 클릭 안전 보장 (safeR,safeC 및 주변 8칸 금지)
            if (Math.abs(r-safeR)<=1 && Math.abs(c-safeC)<=1) continue;
            b[r][c].mine = true; placed++;
        }
// 인접 지뢰 수 계산
        int[] dr={-1,-1,-1,0,0,1,1,1};
        int[] dc={-1,0,1,-1,1,-1,0,1};
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) {
            int cnt=0; if (b[r][c].mine){b[r][c].adjacent=-1; continue;}
            for (int k=0;k<8;k++){
                int nr=r+dr[k], nc=c+dc[k];
                if (nr<0||nr>=rows||nc<0||nc>=cols) continue;
                if (b[nr][nc].mine) cnt++;}
            b[r][c].adjacent=cnt;
        }
    }
}
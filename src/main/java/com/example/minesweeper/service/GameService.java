package com.example.minesweeper.service;

import com.example.minesweeper.model.Cell;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.util.BoardGenerator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class GameService {
    private final Map<UUID, Game> store = new ConcurrentHashMap<>();


    public Game create(int rows, int cols, int mines) {
        if (rows<=0||cols<=0) throw new IllegalArgumentException("rows/cols > 0");
        if (mines<1 || mines>=rows*cols) throw new IllegalArgumentException("invalid mines count");
        UUID id = UUID.randomUUID();
        Cell[][] board = BoardGenerator.createEmpty(rows, cols);
        Game g = new Game(id, rows, cols, mines, board);
        store.put(id, g);
        return g;
    }


    public Game get(UUID id) { return opt(id).orElseThrow(() -> new NoSuchElementException("game not found")); }
    public Optional<Game> opt(UUID id) { return Optional.ofNullable(store.get(id)); }


    public Game reveal(UUID id, int r, int c) {
        Game g = get(id);
        if (g.getStatus()!= Game.Status.PLAYING) return g;
        Cell[][] b = g.getBoard();
        if (out(b,r,c) || b[r][c].flagged || b[r][c].revealed) return g;


// 첫 클릭이면 그 칸이 안전하도록 지뢰 배치
        if (g.isFirstMove()) {
            BoardGenerator.placeMines(b, g.getMines(), r, c);
            g.setFirstMove(false);
        }


        if (b[r][c].mine) {
            b[r][c].revealed = true;
            g.setStatus(Game.Status.LOST);
// 모든 지뢰 공개
            for (int i=0;i<b.length;i++) for(int j=0;j<b[0].length;j++) if (b[i][j].mine) b[i][j].revealed = true;
            return g;
        }


        floodReveal(b, r, c);
        if (checkWin(b, g.getMines())) g.setStatus(Game.Status.WON);
        return g;
    }


    public Game flag(UUID id, int r, int c) {
        Game g = get(id);
        if (g.getStatus()!= Game.Status.PLAYING) return g;
        Cell[][] b = g.getBoard();
        if (out(b,r,c) || b[r][c].revealed) return g;
        b[r][c].flagged = !b[r][c].flagged;
        return g;
    }


    private boolean out(Cell[][] b, int r, int c){
        return r<0||c<0||r>=b.length||c>=b[0].length;
    }


    private void floodReveal(Cell[][] b, int sr, int sc){
        Deque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{sr,sc});
        while(!dq.isEmpty()){
            int[] cur=dq.poll(); int r=cur[0], c=cur[1];
            if (out(b,r,c)) continue; Cell cell=b[r][c];
            if (cell.revealed || cell.flagged) continue; if (cell.mine) continue;
            cell.revealed = true;
            if (cell.adjacent==0){
                for (int dr=-1; dr<=1; dr++) for (int dc=-1; dc<=1; dc++){
                    if (dr==0&&dc==0) continue;
                    dq.add(new int[]{r+dr,c+dc});
                }
            }
        }
    }


    private boolean checkWin(Cell[][] b, int mines){
        int unrevealed=0;
        for (int r=0;r<b.length;r++) for (int c=0;c<b[0].length;c++) if (!b[r][c].revealed) unrevealed++;
        return unrevealed==mines; // 지뢰만 남았으면 승리
    }
}
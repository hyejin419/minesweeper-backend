package com.example.minesweeper.controller;


import com.example.minesweeper.dto.ActionRequest;
import com.example.minesweeper.dto.CreateGameRequest;
import com.example.minesweeper.dto.GameResponse;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;


@RestController
@RequestMapping("/api/games")
public class GameController {


private final GameService service;
public GameController(GameService service){ this.service = service; }


@PostMapping
public GameResponse create(@RequestBody CreateGameRequest req){
Game g = service.create(req.rows, req.cols, req.mines);
return new GameResponse(g);
}


@GetMapping("/{id}")
public GameResponse get(@PathVariable UUID id){
return new GameResponse(service.get(id));
}


@PostMapping("/{id}/reveal")
public GameResponse reveal(@PathVariable UUID id, @RequestBody ActionRequest req){
Game g = service.reveal(id, req.r, req.c);
return new GameResponse(g);
}


@PostMapping("/{id}/flag")
public GameResponse flag(@PathVariable UUID id, @RequestBody ActionRequest req){
Game g = service.flag(id, req.r, req.c);
return new GameResponse(g);
}


@ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
public ResponseEntity<?> handle(Exception e){
return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
}
}
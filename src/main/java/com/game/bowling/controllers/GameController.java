package com.game.bowling.controllers;


import com.game.bowling.dtos.GameStarterDto;
import com.game.bowling.models.Game;
import com.game.bowling.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    public GameService gameService;

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable Integer id) {
        return gameService.getGameById(id);
    }

    @PostMapping("/startGame")
    public Game startGame(@RequestBody GameStarterDto gameStarterDto){
        return gameService.startGame(gameStarterDto);
    }

    @DeleteMapping("/{id}")
    public void finishGame(@PathVariable Integer id) {
        gameService.endGame(id);
    }
}

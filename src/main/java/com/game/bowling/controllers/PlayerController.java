package com.game.bowling.controllers;

import com.game.bowling.dtos.ScoreUpdater;
import com.game.bowling.models.Game;
import com.game.bowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.game.bowling.services.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Integer id) {
        return playerService.getPlayerById(id);
    }

    @PutMapping("/{id}")
    public void updateScoreAndRound(@PathVariable Integer id, @RequestBody ScoreUpdater scoreUpdater) {
        Player player = playerService.getPlayerById(id);
        playerService.updateScoreAndRound(player, scoreUpdater.getRoundScore());
    }
}

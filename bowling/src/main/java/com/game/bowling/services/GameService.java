
package com.game.bowling.services;

import com.game.bowling.dtos.GameStarterDto;
import com.game.bowling.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.bowling.repositories.GameRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private NpcService npcService;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Integer id) {
        return gameRepository.findById(id).isPresent() ? gameRepository.findById(id).get() : null;
    }

    public void deleteGameById(Integer id) {
        gameRepository.deleteById(id);
    }

    public Game startGame(GameStarterDto gameStarterDto) {
        Game game = createGame(gameStarterDto.getNoOfRounds());
        addPlayersToGame(gameStarterDto, game);
        return game;
    }

    public Game createGame(int noOfRounds) {
        Game game = new Game();
        game.setNoOfRounds(noOfRounds);
        return gameRepository.saveAndFlush(game);
    }

    public void addPlayersToGame(GameStarterDto gameStarterDto, Game game) {
        AtomicReference<Integer> order = new AtomicReference<>(1);

        gameStarterDto.getPlayerNames().forEach(player -> playerService.createPlayer(player, order.getAndSet(order.get() + 1), game));
        gameStarterDto.getNpcNames().forEach(npc -> npcService.createNpc(npc, gameStarterDto.getNpcDifficulty(), order.getAndSet(order.get() + 1), game));
    }
}

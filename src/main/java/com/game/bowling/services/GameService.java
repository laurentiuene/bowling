
package com.game.bowling.services;

import com.game.bowling.dtos.GameStarterDto;
import com.game.bowling.exception.GameNotFinishedException;
import com.game.bowling.exception.GameNotFoundException;
import com.game.bowling.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
        if(gameRepository.findById(id).isPresent()) {
            return gameRepository.findById(id).get();
        } else {
            throw new GameNotFoundException("Game with id " + id + " was not found!");
        }
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

        gameStarterDto.getPlayerNames()
                .forEach(player -> playerService.createPlayer(player, order.getAndSet(order.get() + 1), game));
        gameStarterDto.getNpcNames()
                .forEach(npc -> npcService.createNpc(npc, gameStarterDto.getNpcDifficulty(), order.getAndSet(order.get() + 1), game));
    }

    public Game endGame(Integer id) {
        Game game;
        if(gameRepository.findById(id).isPresent()) {
            game = gameRepository.findById(id).get();
        } else {
            throw new GameNotFoundException("Game with id " + id + " was not found!");
        }
        int lastNpcRound = game.getNpcs().get(game.getNpcs().size() - 1).getNpcRound();

        if(lastNpcRound == game.getNoOfRounds()) {
            gameRepository.deleteById(id);
            return game;
        } else {
            throw new GameNotFinishedException("Game with id " + id + " is not finished!");
        }
    }

    @Scheduled(cron = "0 0 1 * * *") //everyday, at 1 am, deletes the games left open/unfinished
    public void deleteAllUnfinishedGames() {
        System.out.println("________________________________");
        gameRepository.deleteAll();
    }
}

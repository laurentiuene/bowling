package com.game.bowling.services;

import com.game.bowling.models.Game;
import com.game.bowling.models.Npc;
import com.game.bowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.bowling.repositories.PlayerRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private NpcService npcService;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id).isPresent() ? playerRepository.findById(id).get() : null;
    }

    public Player createPlayer(String name, int order, Game game) {
        Player player = new Player(name, order);
        player.setGame(game);
        player.setRound(0);
        player.setScore(0);
        return playerRepository.saveAndFlush(player);
    }

    public void updateScoreAndRound(Player player, Integer roundScore) {
        if(validateRound(player)) {
            player.setRound(player.getRound() + 1);
            player.setScore(player.getScore() + roundScore);
            playerRepository.saveAndFlush(player);
            npcService.verifyIfNpcNext(player);
        } else {
            //TODO: throw error and catch in common exception handler
        }
    }

    private Boolean validateRound(Player player) {

        if(player.getOrder() == 1 && player.getRound() == 0)
            return true;

        if(player.getOrder() == 1) {
            List<Npc> npcs = player.getGame().getNpcs();
            npcs.sort(Comparator.comparingInt(Npc::getOrder));
            if(player.getRound().equals(npcs.get(npcs.size() - 1).getNpcRound()))
                return true;
        }

        return player.getGame().getPlayers().stream()
                .filter(p -> p.getOrder() == player.getOrder() - 1)
                .anyMatch(p -> p.getRound() == player.getRound() + 1);
    }

}

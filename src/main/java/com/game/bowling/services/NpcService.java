package com.game.bowling.services;

import com.game.bowling.dtos.Difficulty;
import com.game.bowling.models.Game;
import com.game.bowling.models.Npc;
import com.game.bowling.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.bowling.repositories.NpcRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class NpcService {

    @Autowired
    private NpcRepository npcRepository;

    public List<Npc> getAllNpcs() {
        return npcRepository.findAll();
    }

    public Npc getNpcById(Integer id) {
        return npcRepository.findById(id).isPresent() ? npcRepository.findById(id).get() : null;
    }

    public Npc createNpc(String name, Difficulty difficulty, int order , Game game) {
        Npc npc = new Npc(name, order, difficulty);
        npc.setNpcScore(0);
        npc.setNpcRound(0);
        npc.setGame(game);
        return npcRepository.saveAndFlush(npc);
    }

    public void verifyIfNpcNext(Player player) {
        if(player.getGame().getNpcs().stream().anyMatch(n -> n.getOrder() == player.getOrder() + 1)) {
            List<Npc> npcs = player.getGame().getNpcs()
                    .stream().map(this::generateRoundForNpc)
                    .collect(Collectors.toList());
            npcRepository.saveAll(npcs);
        }
    }

    private Npc generateRoundForNpc(Npc npc) {
        int score;
        switch(npc.getDifficulty()) {
            case Medium:
                score = (int) Math.floor(Math.random()*(11-5+1)+5); //between 5-10
                break;
            case Hard:
                score = (int) Math.floor(Math.random()*(11-7+1)+7); //between 7-10
                break;
            default:
                score = (int) Math.floor(Math.random()*(11+1)+0); //between 0-10
        }
        npc.setNpcScore(npc.getNpcScore() + score);
        npc.setNpcRound(npc.getNpcRound() + 1);
        return npc;
    }
}

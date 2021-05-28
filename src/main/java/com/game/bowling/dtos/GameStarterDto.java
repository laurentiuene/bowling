package com.game.bowling.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameStarterDto {
    private List<String> playerNames;
    private List<String> npcNames;
    private Difficulty npcDifficulty;
    private Integer noOfRounds;
}

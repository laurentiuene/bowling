package com.game.bowling.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.game.bowling.dtos.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Npc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer npcId;

    @Column(name = "name")
    private String npcName;

    @Column(name = "score")
    private Integer npcScore;

    @Column(name = "round")
    private Integer npcRound;

    @Column(name = "throwing_order")
    private Integer order;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    public Npc(String name, int order, Difficulty difficulty) {
        this.npcName = name;
        this.order = order;
        this.difficulty = difficulty;
    }
}

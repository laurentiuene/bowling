package com.game.bowling.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer playerId;

    @Column(name = "name")
    private String playerName;

    private Integer score;
    private Integer round;

    @Column(name = "throwing_order")
    private Integer order;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "id_game", nullable = false)
    private Game game;

    public Player(String name, int order) {
        this.playerName = name;
        this.order = order;
    }
}

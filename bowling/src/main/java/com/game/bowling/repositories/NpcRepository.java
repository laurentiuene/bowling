package com.game.bowling.repositories;

import com.game.bowling.models.Npc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NpcRepository extends JpaRepository<Npc, Integer> {
}

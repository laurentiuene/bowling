package com.game.bowling.controllers;

import com.game.bowling.models.Npc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game.bowling.services.NpcService;

import java.util.List;

@RestController
@RequestMapping("/npcs")
public class NpcController {

    @Autowired
    private NpcService npcService;

    @GetMapping
    public List<Npc> getAllNpcs() {
        return npcService.getAllNpcs();
    }

    @GetMapping("/{id}")
    public Npc getNpc(@PathVariable Integer id) {
        return npcService.getNpcById(id);
    }
}

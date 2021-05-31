package com.game.bowling;

import com.game.bowling.models.Game;
import com.game.bowling.services.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GameService gameService;

    @Test
    public void getAllGames() throws Exception {
        Game game = gameService.getAllGames().get(0); //just one game in database left for testing purposes
        mvc.perform(MockMvcRequestBuilders.get("/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gameId").value(game.getGameId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].noOfRounds").value(game.getNoOfRounds()));
    }
}
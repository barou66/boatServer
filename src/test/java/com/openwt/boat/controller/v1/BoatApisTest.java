package com.openwt.boat.controller.v1;

import com.openwt.boat.service.BoatService;
import com.openwt.boat.service.model.Boat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BoatApis.class)
@ActiveProfiles("test")
class BoatApisTest {

    private final String URL = "/api/v1/boats";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoatService boatService;

    @Test
    @WithMockUser()
    void createBoatShouldReturnBoat() throws Exception {
        when(this.boatService.createBoat(any(Boat.class))).thenReturn(getBoats().get(0));
        JSONObject request = new JSONObject();
        request.put("name", "Barge");
        request.put("description", "Barge description");
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Barge"))
                .andExpect(jsonPath("$.description").value("Barge description"));
    }

    @Test
    @WithMockUser()
    void updateBoatShouldReturnBoat() throws Exception {
        when(this.boatService.updateBoat(eq(1), any(Boat.class))).thenReturn(Boat.builder()
                .id(1)
                .name("Barge Update")
                .description("Barge description Update")
                .build());
        JSONObject request = new JSONObject();
        request.put("name", "Barge Update");
        request.put("description", "Barge description Update");
        mvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Barge Update"))
                .andExpect(jsonPath("$.description").value("Barge description Update"));
    }

    @Test
    @WithMockUser()
    void findAllShouldReturnAllBoats() throws Exception {
        when(this.boatService.findAllBoat()).thenReturn(getBoats());

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boats.length()").value(2));
    }

    @Test
    @WithMockUser()
    void findOneShouldReturnValidBoat() throws Exception {
        Mockito.when(this.boatService.findBoatById(1))
                .thenReturn(getBoats().get(0));

        mvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Barge"))
                .andExpect(jsonPath("$.description").value("Barge description"));
    }

    @Test
    @WithMockUser()
    void deleteBoat() throws Exception {
        mvc.perform(delete(URL + "/1"))
                .andExpect(status().isNoContent());
        verify(boatService, times(1)).deleteBoatById(1);
    }


    private List<Boat> getBoats() {
        return List.of(Boat.builder()
                .id(1)
                .name("Barge")
                .description("Barge description")
                .build(), Boat.builder()
                .id(2)
                .name("Boita")
                .description("Barge description")
                .build());
    }

}
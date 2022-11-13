package com.openwt.boat.mapper;

import com.openwt.boat.controller.dto.BoatDto;
import com.openwt.boat.controller.dto.BoatInput;
import com.openwt.boat.controller.dto.BoatResponse;
import com.openwt.boat.repository.entity.BoatDao;
import com.openwt.boat.service.model.Boat;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoatMapperTest {
    private static Boat getBoatOne() {
        return Boat.builder()
                .id(1)
                .name("Boat 1")
                .description("description of boat1")
                .build();
    }

    @Test
    void modelToEntity() {
        assertNull(BoatMapper.modelToEntity(null));

        BoatDao boatDao = BoatMapper.modelToEntity(getBoatOne());

        assertNotNull(boatDao);
        assertEquals(1, boatDao.getId()
                .intValue());
        assertEquals("Boat 1", boatDao.getName());
        assertEquals("description of boat1", boatDao.getDescription());
    }

    @Test
    void entityToModel() {
        assertNull(BoatMapper.entityToModel(null));

        Boat boat = BoatMapper.entityToModel(BoatDao.builder()
                .id(1)
                .name("Boat 1")
                .description("description of boat1")
                .build());

        assertNotNull(boat);
        assertEquals(1, boat.getId()
                .intValue());
        assertEquals("Boat 1", boat.getName());
        assertEquals("description of boat1", boat.getDescription());
    }

    @Test
    void inputToModel() {
        assertNull(BoatMapper.inputToModel(null));

        Boat boat = BoatMapper.inputToModel(BoatInput.builder()
                .name("Boat 1")
                .description("description of boat1")
                .build());
        assertNotNull(boat);
        assertNull(boat.getId());
        assertEquals("Boat 1", boat.getName());
        assertEquals("description of boat1", boat.getDescription());
    }

    @Test
    void modelToDto() {
        assertNull(BoatMapper.modelToDto(null));

        BoatDto boatDto = BoatMapper.modelToDto(getBoatOne());

        assertNotNull(boatDto);
        assertEquals(1, boatDto.getId()
                .intValue());
        assertEquals("Boat 1", boatDto.getName());
        assertEquals("description of boat1", boatDto.getDescription());
    }

    @Test
    void boatsToResponse() {
        BoatResponse boatResponse = BoatMapper.boatsToResponse(null);

        assertNotNull(boatResponse);
        assertTrue(CollectionUtils.isEmpty(boatResponse.getBoats()));

        boatResponse = BoatMapper.boatsToResponse(List.of(getBoatOne()));
        assertNotNull(boatResponse);
        assertTrue(CollectionUtils.isNotEmpty(boatResponse.getBoats()));
        List<BoatDto> boats = boatResponse.getBoats();
        assertEquals(1, boats.size());
        assertEquals(1, boats.get(0)
                .getId()
                .intValue());
        assertEquals("Boat 1", boats.get(0)
                .getName());
        assertEquals("description of boat1", boats.get(0)
                .getDescription());
    }
}
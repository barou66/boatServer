package com.openwt.boat.service;

import com.openwt.boat.exception.BusinessException;
import com.openwt.boat.exception.ResourceNotFoundException;
import com.openwt.boat.repository.BoatDaoRepository;
import com.openwt.boat.repository.entity.BoatDao;
import com.openwt.boat.service.model.Boat;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class BoatServiceImplTest {

    @Autowired
    BoatService boatService;
    @MockBean
    private BoatDaoRepository boatDaoRepository;

    private static BoatDao getBoat15() {
        return BoatDao.builder()
                .id(15)
                .name("boat15")
                .description("description of boat 15")
                .build();
    }

    @Test
    void createBoat_OK() {
        //Arr
        doReturn(getBoat15()).when(boatDaoRepository)
                .save(any(BoatDao.class));

        //Act
        Boat boat = boatService.createBoat(Boat.builder()
                .name("boat15")
                .description("description of boat 15")
                .build());

        //Assert
        assertNotNull(boat);
        assertEquals("boat15", boat.getName());
        assertEquals("description of boat 15", boat.getDescription());
    }

    @Test
    void createBoat_MissingName_KO() {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> boatService.createBoat(
                Boat.builder()
                        .description("description of boat 15")
                        .build()));
        Assertions.assertEquals("name.is.empty", exception.getMessage());
    }

    @Test
    void createBoat_MissingDescription_KO() {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> boatService.createBoat(
                Boat.builder()
                        .name("boat15")
                        .build()));
        Assertions.assertEquals("description.is.empty", exception.getMessage());
    }

    @Test
    void updateBoat() {
        //Arrange
        doReturn(Optional.of(getBoat15())).when(boatDaoRepository)
                .findById(15);
        doReturn(BoatDao.builder()
                .id(15)
                .name("boat15 Update name")
                .description("description of boat 15 update")
                .build()).when(boatDaoRepository)
                .save(any(BoatDao.class));

        //Act
        Boat boat = boatService.updateBoat(15, Boat.builder()
                .name("boat15 Update name")
                .description("description of boat 15 update")
                .build());
        //Assert
        assertNotNull(boat);
        assertEquals("boat15 Update name", boat.getName());
        assertEquals("description of boat 15 update", boat.getDescription());
    }

    @Test
    void findBoatById_OK() {
        //Arrange
        doReturn(Optional.of(getBoat15())).when(boatDaoRepository)
                .findById(15);

        //Act
        Boat boat = boatService.findBoatById(15);

        //Assert
        assertNotNull(boat);
        assertEquals("boat15", boat.getName());
        assertEquals("description of boat 15", boat.getDescription());
    }

    @Test
    void findBoatById_KO() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> boatService.findBoatById(12));
        Assertions.assertEquals("boat.not.found", resourceNotFoundException.getMessage());
    }

    @Test
    void findAllBoat() {
        //Arrange
        doReturn(List.of(BoatDao.builder()
                .id(12)
                .name("boat 12")
                .description("description of boat12")
                .build(), BoatDao.builder()
                .id(13)
                .name("boat 13")
                .description("description of boat13")
                .build())).when(boatDaoRepository)
                .findAll();

        //Act
        List<Boat> boats = boatService.findAllBoat();

        //Assert
        assertTrue(CollectionUtils.isNotEmpty(boats));
        assertEquals(2, boats.size());
        assertEquals("boat 12", boats.get(0)
                .getName());
        assertEquals("description of boat12", boats.get(0)
                .getDescription());
    }

    @Test
    void deleteBoatById() {
        //Arrange
        doReturn(Optional.of(getBoat15())).when(boatDaoRepository)
                .findById(15);
        //Act
        boatService.deleteBoatById(15);

        //Assert
        verify(boatDaoRepository, times(1)).deleteById(15);
    }

    @Test
    void deleteBoatById_KO() {
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> boatService.deleteBoatById(45));
        Assertions.assertEquals("boat.not.found", exception.getMessage());
    }
}
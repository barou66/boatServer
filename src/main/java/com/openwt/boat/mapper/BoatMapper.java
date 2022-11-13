package com.openwt.boat.mapper;

import com.openwt.boat.controller.dto.BoatDto;
import com.openwt.boat.controller.dto.BoatInput;
import com.openwt.boat.controller.dto.BoatResponse;
import com.openwt.boat.repository.entity.BoatDao;
import com.openwt.boat.service.model.Boat;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class BoatMapper {
    private BoatMapper() {
    }

    public static BoatDao modelToEntity(Boat boat) {
        if(Objects.isNull(boat)) {
            return null;
        }
        return BoatDao.builder()
                .id(boat.getId())
                .name(boat.getName())
                .description(boat.getDescription())
                .build();
    }

    public static Boat entityToModel(BoatDao boatDao) {
        if(Objects.isNull(boatDao)) {
            return null;
        }
        return Boat.builder()
                .id(boatDao.getId())
                .name(boatDao.getName())
                .description(boatDao.getDescription())
                .build();
    }

    public static Boat inputToModel(BoatInput input) {
        if(Objects.isNull(input)) {
            return null;
        }
        return Boat.builder()
                .name(input.getName())
                .description(input.getDescription())
                .build();
    }

    public static BoatDto modelToDto(Boat boat) {
        if(Objects.isNull(boat)) {
            return null;
        }
        return BoatDto.builder()
                .id(boat.getId())
                .name(boat.getName())
                .description(boat.getDescription())
                .build();
    }

    public static BoatResponse boatsToResponse(List<Boat> boats){
        if(CollectionUtils.isEmpty(boats)){
            return BoatResponse.builder()
                    .boats(Collections.emptyList())
                    .build();
        }
        return BoatResponse.builder()
                .boats(boats.stream().map(BoatMapper::modelToDto).collect(Collectors.toList()))
                .build();
    }

}

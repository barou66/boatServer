package com.openwt.boat.service.impl;

import com.openwt.boat.exception.BusinessException;
import com.openwt.boat.exception.ResourceNotFoundException;
import com.openwt.boat.mapper.BoatMapper;
import com.openwt.boat.repository.BoatDaoRepository;
import com.openwt.boat.repository.entity.BoatDao;
import com.openwt.boat.service.BoatService;
import com.openwt.boat.service.model.Boat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoatServiceImpl implements BoatService {
    private final BoatDaoRepository boatDaoRepository;

    public BoatServiceImpl(BoatDaoRepository boatDaoRepository) {
        this.boatDaoRepository = boatDaoRepository;
    }

    @Override
    public Boat createBoat(@NotNull Boat boat) {
        checkMandatoryValues(boat);
        return BoatMapper.entityToModel(this.boatDaoRepository.save(BoatMapper.modelToEntity(boat)));
    }

    @Override
    public Boat updateBoat(Integer id, Boat boat) {
        checkMandatoryValues(boat);
        BoatDao boatDao = this.boatDaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("boat.not.found"));
        boatDao.setName(boat.getName());
        boatDao.setDescription(boat.getDescription());

        return BoatMapper.entityToModel(this.boatDaoRepository.save(boatDao));
    }

    @Override
    public Boat findBoatById(Integer id) {
        Optional<BoatDao> boatOp = this.boatDaoRepository.findById(id);
        if(!boatOp.isPresent()) {
            return null;
        }
        return BoatMapper.entityToModel(boatOp.get());
    }

    @Override
    public List<Boat> findAllBoat() {
        return boatDaoRepository.findAll()
                .stream()
                .map(BoatMapper::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBoatById(Integer id) {
        this.boatDaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("boat.not.found"));
        boatDaoRepository.deleteById(id);
    }

    private void checkMandatoryValues(Boat boat) {
        if(StringUtils.isBlank(boat.getName())) {
            throw new BusinessException("name.is.empty");
        }
        if(StringUtils.isBlank(boat.getDescription())) {
            throw new BusinessException("description.is.empty");
        }
    }
}

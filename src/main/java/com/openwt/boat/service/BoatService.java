package com.openwt.boat.service;

import com.openwt.boat.service.model.Boat;

import java.util.List;

public interface BoatService {
    Boat createBoat(Boat boat);
    Boat updateBoat(Integer id,Boat boat);
    Boat findBoatById(Integer id);
    List<Boat> findAllBoat();
    void deleteBoatById(Integer id);
}

package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.CageDto;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;

import java.util.List;

public interface CageService {
    Cage createCage(CageDto cageDto);

    String updateCage (CageDto cageDto);

    String removeCage(String id);

    List<Cage> listCagesByZooArea(ZooArea zooArea);

    List<Cage> getAllCages();

    List<Cage> getCagesByDescriptionKeyword(String keyword);

    List<Cage> getCagesByCapacityDescending();
    List<Cage> getCagesByCapacityAscending();

    long countCages();
}

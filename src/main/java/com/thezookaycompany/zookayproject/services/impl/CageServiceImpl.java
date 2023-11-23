package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.CageDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.AnimalRepository;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.CageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class CageServiceImpl implements CageService {

    private static final String CAGE_ID_REGEX = "A\\d{4}";

    @Autowired
    private CageRepository cageRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Override
    public Cage createCage(CageDto cageDto) {
        if (!Pattern.matches(CAGE_ID_REGEX, cageDto.getCageID())) {
            throw new InvalidCageException("Invalid Cage ID format. It should match the pattern Axxxx.");
        }
        //Find cage
        if (cageRepository.findById(cageDto.getCageID()).isPresent()) {
            throw new InvalidCageException("This Cage ID has existed.");
        }

        ZooArea zooArea = zooAreaRepository.findById(cageDto.getZoo_AreaID()).orElse(null);
        if (zooArea == null) {
            throw new InvalidCageException("Zoo Area not found");
        }
        Cage cage = new Cage();
        // Create a new Cage entity
        cage.setCageID(cageDto.getCageID());
        cage.setDescription(cageDto.getDescription());
        cage.setCapacity(cageDto.getCapacity());
        cage.setZooArea(zooArea);

        // Save the Cage entity in the repository
        return cageRepository.save(cage);
    }

    @Override
    public String updateCage(CageDto cageDto) {
        if (!Pattern.matches(CAGE_ID_REGEX, cageDto.getCageID())) {
            return "Invalid Cage ID format. It should match the pattern 'Axxxx'.";
        }
        ZooArea zooArea = zooAreaRepository.findById(cageDto.getZoo_AreaID()).orElse(null);

        if (zooArea == null) {
            return "No Zoo Area included, please input available Zoo Area ID.";
        }

        Cage existingCage = cageRepository.findById(cageDto.getCageID()).orElse(null);

        if (existingCage == null) {
            return "Cage not found with ID: " + cageDto.getCageID();
        }
        existingCage.setDescription(cageDto.getDescription());
        existingCage.setCapacity(cageDto.getCapacity());
        existingCage.setZooArea(zooArea);

        cageRepository.save(existingCage);

        return "Cage updated successfully.";
    }

    @Override
    public String removeCage(String id) {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new InvalidCageException("Not found this Cage ID to delete."));
        Set<Animal> animalCage = cage.getCageAnimals();
        if (animalCage != null && animalCage.size() != 0) {
            return "This cage id " + id + " has a lot of animals, please try to delete these animals and again.";
        }
        cageRepository.delete(cage);

        return "Deleted cage id: " + cage.getCageID() + " successfully";
    }

    @Override
    public List<Cage> listCagesByZooArea(ZooArea zooArea) {
        return cageRepository.findCagesByZooArea(zooArea);
    }

    @Override
    public List<Cage> getAllCages() {
        return cageRepository.findAll();
    }

    @Override
    public List<Cage> getCagesByDescriptionKeyword(String keyword) {
        return cageRepository.findCagesByDescriptionContainingKeyword(keyword);
    }

    @Override
    public List<Cage> getCagesByCapacityDescending() {
        return cageRepository.findAllByCapacityDesc();
    }

    @Override
    public List<Cage> getCagesByCapacityAscending() {
        return cageRepository.findAllByCapacityAsc();
    }

    @Override
    public long countCages() {
        return cageRepository.count();
    }
}

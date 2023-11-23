package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.dto.AnimalSpeciesDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalService {
    Animal findAnimalByAnimalID(Integer animalId);
    String createAnimal(AnimalDto animalDto);
    String updateAnimal(AnimalDto animalDto);
    String removeAnimal(Integer id);
    AnimalResponse getAllAnimal();
    List<AnimalSpecies> getAllAnimalSpecies();
    Animal findAnimalWithSpeciesAndCage(Integer animalId);

    List<Animal> findByZooAreaID(String zooAreaID);

    String createAnimalSpecies(AnimalSpeciesDto animalSpeciesDto);
    String updateAnimalSpecies(AnimalSpeciesDto animalSpeciesDto);
    String removeAnimalSpecies(Integer id);

    AnimalSpecies findAnimalByAnimalSpeciesID(Integer speciesId);

    List<Animal> findAllByHeightAsc(String zooAreaId);
    List<Animal> findAllByHeightDesc(String zooAreaId);
    List<Animal> findAllByWeightAsc(String zooAreaId);
    List<Animal> findAllByWeightDesc(String zooAreaId);
    List<Animal> findAllByAgeAsc(String zooAreaId);
    List<Animal> findAllByAgeDesc(String zooAreaId);

    void uploadAnimalImage(Integer animalId, byte[] imageBytes, String format) throws IOException;
    void deleteAnimalImage(Integer animalId);
    byte[] getAnimalImageById(Integer animalId);

    long countAnimals();
    long countAnimalSpecies();

    List<Object[]> findAnimalImageAndNameByGroups(String groups);
    List<Animal> getAnimalsBySpeciesGroups(String groups);
    List<Animal> getAnimalsByName(String name);

}

package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
//    List<AnimalDto> findAnimalByCage(Cage cage);

    Animal findAnimalsByAnimalId(Integer animalId);
    @Query("SELECT a FROM Animal a JOIN FETCH a.species JOIN FETCH a.cage WHERE a.animalId = :animalId")
    Animal findAnimalWithSpeciesAndCage(@Param("animalId") Integer animalId);

    Animal findAnimalByName(String name);

    List<Animal> findAnimalsByCage_ZooArea_ZooAreaId(String zooAreaId);


    @Query("SELECT a.imageAnimal, a.name FROM Animal a WHERE a.species.groups = :groups")
    List<Object[]> findAnimalImageAndNameByGroups(String groups);

    @Query("SELECT a FROM Animal a WHERE a.species.groups = :groups")
    List<Animal> findAnimalsBySpeciesGroups(String groups);

    List<Animal> findAnimalsBySpecies_SpeciesId(Integer speciesId);

    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByHeightAsc(String zooAreaId);
    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByHeightDesc(String zooAreaId);

    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByWeightAsc(String zooAreaId);
    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByWeightDesc(String zooAreaId);

    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByAgeAsc(String zooAreaId);
    List<Animal> findAllByCage_ZooArea_ZooAreaIdOrderByAgeDesc(String zooAreaId);

    @Query("SELECT a FROM Animal a WHERE lower(a.name) LIKE lower(concat('%', :name, '%'))")
    List<Animal> findAnimalsByName(@Param("name") String name);

}

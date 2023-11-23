package com.thezookaycompany.zookayproject.repositories;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ZooAreaRepository extends JpaRepository<ZooArea , String> {
    ZooArea findZooAreaByZooAreaId (String zooAreaId);
    ZooArea findByDescriptionContainingIgnoreCase (String description);
    ZooArea getZooAreaByZooAreaId(String id);
}

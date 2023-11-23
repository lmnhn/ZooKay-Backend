package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.ZooNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZooNewsRepository extends JpaRepository<ZooNews, Integer> {
    List<ZooNews> findAllByOrderByDateCreatedDesc();
}

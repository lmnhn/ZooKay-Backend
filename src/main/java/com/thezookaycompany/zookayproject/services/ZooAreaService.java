package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.entity.ZooArea;

import java.util.List;

public interface ZooAreaService {
    List<ZooArea> findAllZooArea();
    ZooArea findZooAreaByZooAreaDes ( String description);
    ZooArea findZooAreaByZooAreaID (String zooAreaId);

    long countZooAreas();
}

package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.ZooAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZooAreaServiceImpl implements ZooAreaService {
    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Override
    public List<ZooArea> findAllZooArea() {
        return zooAreaRepository.findAll();
    }

    @Override
    public ZooArea findZooAreaByZooAreaDes(String description) {
        return zooAreaRepository.findByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public ZooArea findZooAreaByZooAreaID(String zooAreaId) {
        return zooAreaRepository.findZooAreaByZooAreaId(zooAreaId);
    }

    @Override
    public long countZooAreas() {
        return zooAreaRepository.count();
    }
}

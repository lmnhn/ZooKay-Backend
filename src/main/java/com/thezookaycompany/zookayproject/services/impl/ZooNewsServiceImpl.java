package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.ZooNewsDto;
import com.thezookaycompany.zookayproject.model.entity.ZooNews;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import com.thezookaycompany.zookayproject.repositories.ZooNewsRepository;
import com.thezookaycompany.zookayproject.services.ZooNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
public class ZooNewsServiceImpl implements ZooNewsService {

    @Autowired
    private ZooNewsRepository zooNewsRepository;
    @Autowired
    private EmployeesRepository employeesRepository;

    @Override
    public String postNews(ZooNewsDto zooNewsDto) {
        if (zooNewsDto.getContent().isEmpty() || zooNewsDto.getContent().length() > 255) {
            return "The zoo news' content must not be greater than 256 characters or empty";
        }

        if (zooNewsDto.getDescription().isEmpty() || zooNewsDto.getDescription().length() > 255) {
            return "The zoo news' description must not be greater than 256 characters or empty";
        }

        if (zooNewsDto.getEmpId() == null) {
            return "The Employee ID must not be empty or Employee ID hasn't existed";
        }

        ZooNews zooNews = new ZooNews();
        zooNews.setContent(zooNewsDto.getContent());
        zooNews.setDescription(zooNewsDto.getDescription());
        zooNews.setEmployeesNews(employeesRepository.getReferenceById(zooNewsDto.getEmpId()));
        zooNews.setDateCreated(Date.from(Instant.now()));

        zooNewsRepository.save(zooNews);

        return "You have posted news successfully";
    }

    @Override
    public String removeNews(ZooNewsDto zooNewsDto) {
        if (zooNewsDto.getNewsId() == null) {
            return "Cannot find news id";
        }
        ZooNews zooNews = zooNewsRepository.findById(zooNewsDto.getNewsId()).orElse(null);
        if (zooNews == null) {
            return "This news does not exist";
        }

        zooNewsRepository.delete(zooNews);
        return "Removed news successfully";
    }

    @Override
    public String updateNews(ZooNewsDto zooNewsDto) {
        if (zooNewsDto.getNewsId() == null) {
            return "Cannot find news id";
        }
        ZooNews zooNews = zooNewsRepository.findById(zooNewsDto.getNewsId()).orElse(null);
        if (zooNews == null) {
            return "This news does not exist";
        }

        zooNews.setDateCreated(Date.valueOf(zooNewsDto.getDateCreated()));
        zooNews.setContent(zooNewsDto.getContent());
        zooNews.setDescription(zooNewsDto.getDescription());

        zooNewsRepository.save(zooNews);
        return "Updated news successfully";
    }

    @Override
    public List<ZooNews> getNews() {
        return zooNewsRepository.findAll();
    }

    @Override
    public List<ZooNews> getNewestNews() {
        return zooNewsRepository.findAllByOrderByDateCreatedDesc();
    }
}

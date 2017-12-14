package com.octopus.service;

import com.octopus.pojo.ArriveCity;
import com.octopus.repository.ArriveCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:39
 */
@Service
public class ArriveCityService {

    @Autowired
    private ArriveCityRepository arriveCityRepository;

    public ArriveCity getArriveCityByCityName(String cityName){
        return arriveCityRepository.findByCityName(cityName);
    }

    public List<ArriveCity> getAll(){
        return arriveCityRepository.findAll();
    }
}

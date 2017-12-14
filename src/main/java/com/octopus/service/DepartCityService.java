package com.octopus.service;

import com.octopus.pojo.DepartCity;
import com.octopus.repository.DepartCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:11
 */
@Service
public class DepartCityService {
    @Autowired
    private DepartCityRepository departCityRepository;

    public DepartCity getDepartCityByCityName(String cityName){
        return departCityRepository.findByCityName(cityName);
    }
}

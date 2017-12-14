package com.octopus.repository;

import com.octopus.pojo.DepartCity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:05
 */
public interface DepartCityRepository extends JpaRepository<DepartCity,String>{

    DepartCity findByCityName(String cityName);
}

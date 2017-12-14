package com.octopus.repository;

import com.octopus.pojo.ArriveCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArriveCityRepository extends JpaRepository<ArriveCity,String> {
   ArriveCity findByCityName(String cityName);
}

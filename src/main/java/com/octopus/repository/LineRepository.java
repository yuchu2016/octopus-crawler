package com.octopus.repository;

import com.octopus.pojo.Line;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LineRepository extends JpaRepository<Line,String> {
    void deleteByDepartCityLikeAndDesCityLike(String depart, String desc);
}

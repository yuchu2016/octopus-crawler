package com.octopus.service;

import com.octopus.pojo.Line;
import com.octopus.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 12:14
 */
@Service
@Transactional
public class LineService {
    @Autowired
    private LineRepository lineRepository;

    public Line getLineById(String id){
        return lineRepository.findOne(id);
    }

    public void save(Line line){
        lineRepository.save(line);
    }

    public void deleteByDepartCityAndDescity(String depart,String desc){
        lineRepository.deleteByDepartCityLikeAndDesCityLike("%"+depart+"%","%"+desc+"%");
    }
}

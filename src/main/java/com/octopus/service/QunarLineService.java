package com.octopus.service;

import com.octopus.pojo.Line;
import com.octopus.repository.QunarLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-13
 * Time: 14:10
 */

@Service
public class QunarLineService {
    @Autowired
    private QunarLineRepository qunarLineRepository;

    public void save(Line line){
        qunarLineRepository.save(line);
    }
}

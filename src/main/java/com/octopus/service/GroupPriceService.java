package com.octopus.service;

import com.octopus.pojo.GroupPrice;
import com.octopus.repository.GroupPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 14:26
 */
@Service
public class GroupPriceService {
    @Autowired
    private GroupPriceRepository repository;

    public void save(GroupPrice groupPrice){
        repository.save(groupPrice);
    }
}

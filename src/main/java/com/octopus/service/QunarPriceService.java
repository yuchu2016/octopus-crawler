package com.octopus.service;

import com.octopus.pojo.GroupPrice;
import com.octopus.repository.QunarPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-13
 * Time: 16:11
 */
@Service
public class QunarPriceService {
    @Autowired
    private QunarPriceRepository qunarPriceRepository;

    public void save(GroupPrice priceEntity){
        qunarPriceRepository.save(priceEntity);
    }
}

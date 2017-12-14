package com.octopus.service;

import com.octopus.pojo.QunarSite;
import com.octopus.repository.QunarSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-14
 * Time: 8:31
 */
@Service
public class QunarSiteService {
    @Autowired
    private QunarSiteRepository qunarSiteRepository;

    public List<QunarSite> findAll(){
        return qunarSiteRepository.findAll();
    }
}

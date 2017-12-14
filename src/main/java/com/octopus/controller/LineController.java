package com.octopus.controller;

import com.octopus.service.LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 12:07
 */
@RestController
public class LineController {

    private final Logger logger = LoggerFactory.getLogger(LineController.class);
    @Autowired
    private LineService lineService;


}

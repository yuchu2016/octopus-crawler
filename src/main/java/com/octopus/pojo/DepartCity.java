package com.octopus.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:03
 */
@Entity
@Data
@Table(name = "T_Tuniu_departurecity")
public class DepartCity {

    @Column(name = "cityname")
    @Id
    private String cityName;
    @Column(name = "Shorthand")
    private String shortHand;
    @Column(name = "Citycode")
    private String cityCode;
    @Column(name = "Iswhole")
    private String IsWhole;
}

package com.octopus.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-11
 * Time: 13:36
 */
@Table(name = "T_Tuniu_Arrivecity")
@Entity
@Data
@ToString
public class ArriveCity {

    @Column(name = "cityname")
    @Id
    private String cityName;

    @Column(name = "citycode")
    private String cityCode;

    @Column(name = "iswhole")
    private String isWhole;

}

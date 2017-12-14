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
 * Time: 14:20
 */
@Entity
@Data
@Table(name = "t_qunar_groupprice")
public class GroupPrice {
    @Id
    private String Id;
    @Column(name = "lineid")
    private String lineId;

    @Column(name = "groupdate")
    private String groupDate;

    @Column(name = "Adulprice")
    private String AdultPrice;

    @Column(name = "childprice")
    private String childPrice;

}

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
 * Date: 2017-12-14
 * Time: 8:25
 */
@Entity
@Table(name = "t_qunar_site")
@Data
public class QunarSite {

    @Column(name = "siteName")
    private String siteName;
    @Column(name = "cityname")
    private String cityName;
    @Id
    @Column(name = "sortindex")
    private String sortIndex;
}

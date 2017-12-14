package com.octopus.Enum;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-13
 * Time: 13:24
 */
public enum Site {

    QUNAR("去哪儿","跟团游"),
    TUNIU("途牛","跟团游")

    ;

    private String siteName;

    private String typeName;

    Site(String siteName, String typeName) {
        this.siteName = siteName;
        this.typeName = typeName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

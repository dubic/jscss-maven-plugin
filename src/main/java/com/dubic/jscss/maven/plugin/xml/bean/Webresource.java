/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.jscss.maven.plugin.xml.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DUBEM
 */
@XmlRootElement(name = "Webresource")
@XmlAccessorType(XmlAccessType.FIELD)
public class Webresource {

    @XmlAttribute(name = "page")
    private String page;
    @XmlAttribute(name = "template")
    private String template;
    @XmlElement(name = "Aggregation")
    private List<Aggregation> aggregations;

    public List<Aggregation> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<Aggregation> aggregations) {
        this.aggregations = aggregations;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    

    public static void main(String[] args) {
        Webresource wr = new Webresource();
        List<Aggregation> list = new ArrayList<Aggregation>();
        Aggregation a1 = new Aggregation();
        a1.setAggregatedFileName("js/foundation-web-component.js");
        List<String> flist = new ArrayList<String>();
        flist.add("js/foundation/core/common.js");
        flist.add("js/foundation/keyboard/hungarian.js");
        flist.add("js/pegasus/function/login/auth/LoginIdPanel.js");
        a1.setFilePath(flist);

        Aggregation a2 = new Aggregation();
        a2.setAggregatedFileName("js/foundation-web-component-logout.js");
        List<String> flist2 = new ArrayList<String>();
        flist2.add("js/foundation/core/common.js");
        flist2.add("js/pegasus/function/logout/ActionOrder.js");
        flist2.add("js/pegasus/function/logout/LogoutController.js");
        a2.setFilePath(flist2);

        list.add(a1);
        list.add(a2);

        wr.setAggregations(list);

        JAXB.marshal(wr, System.out);
    }
}

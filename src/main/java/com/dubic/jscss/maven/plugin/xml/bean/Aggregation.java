/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.jscss.maven.plugin.xml.bean;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DUBEM
 */
@XmlRootElement(name = "Aggregation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Aggregation {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "aggregatedFileName")
    private String aggregatedFileName;
    @XmlElement(name = "FilePath")
    private List<String> filePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAggregatedFileName() {
        return aggregatedFileName;
    }

    public void setAggregatedFileName(String aggregatedFileName) {
        this.aggregatedFileName = aggregatedFileName;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

}


package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome;

import org.apache.ibatis.type.Alias;

@Alias("ChartConfigBar")
public class ChartConfigBar {

    private String name;
    private String groups;
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}

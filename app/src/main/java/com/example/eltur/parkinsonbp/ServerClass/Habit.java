package com.example.eltur.parkinsonbp.ServerClass;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Eltur on 12/06/2017.
 */

public class Habit {

    private String habitName;

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    private Long groupID;
    public Habit() {
    }

    private List<SubMenu> subMenus = new ArrayList<>();

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    @Override
    public String toString() {
        return format("{habitName:\"%s\",groupID:\"%d\",subMenus:%s}",habitName,groupID,subMenus);
    }

    public List<SubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }
}

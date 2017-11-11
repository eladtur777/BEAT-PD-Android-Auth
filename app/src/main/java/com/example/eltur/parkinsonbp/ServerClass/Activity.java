package com.example.eltur.parkinsonbp.ServerClass;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Eltur on 28/05/2017.
 */

public class Activity  {

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<SubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenu> subMenus) {
        this.subMenus = subMenus;
    }

    private String activityName;
    private List<SubMenu> subMenus = new ArrayList<>();

    @Override
    public String toString(){
        return String.format("{activityName:\"%s\", subMenus:%s}", activityName, subMenus);
    }


    public Activity(String activityN,List<SubMenu> subM) {

     this.setActivityName(activityN);
        this.setSubMenus(subM);


     }
public Activity()
{

}

}


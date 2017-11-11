package com.example.eltur.parkinsonbp.ServerClass;

import static java.lang.String.format;

/**
 * Created by Eltur on 24/08/2017.
 */

public class ActivityUpdate {


    private Long activityUpdateID;
    private String activityName;
    private String activityDescription;

    public ActivityUpdate(){};

    @Override
    public String toString(){
        return format("{activityName:\"%s\",activityDescription:\"%s\"}",activityName,activityDescription);
    }

    public Long getActivityUpdateID() {
        return activityUpdateID;
    }

    public void setActivityUpdateID(Long activityUpdateID) {
        this.activityUpdateID = activityUpdateID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
}

package com.example.eltur.parkinsonbp.ServerClass;

import static java.lang.String.format;

/**
 * Created by Eltur on 02/09/2017.
 */

public class HabitUpdate {

    // private Long habitUpdateID;
    private String habitName;
    private String habitDescription;

    public HabitUpdate(){}

    @Override
        public String toString(){
            return format("{habitName:\"%s\",habitDescription:\"%s\"}",habitName,habitDescription);
        }


    // public Long getHabitUpdateID() {
    //    return habitUpdateID;
    // }

    //  public void setHabitUpdateID(Long habitUpdateID) {
    //   this.habitUpdateID = habitUpdateID;
    // }

    public String getName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getDescription() {
        return habitDescription;
    }

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }
}

package be.ehb.swp2.application;

import java.util.StringJoiner;

/**
 * Created by Thomas on 29/10/2015.
 */
public abstract class Question {
    private String name;
    private String description;
    private int time;
    private boolean timeOn;

    /** default constructor */
    public Question(){
        this.name = "geen naam";
    }

    /** constructor */
    public Question(String name, String description, int time, boolean timeOn){
        this.name = name;
        this.description = description;
        this.time = time;
        this.timeOn = timeOn;
    }

    /** getters */
    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getTime(){
        return this.time;
    }

    public boolean isTimeOn(){
        return this.timeOn;
    }

    /** setters */

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setTimeOn(boolean timeOn){

    }


    /** solve method */

    public abstract boolean solve();
}

package be.ehb.swp2.util;

/**
 * Created by Chris on 26/11/2015.
 */
public class PieChartData {
    private String name;
    private double percentage;

    public PieChartData(String name, double percentage){
        this.name = name;
        this.percentage = percentage;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setPercentage(double percentage){
        this.percentage = percentage;
    }

    public String getName(){return name;}
    public double getPercentage(){return percentage;}
}

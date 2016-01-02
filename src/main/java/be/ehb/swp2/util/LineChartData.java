package be.ehb.swp2.util;

/**
 * Created by Chris on 29/11/2015.
 */
public class LineChartData {
    private String name;
    private double[] data;

    public LineChartData(String name, double[] data){
        this.name = name;
        this.data = data;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setData(double[] data){
        this.data = data;
    }

    public String getName(){
        return name;
    }

    public double[] getData(){
        return data;
    }

}
package be.ehb.swp2.util;

/**
 * Created by Chris on 03/12/2015.
 */
public class ColumnData {

    private String name;
    private double[] data;

    public ColumnData(String name, double[] data){
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
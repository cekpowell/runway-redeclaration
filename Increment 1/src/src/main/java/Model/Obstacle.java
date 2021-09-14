package Model;

import java.util.Optional;
import java.util.Properties;

public class Obstacle {

    public Obstacle (){}

    public Obstacle (String name, int id, double width, double length, double height, Optional<Properties> properties) {
        this.name = name;
        this.id = id;
        this.width = width;
        this.length = length;
        this.height = height;
        if(properties.isPresent()){
            this.properties = properties.get();
        }

    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Obstacle)){
            return false;
        }
        if(o == this){
            return true;
        }
        return this.id == ((Obstacle) o).getId();
    }
    @Override
    public int hashCode(){
        return this.id;
    }


    private String name;
    private int id;
    private double width;
    private double length;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private double height;


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    Properties properties;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString(){
        return this.getName() + " (" + this.getId() + ")";
    }

}

package GUITesting;

import Model.Obstacle;

public class MockObstacle {

    private String name;
    private String id;
    private String width;
    private String length;



    private String height;

    public MockObstacle(String name,String id,String width,String length,String height){
        super();
        this.name = name;
        this.id = id;
        this.width = width;
        this.length = length;
        this.height = height;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}

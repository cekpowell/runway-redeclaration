package ProgramOperations;

import Model.Obstacle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeclareObstacle {
    @Test
    public void declareObstacle(){
        Obstacle a = new Obstacle();
        String name = "ROCK";
        double len = 2.0;
        double height = 3.0;
        double width = 4.0;
        int id = 3;
        a.setName(name);
        a.setHeight(height);
        a.setLength(len);
        a.setWidth(width);
        assertEquals(name,a.getName());
        assertEquals(height,a.getHeight());
        assertEquals(len,a.getLength());
        assertEquals(width,a.getWidth());

    }

    @Test
    public void equality(){
        Obstacle a = new Obstacle();
        int id = 3;
        a.setId(id);
        Obstacle b = new Obstacle();
        b.setId(id);
        assertTrue(b.equals(a));

    }
}

package Model;

/**
 * Class to Represent a point in the 3 space. R^3
 */
public class Position {
    public Position(){}
    public Position(int x, int y){
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public Position(int x, int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    int x;
    int y;
    int z;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public double distance(Position pos) {
        double dx = x - pos.x;
        double dy = y - pos.y;
        double dz = z - pos.z;

        return Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2), 1.0/2.0);
    }

}

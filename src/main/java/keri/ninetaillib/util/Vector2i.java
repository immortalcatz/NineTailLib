package keri.ninetaillib.util;

public class Vector2i {

    private int x;
    private int y;

    public Vector2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Vector2i add(int x, int y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i multiply(int multiplier){
        this.x *= multiplier;
        this.y *= multiplier;
        return this;
    }

}

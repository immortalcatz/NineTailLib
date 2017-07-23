/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.math;

import codechicken.lib.util.Copyable;

public class Point2d implements Copyable<Point2d> {

    private double x;
    private double y;

    public Point2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point2d add(Point2d point){
        this.x += point.x;
        this.y += point.y;
        return this;
    }

    public Point2d add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Point2d multiply(Point2d point){
        this.x *= point.x;
        this.y *= point.y;
        return this;
    }

    public Point2d multiply(double x, double y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    @Override
    public Point2d copy() {
        return new Point2d(this.x, this.y);
    }

}

package com.evilcorp.entity;

public class Pixel {

    private int x;
    private int y;

    public Pixel() {
    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pixel(Pixel pixel) {
        this.x = pixel.x;
        this.y = pixel.y;
    }

    public void moveByConstant(int diff) {
        this.x += diff;
        this.y += diff;
    }

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

    @Override
    public String toString() {
        return "Pixel{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

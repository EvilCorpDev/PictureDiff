package com.evilcorp.entity;

import static com.evilcorp.utils.ImageUtils.getDistanceBetweenTwoPoints;

public class RectangularBound {

    private Pixel leftTopCorner;
    private Pixel rightBottomCorner;

    public RectangularBound() {
    }

    public RectangularBound(Pixel leftTopCorner, Pixel rightBottomCorner) {
        this.leftTopCorner = leftTopCorner;
        this.rightBottomCorner = rightBottomCorner;
    }

    public int getWidth() {
        return (int) getDistanceBetweenTwoPoints(leftTopCorner, new Pixel(rightBottomCorner.getX(), leftTopCorner.getY()));
    }

    public int getHeight() {
        return (int) getDistanceBetweenTwoPoints(leftTopCorner, new Pixel(leftTopCorner.getX(), rightBottomCorner.getY()));
    }

    public Pixel getLeftTopCorner() {
        return leftTopCorner;
    }

    public void setLeftTopCorner(Pixel leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
    }

    public Pixel getRightBottomCorner() {
        return rightBottomCorner;
    }

    public void setRightBottomCorner(Pixel rightBottomCorner) {
        this.rightBottomCorner = rightBottomCorner;
    }
}

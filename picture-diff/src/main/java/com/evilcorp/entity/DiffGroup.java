package com.evilcorp.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiffGroup {

    private static final int BOUND_DIFF = 1;

    private List<Pixel> pixels;

    public DiffGroup() {
        pixels = new ArrayList<>();
    }

    public void addPixel(Pixel pixel) {
        pixels.add(pixel);
    }

    public void addPixels(List<Pixel> pixels) {
        this.pixels.addAll(pixels);
    }

    public void addPixels(Pixel... pixels) {
        addPixels(Arrays.asList(pixels));
    }

    public RectangularBound getGroupBorders() {
        Pixel leftTopCorner = new Pixel(pixels.get(0));
        Pixel rightBottomCorner = new Pixel(pixels.get(0));
        for(Pixel pixel : pixels) {
            updateLeftTopCorner(leftTopCorner, pixel);
            updateRightbottomCorner(rightBottomCorner, pixel);
        }
        leftTopCorner.moveByConstant(-BOUND_DIFF);
        rightBottomCorner.moveByConstant(BOUND_DIFF);
        return new RectangularBound(leftTopCorner, rightBottomCorner);
    }

    public List<Pixel> getPixels() {
        return pixels;
    }

    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public String toString() {
        return "DiffGroup{" +
                "pixels=" + pixels +
                '}';
    }

    private void updateLeftTopCorner(Pixel leftTopCorner, Pixel comparingPixel) {
        if(comparingPixel.getX() < leftTopCorner.getX()) {
            leftTopCorner.setX(comparingPixel.getX());
        }
        if(comparingPixel.getY() < leftTopCorner.getY()) {
            leftTopCorner.setY(comparingPixel.getY());
        }
    }

    private void updateRightbottomCorner(Pixel rightBottomCorner, Pixel comparingPixel) {
        if(comparingPixel.getX() > rightBottomCorner.getX()) {
            rightBottomCorner.setX(comparingPixel.getX());
        }
        if(comparingPixel.getY() > rightBottomCorner.getY()) {
            rightBottomCorner.setY(comparingPixel.getY());
        }
    }
}

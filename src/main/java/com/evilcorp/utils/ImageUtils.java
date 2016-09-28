package com.evilcorp.utils;

import com.evilcorp.entity.Pixel;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static double getDistanceBetweenTwoPoints(Pixel first, Pixel second) {
        double squareX = Math.pow((double) second.getX() - first.getX(), 2);
        double squareY = Math.pow((double) second.getY() - first.getY(), 2);
        return Math.sqrt(squareX + squareY);
    }
}

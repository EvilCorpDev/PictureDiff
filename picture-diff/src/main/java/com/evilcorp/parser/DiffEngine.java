package com.evilcorp.parser;

import com.evilcorp.entity.Colour;
import com.evilcorp.entity.Pixel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DiffEngine {

    public List<Pixel> findDifferences(BufferedImage first, BufferedImage second) {
        List<Pixel> diffPixels = new ArrayList<>();
        for (int height = 0; height < first.getHeight(); height++) {
            for (int width = 0; width < first.getWidth(); width++) {
                if (!comparePixels(createColour(first, width, height), createColour(second, width, height))) {
                    diffPixels.add(new Pixel(width, height));
                }
            }
        }
        return diffPixels;
    }

    private boolean comparePixels(Colour firstColour, Colour secondColour) {
        return getColourDiff(firstColour, secondColour) < 10;
    }

    private double getColourDiff(Colour firstColour, Colour secondColour) {
        double redSquare = Math.pow((double) firstColour.getRed() - secondColour.getRed(), 2);
        double greenSquare = Math.pow((double) firstColour.getGreen() - secondColour.getGreen(), 2);
        double blueSquare = Math.pow((double) firstColour.getBlue() - secondColour.getBlue(), 2);
        double colourDistance = Math.sqrt(redSquare + greenSquare + blueSquare);
        return colourDistance / Math.sqrt(3d * 255 * 255) * 100;
    }

    private Colour createColour(BufferedImage image, int x, int y) {
        Colour colour = new Colour();
        colour.setRed((image.getRGB(x, y) >> 16) & 0xff);
        colour.setGreen((image.getRGB(x, y) >> 8) & 0xff);
        colour.setBlue(image.getRGB(x, y)& 0xff);
        return colour;
    }
}

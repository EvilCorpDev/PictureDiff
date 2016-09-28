package com.evilcorp;

import com.evilcorp.entity.DiffGroup;
import com.evilcorp.entity.Pixel;
import com.evilcorp.entity.RectangularBound;
import com.evilcorp.parser.DiffEngine;
import com.evilcorp.parser.PixelAgregator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws IOException {
        Demo demo = new Demo();
        demo.startDemo("image1.png", "image4.png");
    }

    public void startDemo(String firstFile, String secondFile) throws IOException {
        BufferedImage firstImage = ImageIO.read(getFileFromResources(firstFile));
        BufferedImage secondImage = ImageIO.read(getFileFromResources(secondFile));
        DiffEngine engine = new DiffEngine();
        List<Pixel> differences = engine.findDifferences(firstImage, secondImage);
        PixelAgregator agregator = new PixelAgregator();
        List<DiffGroup> pixelGroups = agregator.getPixelGroups(differences);
        drawBoundary(secondImage, pixelGroups);
    }

    public File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public void drawBoundary(BufferedImage image, List<DiffGroup> diffGroups) throws IOException {
        BufferedImage rendered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics = rendered.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(Color.red);
        diffGroups.forEach(group -> {
            RectangularBound groupBorders = group.getGroupBorders();
            Pixel leftCorner = groupBorders.getLeftTopCorner();
            graphics.drawRect(leftCorner.getX(), leftCorner.getY(), groupBorders.getWidth(), groupBorders.getHeight());
        });
        graphics.dispose();
        File output = new File("output.png");
        ImageIO.write(rendered, "png", output);
    }
}

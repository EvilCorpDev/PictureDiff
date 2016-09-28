package com.evilcorp.services;

import com.evilcorp.entity.DiffGroup;
import com.evilcorp.entity.Pixel;
import com.evilcorp.entity.RectangularBound;
import com.evilcorp.parser.DiffEngine;
import com.evilcorp.parser.PixelAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ServletContext servletContext;

    private static final String DOT_SEPARATOR = ".";
    private static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    public BufferedImage compareTwoImages(MultipartFile firstFile, MultipartFile secondFile) throws IOException {
        File first = saveImage(firstFile, servletContext.getRealPath("/resources/img/"));
        File second = saveImage(secondFile, servletContext.getRealPath("/resources/img/"));
        BufferedImage firstImage = ImageIO.read(first);
        BufferedImage secondImage = ImageIO.read(second);
        DiffEngine engine = new DiffEngine();
        List<Pixel> differences = engine.findDifferences(firstImage, secondImage);
        PixelAggregator aggregator = new PixelAggregator();
        List<DiffGroup> pixelGroups = aggregator.getPixelGroups(differences);
        return drawBoundary(secondImage, pixelGroups);
    }

    public BufferedImage drawBoundary(BufferedImage image, List<DiffGroup> diffGroups) throws IOException {
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
        File output = new File("output.jpg");
        ImageIO.write(rendered, "jpg", output);
        return rendered;
    }

    /**
     * Saved uploaded image into under img/{currentDate} folder with random UUID
     *
     * @param file     - image to be saved
     * @param realPath - realPath of where img folder is located
     * @return result of saving image. It could be filePath or error inside
     */
    public File saveImage(MultipartFile file, String realPath) {
        if (!file.isEmpty()) {
            try {
                StringBuilder fileName = new StringBuilder(LocalDate.now().toString());
                Path destination = Paths.get(realPath, fileName.toString());
                if (!Files.exists(destination)) {
                    Files.createDirectories(destination);
                }
                File output = new File(destination.toAbsolutePath() + "/" + getFileName(file));
                file.transferTo(output);
                return output;
            } catch (IOException | RuntimeException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * Get full fileName with extension
     *
     * @param file - image to be saved
     * @return full fileName
     */
    private String getFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + DOT_SEPARATOR
                + ((CommonsMultipartFile) file).getFileItem().getName().split("\\.")[1];
    }
}

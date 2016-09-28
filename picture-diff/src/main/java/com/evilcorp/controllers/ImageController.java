package com.evilcorp.controllers;

import com.evilcorp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Controller
@RequestMapping("/rest")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/images/compare", method = RequestMethod.POST, produces = IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getImage(@RequestParam("firstFile")MultipartFile first,
                                  @RequestParam("secondFile") MultipartFile second) throws IOException {
        return imageService.compareTwoImages(first, second);
    }
}

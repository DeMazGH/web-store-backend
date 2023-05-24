package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Slf4j
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/image/{adId}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void getImage(@PathVariable int adId,
                         HttpServletResponse response) throws IOException {
        log.info("Was invoked method - getImage");
        try (InputStream is = Files.newInputStream(imageService.getImagePath(adId));
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/avatar/{userId}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void getAvatar(@PathVariable int userId,
                          HttpServletResponse response) throws IOException {
        log.info("Was invoked method - getAvatar");
        try (InputStream is = Files.newInputStream(imageService.getAvatarPath(userId));
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            is.transferTo(os);
        }
    }
}

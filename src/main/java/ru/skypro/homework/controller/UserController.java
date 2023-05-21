package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto passwordDto) {
        log.info("Was invoked method - setPassword");
        if (userService.setPassword(passwordDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        log.info("Was invoked method - getMe");
        return ResponseEntity.ok(userService.getMe());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        log.info("Was invoked method - updateUser");
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateImage(@RequestBody MultipartFile image) throws IOException {
        log.info("Was invoked method - updateImage");
        userService.updateImage(image);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/avatar", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void getAvatar(HttpServletResponse response) throws IOException {
        try(InputStream is = Files.newInputStream(userService.getAvatarPath());
            OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            is.transferTo(os);
        }
    }
}

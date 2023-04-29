package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.NewPasswordService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final NewPasswordService newPasswordService;
    private final UserService userService;

    public UserController(NewPasswordService newPasswordService, UserService userService) {
        this.newPasswordService = newPasswordService;
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto passwordDto) {
        log.info("Was invoked method - setPassword");
        return newPasswordService.setPassword(passwordDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        log.info("Was invoked method - getMe");
        //по API описано, что в каких-то случаях должен возвращаться ответ - Forbidden, не пойму в каком случае?
        if (userService.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (userService.getMe() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userService.getMe());
        }
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        log.info("Was invoked method - updateUser");
        //по API описано, что в каких-то случаях должен возвращаться ответ - Forbidden, не пойму в каком случае?
        if (userService.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (userService.getMe() == null) {
            return ResponseEntity.notFound().build();
        } else if (userService.dataIsNew(userDto)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(userService.updateUser(userDto));
        }
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateImage(@RequestBody MultipartFile image) throws IOException {
        log.info("Was invoked method - updateImage");
        if (userService.getMe() == null) {
            return ResponseEntity.notFound().build();
        } else {
            userService.updateImage(image);
            return ResponseEntity.noContent().build();
        }
    }
}

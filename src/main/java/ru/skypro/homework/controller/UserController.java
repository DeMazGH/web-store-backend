package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto passwordDto) {
        logger.info("Was invoked method - setPassword");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        logger.info("Was invoked method - getMe");
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        logger.info("Was invoked method - updateUser");
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateImage(@RequestBody String image) {
        logger.info("Was invoked method - updateImage");
        return ResponseEntity.ok().build();
    }
}

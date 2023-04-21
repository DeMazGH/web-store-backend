package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDto passwordDto) {
        log.info("Was invoked method - setPassword");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        log.info("Was invoked method - getMe");
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        log.info("Was invoked method - updateUser");
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateImage(@RequestBody String image) {
        log.info("Was invoked method - updateImage");
        return ResponseEntity.ok().build();
    }
}

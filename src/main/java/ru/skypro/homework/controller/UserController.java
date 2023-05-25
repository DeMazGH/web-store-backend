package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@RequestPart MultipartFile image) throws IOException {
        log.info("Was invoked method - updateImage");
        userService.updateImage(image);
        return ResponseEntity.ok().build();
    }
}

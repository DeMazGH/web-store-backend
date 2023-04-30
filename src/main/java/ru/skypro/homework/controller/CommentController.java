package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> getAdsComments(@PathVariable("ad_pk") int adPk) {
        log.info("Was invoked method - getAdsComments");
        return ResponseEntity.ok(new CommentDto());
    }


    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> addAdsComment(@PathVariable("ad_pk") int adPk,
                                                    @RequestBody CommentDto adsCommentDto) {
        log.info("Was invoked method - addAdsComment");
        return ResponseEntity.ok(new CommentDto());
    }


    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable("ad_pk") int adPk,
                                                 @PathVariable int id) {
        log.info("Was invoked method - deleteAdsComment");
        return ResponseEntity.ok().build();
    }


    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> getAdsComment(@PathVariable("ad_pk") int adPk,
                                                    @PathVariable int id) {
        log.info("Was invoked method - getAdsComment");
        return ResponseEntity.ok(new CommentDto());
    }


    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> updateAdsComment(@PathVariable("ad_pk") int adPk,
                                                       @PathVariable int id,
                                                       @RequestBody CommentDto comment) {
        log.info("Was invoked method - updateAdsComment");
        return ResponseEntity.ok(new CommentDto());
    }
}



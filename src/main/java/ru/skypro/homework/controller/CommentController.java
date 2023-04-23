package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> getAdsComments(@PathVariable("ad_pk") int adPk) {
        return ResponseEntity.ok(new CommentDto());
    }


    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> addAdsComment(@PathVariable("ad_pk") int adPk, @RequestBody CommentDto adsCommentDto) {
        return ResponseEntity.ok(new CommentDto());
    }


    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable("ad_pk") int adPk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> getAdsComment(@PathVariable("ad_pk") int adPk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok(new CommentDto());
    }


    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> updateAdsComment(@PathVariable("ad_pk") int adPk,
                                                       @PathVariable int id,
                                                       @RequestBody CommentDto comment) {
        return ResponseEntity.ok(new CommentDto());
    }
}

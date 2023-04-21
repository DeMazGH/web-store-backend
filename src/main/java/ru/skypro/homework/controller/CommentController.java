package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDTO> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(new CommentDTO());
    }


    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDTO> addAdsComment(@PathVariable int ad_pk, @RequestBody CommentDTO adsCommentDto) {
        return ResponseEntity.ok(new CommentDTO());
    }


    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDTO> getAdsComment(@PathVariable int ad_pk,
                                                       @PathVariable int id) {
        return ResponseEntity.ok(new CommentDTO());
    }


    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDTO> updateAdsComment(@PathVariable int ad_pk,
                                                          @PathVariable int id,
                                                          @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(new CommentDTO());
    }
}

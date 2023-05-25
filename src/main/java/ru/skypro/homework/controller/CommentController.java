package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.service.AccessRightValidator;
import ru.skypro.homework.service.CommentService;

@Slf4j
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final AccessRightValidator accessRightValidator;

    @GetMapping("{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getAdComments(@PathVariable("ad_pk") int adId) {
        log.info("Was invoked method - getAdComments");
        return ResponseEntity.ok(commentService.getAdComments(adId));
    }


    @PostMapping("{ad_pk}/comments")
    public ResponseEntity<CommentDto> addCommentToAd(@PathVariable("ad_pk") int adId,
                                                     @RequestBody CreateCommentDto createdComment) {
        log.info("Was invoked method - addAdsComment");
        return ResponseEntity.ok(commentService.addCommentToAd(adId, createdComment));
    }

    @DeleteMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable("ad_pk") int adId,
                                                 @PathVariable("id") int commentId) {
        log.info("Was invoked method - deleteAdsComment");
        if (!accessRightValidator.userHaveAccessToComment(commentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (!commentService.dataIsConsistent(adId, commentId)) {
            return ResponseEntity.badRequest().build();
        } else {
            commentService.deleteAdsComment(commentId);
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("ad_pk") int adId,
                                                      @PathVariable("id") int commentId,
                                                      @RequestBody CommentDto newData) {
        log.info("Was invoked method - updateAdComment");
        if (!accessRightValidator.userHaveAccessToComment(commentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (!commentService.dataIsConsistent(adId, commentId)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(commentService.updateAdComment(commentId, newData));
        }
    }
}



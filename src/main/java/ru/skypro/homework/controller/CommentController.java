package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.service.AuthValidator;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;
    private final AuthValidator authValidator;

    public CommentController(CommentService commentService, AuthValidator authValidator) {
        this.commentService = commentService;
        this.authValidator = authValidator;
    }

    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperCommentDto> getAdsComments(@PathVariable("ad_pk") int adPk) {
        log.info("Was invoked method - getAdsComments");
        if (authValidator.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(commentService.getAdsComments(adPk));
        }
    }


    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> addAdsComment(@PathVariable("ad_pk") int adPk,
                                                    @RequestBody CreateCommentDto createdComment) {
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



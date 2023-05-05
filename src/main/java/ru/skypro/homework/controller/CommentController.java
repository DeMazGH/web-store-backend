package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.service.AccessRightValidator;
import ru.skypro.homework.service.AuthValidator;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;
    private final AuthValidator authValidator;
    private final AccessRightValidator accessRightValidator;

    public CommentController(CommentService commentService, AuthValidator authValidator, AccessRightValidator accessRightValidator) {
        this.commentService = commentService;
        this.authValidator = authValidator;
        this.accessRightValidator = accessRightValidator;
    }

    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperCommentDto> getAdComments(@PathVariable("ad_pk") int adPk) {
        log.info("Was invoked method - getAdComments");
        if (authValidator.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(commentService.getAdComments(adPk));
        }
    }


    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<CommentDto> addCommentToAd(@PathVariable("ad_pk") int adPk,
                                                     @RequestBody CreateCommentDto createdComment) {
        log.info("Was invoked method - addAdsComment");
        if (authValidator.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(commentService.addCommentToAd(adPk, createdComment));
        }
    }

    //Зачем нам здесь id объявления, если мы сразу можем удалить коммент по его id?
    //Или нужно чтобы пользователь мог удалять чужие комменты в своих объявлениях?
    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable("ad_pk") int adId,
                                                 @PathVariable("id") int commentId) {
        log.info("Was invoked method - deleteAdsComment");
        if (authValidator.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (!accessRightValidator.userHaveAccessToComment(commentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            commentService.deleteAdsComment(commentId);
            return ResponseEntity.ok().build();
        }
    }

    //Зачем нам здесь id объявления, если мы сразу можем изменить коммент по его id?
    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> updateAdComment(@PathVariable("ad_pk") int adId,
                                                      @PathVariable("id") int commentId,
                                                      @RequestBody CommentDto newData) {
        log.info("Was invoked method - updateAdComment");
        if (authValidator.userIsNotAuthorised()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (!accessRightValidator.userHaveAccessToComment(commentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.ok(commentService.updateAdComment(commentId, newData));
        }
    }
}



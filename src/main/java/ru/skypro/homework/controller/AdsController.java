package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.service.AccessRightValidator;
import ru.skypro.homework.service.AdsService;

@Slf4j
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdsService adsService;
    private final AccessRightValidator accessRightValidator;

    public AdsController(AdsService adsService, AccessRightValidator accessRightValidator) {
        this.adsService = adsService;
        this.accessRightValidator = accessRightValidator;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        log.info("Was invoked method - getAllAds");
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> createAd(@RequestPart("properties") CreateAdsDto properties,
                                           @RequestPart("image") MultipartFile adImage) {
        log.info("Was invoked method - createAd");
        return ResponseEntity.ok(adsService.createAd(properties, adImage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getInfoAboutAd(@PathVariable("id") int adId) {
        log.info("Was invoked method - getInfoAboutAd");
        FullAdsDto desiredAd = adsService.getInfoAboutAd(adId);
        return (desiredAd == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(desiredAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable("id") int adId) {
        log.info("Was invoked method - deleteAd");
        if (!accessRightValidator.userHaveAccessToAd(adId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            adsService.deleteAd(adId);
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAd(@PathVariable("id") int adId, @RequestBody CreateAdsDto newAdData) {
        log.info("Was invoked method - updateAd");
        if (!accessRightValidator.userHaveAccessToAd(adId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            AdsDto updatedAd = adsService.updateAd(adId, newAdData);
            return (updatedAd == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedAd);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        log.info("Was invoked method - getMyAds");
        return ResponseEntity.ok(adsService.getMyAds());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdImage(@PathVariable("id") int adId,
                                                @RequestPart("image") MultipartFile adImage) {
        log.info("Was invoked method - updateAdImage");
        if (!accessRightValidator.userHaveAccessToAd(adId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.ok(adsService.updateAdImage(adId, adImage));
        }
    }

    //Добавить Get-метод для получения картинки объявления
}

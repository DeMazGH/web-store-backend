package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.service.AdsService;

@Slf4j
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        log.info("Was invoked method - getAllAds");
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> createAd(@RequestPart("properties") CreateAdsDto properties,
                                           @RequestPart("image") MultipartFile image) {
        log.info("Was invoked method - createAd");
        return ResponseEntity.status(401).body(new AdsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getInfoAboutAd(@PathVariable int id) {
        log.info("Was invoked method - getInfoAboutAd");
        return ResponseEntity.ok(new FullAdsDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable int id) {
        log.info("Was invoked method - deleteAd");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAd(@PathVariable int id, @RequestBody CreateAdsDto adProperties) {
        log.info("Was invoked method - updateAd");
        return ResponseEntity.ok(new AdsDto());
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        log.info("Was invoked method - getMyAds");
        return ResponseEntity.ok(new ResponseWrapperAdsDto());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdImage(@PathVariable int id, @RequestPart("image") MultipartFile image) {
        log.info("Was invoked method - updateAdImage");
        return ResponseEntity.ok("testString");
    }
}

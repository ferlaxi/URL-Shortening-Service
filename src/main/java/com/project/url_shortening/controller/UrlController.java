package com.project.url_shortening.controller;

import com.project.url_shortening.dto.UrlDTO;
import com.project.url_shortening.model.Url;
import com.project.url_shortening.service.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/shorten")
public class UrlController {

    @Autowired
    private IUrlService iUrlService;

    @PostMapping
    public ResponseEntity<UrlDTO> createUrl (@RequestBody Url url) {
        Optional<UrlDTO> newUrl = iUrlService.save(url);
        return newUrl.map(urlDTO -> new ResponseEntity<>(urlDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(newUrl.get(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<UrlDTO>> getAllUrls () {
        return new ResponseEntity<>(iUrlService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<UrlDTO> getShortCode (@PathVariable String shortcode) {
        Optional<UrlDTO> urlFinded = iUrlService.findByShortCode(shortcode);
        return urlFinded.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{shortcode}")
    public ResponseEntity<UrlDTO> updateUrl (@PathVariable String shortcode,
                                             @RequestBody UrlDTO url) {
        Optional<UrlDTO> editedUrl = iUrlService.update(shortcode, url);
        return editedUrl.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{shortcode}")
    public ResponseEntity<String> deleteUrl (@PathVariable String shortcode) {
        iUrlService.delete(shortcode);
        if (iUrlService.findByShortCode(shortcode).isEmpty()) {
            return new ResponseEntity<>("Successfully deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("URL could not be deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{shortcode}/stats")
    public ResponseEntity<Url> getStatsFromUrl (@PathVariable String shortcode) {
        Optional<Url> urlStats = iUrlService.getStatsByShortCode(shortcode);
        return urlStats.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

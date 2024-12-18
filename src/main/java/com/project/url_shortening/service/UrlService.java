package com.project.url_shortening.service;

import com.project.url_shortening.dto.UrlDTO;
import com.project.url_shortening.model.Url;
import com.project.url_shortening.repository.IUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService implements IUrlService {

    public static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final SecureRandom random = new SecureRandom();

    @Autowired
    private IUrlRepository iUrlRepository;

    @Override
    public Optional<UrlDTO> save (Url url) {
        Url newUrl = new Url();

        String verifiedString = this.verifyCode();

        newUrl.setUrl(url.getUrl());
        newUrl.setShortCode(verifiedString);
        newUrl.setCreatedAt(LocalDateTime.now());
        newUrl.setUpdatedAt(LocalDateTime.now());
        newUrl.setAccessCount(0);

        iUrlRepository.save(newUrl);

        return this.findByShortCode(newUrl.getShortCode());
    }

    @Override
    public Optional<UrlDTO> findByShortCode(String shortcode) {
        Optional<Url> url =  iUrlRepository.findByShortCode(shortcode);
        UrlDTO urlDTO = new UrlDTO();
        Optional<UrlDTO> optionalUrlDTO = Optional.of(urlDTO);

        if (url.isPresent()) {
            optionalUrlDTO.get().setId(url.get().getId());
            optionalUrlDTO.get().setUrl(url.get().getUrl());
            optionalUrlDTO.get().setShortCode(url.get().getShortCode());
            optionalUrlDTO.get().setCreatedAt(url.get().getCreatedAt());
            optionalUrlDTO.get().setUpdatedAt(url.get().getUpdatedAt());
        } else {
            return Optional.empty();
        }

        return optionalUrlDTO;
    }

    @Override
    public Optional<UrlDTO> update(String shortcode, UrlDTO url) {

        Optional<UrlDTO> urlFinded;
        Optional<Url> urlOriginal = iUrlRepository.findByShortCode(shortcode);

        if (urlOriginal.isPresent()) {
            urlOriginal.get().setUrl(url.getUrl());
            urlOriginal.get().setUpdatedAt(LocalDateTime.now());
            this.save(urlOriginal.get());

            urlFinded = this.findByShortCode(shortcode);
            if (urlFinded.isPresent()) {
                urlFinded.get().setUrl(url.getUrl());
                urlFinded.get().setUpdatedAt(LocalDateTime.now());
                return urlFinded;
            }
        }

        return Optional.empty();
    }

    @Override
    public void delete(String shortcode) {
        iUrlRepository.deleteByShortCode(shortcode);
    }

    @Override
    public Optional<Url> getStatsByShortCode(String shortcode) {
        return iUrlRepository.findStatsByShortCode(shortcode);
    }



    public String verifyCode (){
        StringBuilder stringBuilder = new StringBuilder(5);
        Optional<UrlDTO> urlFinded;

        do {
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(characters.length());
                stringBuilder.append(characters.charAt(randomIndex));
            }
            urlFinded = this.findByShortCode(stringBuilder.toString());
        } while (urlFinded.isPresent());

        return stringBuilder.toString();
    }
}

package com.project.url_shortening.service;

import com.project.url_shortening.dto.UrlDTO;
import com.project.url_shortening.model.Url;
import com.project.url_shortening.repository.IUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<UrlDTO> findAll() {
        List<Url> urlList = iUrlRepository.findAll();
        List<UrlDTO> urlDTOS = new ArrayList<>();

        for (Url url : urlList) {
            urlDTOS.add(new UrlDTO(url.getId(), url.getUrl(), url.getShortCode(), url.getCreatedAt(), url.getUpdatedAt()));
        }

        return urlDTOS;
    }

    @Override
    public Optional<UrlDTO> findByShortCode(String shortcode) {
        Optional<Url> url =  iUrlRepository.findByShortCode(shortcode);
        
        if (url.isPresent()) {
            Url u = url.get();
            return Optional.of(new UrlDTO(u.getId(), u.getUrl(), u.getShortCode(), u.getCreatedAt(), u.getUpdatedAt()));
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<UrlDTO> resolveShortCode(String shortcode) {
        Optional<Url> urlOptional = iUrlRepository.findByShortCode(shortcode);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            // Incrementar contador de visitas
            url.setAccessCount(url.getAccessCount() + 1);
            iUrlRepository.save(url);
            return Optional.of(new UrlDTO(url.getId(), url.getUrl(), url.getShortCode(), url.getCreatedAt(), url.getUpdatedAt()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UrlDTO> update(String shortcode, UrlDTO url) {
        Optional<Url> urlOriginal = iUrlRepository.findByShortCode(shortcode);

        if (urlOriginal.isPresent()) {
            Url existingUrl = urlOriginal.get();
            existingUrl.setUrl(url.getUrl());
            existingUrl.setUpdatedAt(LocalDateTime.now());
            // Guardamos el objeto existente, NO creamos uno nuevo
            iUrlRepository.save(existingUrl);

            return this.findByShortCode(shortcode);
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
        Optional<UrlDTO> urlFinded;
        String generatedCode;

        do {
            // BUGFIX: El StringBuilder debe reiniciarse en cada iteracion
            StringBuilder stringBuilder = new StringBuilder(5);
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(characters.length());
                stringBuilder.append(characters.charAt(randomIndex));
            }
            generatedCode = stringBuilder.toString();
            urlFinded = this.findByShortCode(generatedCode);
        } while (urlFinded.isPresent());

        return generatedCode;
    }
}

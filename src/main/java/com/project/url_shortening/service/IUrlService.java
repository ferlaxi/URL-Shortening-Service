package com.project.url_shortening.service;

import com.project.url_shortening.dto.UrlDTO;
import com.project.url_shortening.model.Url;

import java.util.Optional;

public interface IUrlService {
    public Optional<UrlDTO> save (Url url);
    public Optional<UrlDTO> findByShortCode (String shortcode);
    public Optional<UrlDTO> update (String shortcode, UrlDTO url);
    public void delete (String shortcode);
    public Optional<Url> getStatsByShortCode(String shortcode);
}

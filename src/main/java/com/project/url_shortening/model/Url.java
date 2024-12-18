package com.project.url_shortening.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String shortCode;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private Integer accessCount;

    public Url() {
    }

    public Url(Long id, String url, String shortCode, LocalDateTime createdAt, LocalDateTime updatedAt, Integer accessCount) {
        this.id = id;
        this.url = url;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accessCount = accessCount;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }
}

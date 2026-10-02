package com.project.url_shortening.repository;

import com.project.url_shortening.model.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUrlRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.shortCode = :shortcode")
    public Optional<Url> findByShortCode(@Param("shortcode") String shortcode);

    @Modifying
    @Transactional
    @Query("DELETE FROM Url u WHERE u.shortCode = :shortcode")
    public void deleteByShortCode (@Param("shortcode") String shortcode);

    @Query("SELECT u FROM Url u WHERE u.shortCode = :shortcode")
    public Optional<Url> findStatsByShortCode(@Param("shortcode") String shortcode);
}

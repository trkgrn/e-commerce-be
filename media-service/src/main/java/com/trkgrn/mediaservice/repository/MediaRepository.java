package com.trkgrn.mediaservice.repository;

import com.trkgrn.mediaservice.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
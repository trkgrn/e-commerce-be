package com.trkgrn.mediaservice.repository;

import com.trkgrn.mediaservice.model.MediaContainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaContainerRepository extends JpaRepository<MediaContainer, Long> {
}
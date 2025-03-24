package com.trkgrn.mediaservice.repository;

import com.trkgrn.mediaservice.model.MediaFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFormatRepository extends JpaRepository<MediaFormat, Long> {
}
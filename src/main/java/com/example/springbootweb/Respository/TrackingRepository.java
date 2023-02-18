package com.example.springbootweb.Respository;

import com.example.springbootweb.Models.TrackingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingId, Long> {
    TrackingId findTrackingIdByIdUsername(Long idUsername);
    Boolean existsTrackingIdByName(String name);
}

package com.project.iotshield.repository;

import com.project.iotshield.model.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

}

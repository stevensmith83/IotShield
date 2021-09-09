package com.project.iotshield.repository;

import com.project.iotshield.model.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {

  @Query(value = "select count(*) from DeviceEntity")
  Integer countDevices();

  @Query(value = "select count(*) from DeviceEntity where quarantined = true")
  Integer countQuarantinedDevices();
}

package com.project.iotshield.service;

import com.project.iotshield.model.entities.DeviceEntity;
import com.project.iotshield.repository.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceService {

  private final DeviceRepository deviceRepository;

  public boolean isQuarantined(String deviceId) {

    DeviceEntity deviceEntity;

    if (deviceRepository.existsById(deviceId)) {
      deviceEntity = deviceRepository.getOne(deviceId);
      return deviceEntity.isQuarantined();
    } else {
      deviceEntity = DeviceEntity.builder()
          .deviceId(deviceId)
          .quarantined(false)
          .build();

      deviceRepository.save(deviceEntity);
      return false;
    }
  }

  public void setQuarantined(String deviceId, boolean quarantined) {

    DeviceEntity deviceEntity = deviceRepository.getOne(deviceId);
    deviceEntity.setQuarantined(quarantined);
    deviceRepository.save(deviceEntity);
  }
}

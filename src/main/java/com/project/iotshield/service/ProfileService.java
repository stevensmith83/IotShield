package com.project.iotshield.service;

import com.project.iotshield.model.entities.ProfileEntity;
import com.project.iotshield.model.entities.RequestEntity;
import com.project.iotshield.model.events.ProfileEvent;
import com.project.iotshield.repository.ProfileRepository;
import com.project.iotshield.repository.RequestRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileService {

  public static final String ALLOW = "allow";
  public static final String BLOCK = "block";
  public static final String INCORRECT_BLOCKS = "incorrectBlocks";
  public static final String MISSED_BLOCKS = "missedBlocks";

  private final ProfileRepository profileRepository;
  private final RequestRepository requestRepository;
  private final DeviceService deviceService;
  private final StatisticService statisticService;

  public void createProfile(ProfileEvent event) {

    if (!profileRepository.existsById(event.getModelName())) {
      ProfileEntity profileEntity = ProfileEntity.builder()
          .modelName(event.getModelName())
          .defaultPolicy(event.getDefaultPolicy())
          .whitelist(event.getWhitelist())
          .blacklist(event.getBlacklist())
          .build();

      profileRepository.save(profileEntity);
    }
  }

  public void updateProfile(ProfileEvent event) {

    if (profileRepository.existsById(event.getModelName())) {
      ProfileEntity profileEntity = profileRepository.getOne(event.getModelName());

      if (event.getDefaultPolicy() != null) {
        profileEntity.setDefaultPolicy(event.getDefaultPolicy());
      }

      if (event.getWhitelist() != null) {
        profileEntity.setWhitelist(event.getWhitelist());
      }

      if (event.getBlacklist() != null) {
        profileEntity.setBlacklist(event.getBlacklist());
      }

      profileRepository.save(profileEntity);
      saveStatistics(profileEntity, event.getTimestamp());
    }
  }

  private void saveStatistics(ProfileEntity profileEntity, Timestamp timestamp) {
    if (profileEntity.getWhitelist() != null) {
      int incorrectBlocks = countEntities(profileEntity.getWhitelist(), profileEntity.getModelName(), BLOCK, timestamp);
      statisticService.increaseBlocks(profileEntity.getModelName(), INCORRECT_BLOCKS, incorrectBlocks);
    }

    if (profileEntity.getBlacklist() != null) {
      int missedBlocks = countEntities(profileEntity.getBlacklist(), profileEntity.getModelName(), ALLOW, timestamp);
      statisticService.increaseBlocks(profileEntity.getModelName(), MISSED_BLOCKS, missedBlocks);
    }
  }

  private int countEntities(Set<String> set, String modelName, String action, Timestamp timestamp) {
    List<RequestEntity> requests = requestRepository.findByModelNameAndActionAndTimestampBefore(modelName, action,
        timestamp);
    int counter = 0;

    for (RequestEntity request : requests) {
      if (set.contains(request.getUrl())) {
        counter++;
      }
    }

    return counter;
  }

  public String getAction(String modelName, String deviceId, String url) {

    if (profileRepository.existsById(modelName)) {
      ProfileEntity profileEntity = profileRepository.getOne(modelName);

      if (profileEntity.getDefaultPolicy().equals(ALLOW)) {
        return profileEntity.getBlacklist().contains(url) ? BLOCK : ALLOW;
      } else {
        if (profileEntity.getWhitelist().contains(url)) {
          return ALLOW;
        } else {
          deviceService.setQuarantined(deviceId, true);
          return BLOCK;
        }
      }
    }

    return null;
  }
}

package com.project.iotshield.service;

import com.project.iotshield.model.entities.StatisticEntity;
import com.project.iotshield.repository.DeviceRepository;
import com.project.iotshield.repository.StatisticRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticService {

  private final DeviceRepository deviceRepository;
  private final StatisticRepository statisticRepository;

  public JSONObject getProtectedDevices() {
    JSONObject result = new JSONObject();
    result.put("number of devices", deviceRepository.countDevices());
    return result;
  }

  public JSONObject getHackedDevices() {
    JSONObject result = new JSONObject();
    result.put("quarantined devices", deviceRepository.countQuarantinedDevices());
    return result;
  }

  public JSONObject getBlocks(String type) {
    JSONObject result = new JSONObject();
    List<StatisticEntity> missedBlocks = statisticRepository.findByType(type);
    missedBlocks.forEach(m -> result.put(m.getModelName(), m.getCounter()));
    return result;
  }

  public void increaseBlocks(String modelName, String type, int increase) {
    StatisticEntity statisticEntity = statisticRepository.findByModelNameAndType(modelName, type);

    if (statisticEntity == null) {
      statisticEntity = StatisticEntity.builder()
            .modelName(modelName)
          .type(type)
          .counter(0)
          .build();
    }

    statisticEntity.setCounter(statisticEntity.getCounter() + increase);
    statisticRepository.save(statisticEntity);
  }
}

package com.project.iotshield.service;

import com.project.iotshield.model.entities.RequestEntity;
import com.project.iotshield.model.events.RequestEvent;
import com.project.iotshield.repository.RequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {

  public static final String REQUEST_ID = "request_id";
  public static final String ACTION = "action";
  public static final String DEVICE_ID = "device_id";
  public static final String QUARANTINED = "quarantined";
  public static final String PROFILE_NOT_FOUND = "profile not found";

  private final ProfileService profileService;
  private final DeviceService deviceService;
  private final RequestRepository requestRepository;

  public JSONArray processRequest(RequestEvent event) {

    log.debug("Processing request: {}", event);
    JSONArray response = new JSONArray();
    String modelName = event.getModelName();
    String deviceId = event.getDeviceId();
    String url = event.getUrl();

    RequestEntity requestEntity = RequestEntity.builder()
        .requestId(event.getRequestId())
        .deviceId(deviceId)
        .modelName(modelName)
        .url(url)
        .timestamp(event.getTimestamp())
        .build();

    if (deviceService.isQuarantined(deviceId)) {
      log.debug("Device is quarantined: {}", deviceId);
      requestEntity.setAction(QUARANTINED);
      requestRepository.save(requestEntity);
      return response;
    }

    String action = profileService.getAction(modelName, deviceId, url);

    if (action == null) {
      log.debug("Profile is not found for request: {}", event);
      requestEntity.setAction(PROFILE_NOT_FOUND);
      requestRepository.save(requestEntity);
      return response;
    }

    requestEntity.setAction(action);
    requestRepository.save(requestEntity);

    JSONObject requestActionResponse = new JSONObject();
    requestActionResponse.put(REQUEST_ID, requestEntity.getRequestId());
    requestActionResponse.put(ACTION, action);

    response.add(requestActionResponse);

    if (deviceService.isQuarantined(deviceId)) {
      JSONObject deviceActionResponse = new JSONObject();
      deviceActionResponse.put(DEVICE_ID, deviceId);
      deviceActionResponse.put(ACTION, QUARANTINED);

      response.add(deviceActionResponse);
    }

    return response;
  }
}

package com.project.iotshield.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.iotshield.model.events.ProfileEvent;
import com.project.iotshield.model.events.RequestEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {

  private final ObjectMapper objectMapper;
  private final ProfileService profileService;
  private final RequestService requestService;

  public JSONArray dispatchByType(String event) {

    try {
      JsonNode node = objectMapper.readTree(event);
      String type = node.get("type").textValue();

      switch (type) {
        case "profile_create": {
          ProfileEvent mappedEvent = objectMapper.treeToValue(node, ProfileEvent.class);
          profileService.createProfile(mappedEvent);
          log.debug("Profile created: {}", mappedEvent);
          break;
        }
        case "profile_update": {
          ProfileEvent mappedEvent = objectMapper.treeToValue(node, ProfileEvent.class);
          profileService.updateProfile(mappedEvent);
          log.debug("Profile updated: {}", mappedEvent);
          break;
        }
        case "request": {
          RequestEvent mappedEvent = objectMapper.treeToValue(node, RequestEvent.class);
          return requestService.processRequest(mappedEvent);
        }
        default: {
          log.error("Undefined request type.");
          return new JSONArray();
        }
      }
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    return new JSONArray();
  }
}

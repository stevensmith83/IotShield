package com.project.iotshield.controller;

import com.project.iotshield.service.EventService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

  private final EventService eventService;

  @PostMapping("events")
  public JSONArray eventListener(@RequestBody String event) {

    String[] lines = event.split(System.lineSeparator());
    JSONArray responses = new JSONArray();

    for (String line : lines) {
      JSONArray response = eventService.dispatchByType(line);

      if (response != null) {
        responses.addAll(response);
      }
    }

    return responses;
  }
}

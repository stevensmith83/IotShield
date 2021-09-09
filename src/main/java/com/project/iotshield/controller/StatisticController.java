package com.project.iotshield.controller;

import com.project.iotshield.service.StatisticService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticController {

  public static final String INCORRECT_BLOCKS = "incorrectBlocks";
  public static final String MISSED_BLOCKS = "missedBlocks";

  private final StatisticService statisticService;

  @GetMapping("protected")
  public JSONObject getProtectedDevices() {
    return statisticService.getProtectedDevices();
  }

  @GetMapping("hacked")
  public JSONObject getHackedDevices() {
    return statisticService.getHackedDevices();
  }

  @GetMapping("missedBlocks")
  public JSONObject getMissedBlocks() {
    return statisticService.getBlocks(MISSED_BLOCKS);
  }

  @GetMapping("incorrectBlocks")
  public JSONObject getIncorrectBlocks() {
    return statisticService.getBlocks(INCORRECT_BLOCKS);
  }
}

package com.project.iotshield.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestEvent {

  String type;

  @JsonProperty("request_id")
  String requestId;

  @JsonProperty("model_name")
  String modelName;

  @JsonProperty("device_id")
  String deviceId;

  String url;
  Timestamp timestamp;
}

package com.project.iotshield.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.HashSet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileEvent {

  String type;

  @JsonProperty("model_name")
  String modelName;

  @JsonProperty("default")
  String defaultPolicy;

  HashSet<String> whitelist;
  HashSet<String> blacklist;
  Timestamp timestamp;
}

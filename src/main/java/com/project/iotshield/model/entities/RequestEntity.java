package com.project.iotshield.model.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {

  @Id
  @Column(name = "request_id", nullable = false, unique = true)
  String requestId;

  @Column(name = "model_name")
  String modelName;

  @Column(name = "device_id")
  String deviceId;

  @Column(name = "url")
  String url;

  @Column(name = "timestamp")
  Timestamp timestamp;

  @Column(name = "action")
  String action;
}

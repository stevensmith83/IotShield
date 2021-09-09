package com.project.iotshield.model.entities;

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
@Table(name = "devices")
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {

  @Id
  @Column(name = "device_id", nullable = false, unique = true)
  String deviceId;

  @Column(name = "quarantined")
  boolean quarantined;
}

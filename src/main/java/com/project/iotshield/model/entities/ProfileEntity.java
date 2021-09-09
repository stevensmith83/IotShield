package com.project.iotshield.model.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {

  @Id
  @Column(name = "model_name", nullable = false, unique = true)
  String modelName;

  @Column(name = "default_policy")
  String defaultPolicy;

  @ElementCollection
  @Column(name = "whitelist")
  Set<String> whitelist;

  @ElementCollection
  @Column(name = "blacklist")
  Set<String> blacklist;
}

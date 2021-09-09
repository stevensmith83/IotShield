package com.project.iotshield.repository;

import com.project.iotshield.model.entities.StatisticEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<StatisticEntity, String> {

  List<StatisticEntity> findByType(String type);

  StatisticEntity findByModelNameAndType(String modelName, String type);
}

package com.project.iotshield.repository;

import com.project.iotshield.model.entities.RequestEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, String> {

  List<RequestEntity> findByModelNameAndActionAndTimestampBefore(String modelName, String action, Timestamp timestamp);
}

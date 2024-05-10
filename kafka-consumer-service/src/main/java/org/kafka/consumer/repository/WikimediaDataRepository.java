package org.kafka.consumer.repository;
import org.kafka.consumer.entity.WikimediaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikimediaDataRepository extends JpaRepository<WikimediaDataEntity, Integer>{

}
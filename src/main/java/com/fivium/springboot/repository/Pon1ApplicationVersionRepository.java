package com.fivium.springboot.repository;

import com.fivium.springboot.model.persistence.Pon1ApplicationVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pon1ApplicationVersionRepository extends CrudRepository<Pon1ApplicationVersion, Long> {
}

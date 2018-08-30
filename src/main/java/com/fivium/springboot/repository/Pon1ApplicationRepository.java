package com.fivium.springboot.repository;

import com.fivium.springboot.model.persistence.Pon1Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface Pon1ApplicationRepository extends CrudRepository<Pon1Application, Long> {

  List<Pon1Application> findByOrganisationIdIn(Collection<String> organisationIds);

}

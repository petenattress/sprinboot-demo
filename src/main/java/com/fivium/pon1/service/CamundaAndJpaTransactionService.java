package com.fivium.pon1.service;

import com.fivium.pon1.model.persistence.Person;
import com.fivium.pon1.repository.PersonRepository;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaAndJpaTransactionService {

  private final RuntimeService runtimeService;
  private final RepositoryService repositoryService;
  private final PersonRepository personRepository;

  @Autowired
  public CamundaAndJpaTransactionService(RuntimeService runtimeService, RepositoryService repositoryService,
                                         PersonRepository personRepository) {
    this.runtimeService = runtimeService;
    this.repositoryService = repositoryService;
    this.personRepository = personRepository;
  }

  public void startAndAddPerson(boolean fail) {

    Person person = new Person();
    person.setName("dummy person");
    personRepository.save(person);

    if (fail) {
      throw new RuntimeException("oh noes!");
    }

    runtimeService.startProcessInstanceByKey("mySimpleProcess");

  }
}

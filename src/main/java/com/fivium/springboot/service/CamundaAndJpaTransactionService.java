package com.fivium.springboot.service;

import com.fivium.springboot.model.persistence.Person;
import com.fivium.springboot.repository.PersonRepository;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public void startAndAddPerson(boolean fail) {

    Person person = new Person();
    person.setName("dummy person");
    personRepository.save(person);

    runtimeService.startProcessInstanceByKey("mySimpleProcess");

    if (fail) {
      throw new RuntimeException("oh noes!");
    }
  }

  public void startAndAddPersonNoTransaction(boolean fail) {

    Person person = new Person();
    person.setName("dummy person");
    personRepository.save(person);

    runtimeService.startProcessInstanceByKey("mySimpleProcess");

    if (fail) {
      throw new RuntimeException("oh noes!");
    }
  }
}

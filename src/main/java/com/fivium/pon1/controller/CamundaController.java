package com.fivium.pon1.controller;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camunda")
public class CamundaController {

  private final RuntimeService runtimeService;
  private final RepositoryService repositoryService;

  @Autowired
  public CamundaController(RuntimeService runtimeService, RepositoryService repositoryService) {
    this.runtimeService = runtimeService;
    this.repositoryService = repositoryService;
  }

  @GetMapping("/start")
  public void startProcess() {
    runtimeService.startProcessInstanceByKey("mySimpleProcess");
  }

}

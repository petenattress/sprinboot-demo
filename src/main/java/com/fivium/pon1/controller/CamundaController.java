package com.fivium.pon1.controller;

import com.fivium.pon1.service.CamundaAndJpaTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/camunda")
public class CamundaController {

  private final CamundaAndJpaTransactionService camundaAndJpaTransactionService;

  @Autowired
  public CamundaController(CamundaAndJpaTransactionService camundaAndJpaTransactionService) {
    this.camundaAndJpaTransactionService = camundaAndJpaTransactionService;
  }

  @GetMapping("/start")
  @ResponseBody
  public String startProcess(@RequestParam("fail") boolean fail) {
    camundaAndJpaTransactionService.startAndAddPerson(fail);
    return "OK";
  }

}

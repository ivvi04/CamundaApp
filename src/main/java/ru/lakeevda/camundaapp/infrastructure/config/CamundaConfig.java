package ru.lakeevda.camundaapp.infrastructure.config;

import io.camunda.client.annotation.Deployment;
import org.springframework.context.annotation.Configuration;

@Configuration
@Deployment(resources = {
        "classpath:processes/form/*.form",
        "classpath:processes/bpmn/*.bpmn"
})
public class CamundaConfig {
}

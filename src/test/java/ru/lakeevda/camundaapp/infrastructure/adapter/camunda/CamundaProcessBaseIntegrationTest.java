package ru.lakeevda.camundaapp.infrastructure.adapter.camunda;

import io.camunda.process.test.api.CamundaSpringProcessTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(properties = "camunda.client.worker.defaults.enabled=true")
@CamundaSpringProcessTest
@Sql(scripts = "/scripts/clean-db.sql")
public class CamundaProcessBaseIntegrationTest {

}

package com.sitionix.bffssox.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.autoconfigure.exclude="
                        + "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration"
        }
)
@ActiveProfiles("it")
class ActuatorHealthEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void givenStartedApplication_whenReadinessEndpointCalled_thenReturnUp() {
        //given when
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/bffssox/actuator/health/readiness",
                String.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    void givenStartedApplication_whenLivenessEndpointCalled_thenReturnUp() {
        //given when
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/bffssox/actuator/health/liveness",
                String.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
    }
}

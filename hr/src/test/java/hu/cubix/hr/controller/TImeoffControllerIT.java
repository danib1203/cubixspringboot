package hu.cubix.hr.controller;

import hu.cubix.hr.dto.TimeoffDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(NoSecurityConfiguration.class)
public class TImeoffControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    EmptyDb emptyDb;

    @Test
    void testGetAllTimeoffs() {
        List<TimeoffDto> timeoffs = getAllTimeoffs();
        System.out.println("Timeoffs before: " + timeoffs);
    }

    private List<TimeoffDto> getAllTimeoffs() {
        return webTestClient.get().uri("/api/timeoff")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TimeoffDto.class)
                .returnResult()
                .getResponseBody();
    }


    @BeforeEach
    void clearDb() {
        emptyDb.clearDB();
    }
}

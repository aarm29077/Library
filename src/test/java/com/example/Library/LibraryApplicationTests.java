package com.example.Library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LibraryApplicationTests {

    @Test
    void contextLoads() {
    }

 /*   private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "http://localhost:8080";

    @Test
    void testAddBookToUsersSimultaneously() throws InterruptedException {
        int numberOfUsers = 10; // The number of concurrent users
        long bookId = 6;
        AtomicBoolean lastBookReserved = new AtomicBoolean(false);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                String url = BASE_URL + "/users/" + userId + "/add-book/" + bookId;
                ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

                HttpStatus expectedStatus = HttpStatus.OK;
                assertEquals(expectedStatus, response.getStatusCode());

                if (response.getBody().equals("Book added to the user successfully")) {
                    lastBookReserved.set(true);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // Check if the last book was reserved by one of the users
        assertTrue(lastBookReserved.get());
    }

    @Test
    void testReleaseBookToUsersSimultaneously() throws InterruptedException {
        int numberOfUsers = 10; // The number of concurrent users
        long bookId = 6;
        AtomicBoolean lastBookReleased = new AtomicBoolean(false);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                String url = BASE_URL + "/users/" + userId + "/release-book/" + bookId;
                ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

                HttpStatus expectedStatus = HttpStatus.OK;
                assertEquals(expectedStatus, response.getStatusCode());

                if (response.getBody().equals("Book released successfully")) {
                    lastBookReleased.set(true);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // Check if the last book was released by one of the users
        assertTrue(lastBookReleased.get());
    }*/


}

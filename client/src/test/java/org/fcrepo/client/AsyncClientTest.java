package org.fcrepo.client;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;

import static org.fcrepo.client.MockHttpExpectations.host;
import static org.fcrepo.client.MockHttpExpectations.port;
import static org.fcrepo.client.TestUtils.setField;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
final class AsyncClientTest {

    @Test
    void asyncClientTest() throws ExecutionException, InterruptedException,
            URISyntaxException {
        Assertions.assertThrows(ExecutionException.class, () -> {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/fcrepo/rest"))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse

                    .BodyHandler.asString());

            Thread.sleep(5000);
            if (response.isDone()) {
                System.out.println(response.get().statusCode());
                System.out.println(response.get().body());
            } else {
                response.cancel(true);
                System.out.println("Request took more than 5 seconds... cancelling.");
            }
        });
    }
}

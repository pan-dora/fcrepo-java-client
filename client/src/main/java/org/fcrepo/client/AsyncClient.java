package org.fcrepo.client;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

final class AsyncClient {

    public static void main(final String[] args) throws ExecutionException, InterruptedException,
            URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.google.com/"))
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
    }
}

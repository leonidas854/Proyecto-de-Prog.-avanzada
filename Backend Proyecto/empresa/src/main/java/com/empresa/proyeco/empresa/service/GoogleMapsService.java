package com.empresa.proyeco.empresa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class GoogleMapsService {
    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGeocode(String address) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
            address, apiKey
        );
        return restTemplate.getForObject(url, String.class);
    }

    public double calcularDistancia(String origen, String destino) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
            origen, destino, apiKey
        );
        String response = restTemplate.getForObject(url, String.class);
        return extractDistanceFromResponse(response);
    }

    private double extractDistanceFromResponse(String response) {
        return 0.0;
    }

}

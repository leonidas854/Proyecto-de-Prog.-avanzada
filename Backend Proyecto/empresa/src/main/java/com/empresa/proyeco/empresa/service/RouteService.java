package com.empresa.proyeco.empresa.service;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class RouteService {
    private final Graph<String, DefaultWeightedEdge> ciudadGrafo;
    private final Map<String, String> locaciones;

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public RouteService(RestTemplate restTemplate) {
        this.ciudadGrafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        this.locaciones = new HashMap<>();
        this.restTemplate = restTemplate;
    }

    public void agregarCiudad(String interseccion) {
        if (!ciudadGrafo.containsVertex(interseccion)) {
            ciudadGrafo.addVertex(interseccion);
            locaciones.put(interseccion, interseccion);
        }
    }

    public void agregarArco(String origen, String destino) {
        if (!ciudadGrafo.containsVertex(origen)) {
            agregarCiudad(origen);
        }
        if (!ciudadGrafo.containsVertex(destino)) {
            agregarCiudad(destino);
        }
        double distancia = calcularDistanciaConGoogleMaps(origen, destino);
        ciudadGrafo.addEdge(origen, destino);
        ciudadGrafo.setEdgeWeight(ciudadGrafo.getEdge(origen, destino), distancia);
    }

    public Map<String, String> obtenerLocaciones() {
        return locaciones;
    }

    public double calcularDistancia(String origen, String destino) {
        if (ciudadGrafo.containsEdge(origen, destino)) {
            return ciudadGrafo.getEdgeWeight(ciudadGrafo.getEdge(origen, destino));
        }
        return -1; 
    }

    private double calcularDistanciaConGoogleMaps(String origen, String destino) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
            origen, destino, apiKey
        );
    
        String response = restTemplate.getForObject(url, String.class);
        double distanciaEnMetros = extractDistanceFromResponse(response);
        return distanciaEnMetros / 1000.0; 
    }
    

    private double extractDistanceFromResponse(String response) {
       
        try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);

        JsonNode distanceNode = rootNode
            .path("rows")
            .get(0)
            .path("elements")
            .get(0)
            .path("distance")
            .path("value");

        if (distanceNode.isNumber()) {
            return distanceNode.asDouble(); 
        } else {
            throw new RuntimeException("No se pudo obtener la distancia de la respuesta");
        }
        } catch (JsonProcessingException | RuntimeException e) {
        throw new RuntimeException("Error procesando la respuesta de Google Maps API: " + e.getMessage());
        }
    }
}

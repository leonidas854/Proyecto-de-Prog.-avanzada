package com.empresa.proyeco.empresa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class RouteService {
    private final Map<String, double[]> ubicaciones; 
    private final List<Edge> edges;
    private static final int INF = Integer.MAX_VALUE; 

    @Value("${google.api.key}")
    private String googleApiKey; 

    private final RestTemplate restTemplate;

    public RouteService(RestTemplate restTemplate) {
        this.ubicaciones = new HashMap<>();
        this.edges = new ArrayList<>();
        this.restTemplate = restTemplate;
    }

   
    public void agregarUbicacion(String nombre, double latitud, double longitud) {
        if (!ubicaciones.containsKey(nombre)) {
            ubicaciones.put(nombre, new double[]{latitud, longitud});
        }
    }

   
    public List<Map<String, Double>> calcularRutaOptimaDesdeSucursal(String nombreSucursal, List<String> clientes) {
      
        if (!ubicaciones.containsKey(nombreSucursal)) {
            throw new RuntimeException("Sucursal no encontrada en el sistema: " + nombreSucursal);
        }
        for (String cliente : clientes) {
            if (!ubicaciones.containsKey(cliente)) {
                throw new RuntimeException("Cliente no encontrado en el sistema: " + cliente);
            }
        }

        
        List<String> nodos = new ArrayList<>();
        nodos.add(nombreSucursal);
        nodos.addAll(clientes);

       
        int[][] matrizDistancias = crearMatrizDistancias(nodos);
        List<Edge> edges = crearListaEdges(nodos);

       
        ChinesePostman chinesePostman = new ChinesePostman();
        int costoTotal = chinesePostman.solve(matrizDistancias, edges);

       

        List<String> rutaOrdenada = nodos; 

       
        List<Map<String, Double>> coordenadas = new ArrayList<>();
        for (String nodo : rutaOrdenada) {
            double[] coords = ubicaciones.get(nodo);
            Map<String, Double> punto = new HashMap<>();
            punto.put("lat", coords[0]);
            punto.put("lng", coords[1]);
            coordenadas.add(punto);
        }

        return coordenadas;
    }

   
    private int[][] crearMatrizDistancias(List<String> nodos) {
        int n = nodos.size();
        int[][] matriz = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(matriz[i], Integer.MAX_VALUE);
        }

     
        for (Edge edge : edges) {
            matriz[edge.from][edge.to] = edge.cost;
            matriz[edge.to][edge.from] = edge.cost; 
        }

        return matriz;
    }

  
    private List<Edge> crearListaEdges(List<String> nodos) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < nodos.size(); i++) {
            for (int j = i + 1; j < nodos.size(); j++) {
                double distancia = calcularDistancia(nodos.get(i), nodos.get(j));
                edges.add(new Edge(i, j, (int) distancia));
            }
        }
        return edges;
    }


    private double calcularDistancia(String origen, String destino) {
        double[] origenCoords = ubicaciones.get(origen);
    double[] destinoCoords = ubicaciones.get(destino);

    String origenStr = origenCoords[0] + "," + origenCoords[1];
    String destinoStr = destinoCoords[0] + "," + destinoCoords[1];

    String url = String.format(
        "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
        origenStr, destinoStr, googleApiKey
    );

    try {
        String response = restTemplate.getForObject(url, String.class);

        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

       
        String status = rootNode.path("status").asText();
        if (!"OK".equals(status)) {
            throw new RuntimeException("Error en la API de Google Maps: " + status);
        }

      
        JsonNode element = rootNode.path("rows").get(0).path("elements").get(0);
        String elementStatus = element.path("status").asText();
        if (!"OK".equals(elementStatus)) {
            throw new RuntimeException("No se pudo calcular la distancia: " + elementStatus);
        }

        int distanciaEnMetros = element.path("distance").path("value").asInt();
        return distanciaEnMetros / 1000.0; 
    } catch (JsonProcessingException | RuntimeException e) {
        throw new RuntimeException("Error al calcular la distancia con Google Maps API: " + e.getMessage(), e);
    }
    }

    private static final double COSTO_POR_KILOMETRO = 0.31; 

    public double calcularCosto(String origen, String destino) {
    double distanciaEnKm = calcularDistancia(origen, destino);
    return distanciaEnKm * COSTO_POR_KILOMETRO;
    }

  
    private static class Edge {
        int from, to, cost;

        Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    private static class ChinesePostman {

        private int solve(int[][] graph, List<Edge> edges) {
            int n = graph.length;

          
            int[] degrees = calculateDegrees(graph);
            List<Integer> oddVertices = findOddVertices(degrees);

            if (oddVertices.isEmpty()) {
      
                return calculateCost(edges);
            }

     
            int[][] minPaths = floydWarshall(graph);
            int minCost = findMinimumMatching(oddVertices, minPaths);

         
            return calculateCost(edges) + minCost;
        }

        private int[] calculateDegrees(int[][] graph) {
            int n = graph.length;
            int[] degrees = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (graph[i][j] != INF && graph[i][j] > 0) {
                        degrees[i]++;
                    }
                }
            }
            return degrees;
        }

        private List<Integer> findOddVertices(int[] degrees) {
            List<Integer> oddVertices = new ArrayList<>();
            for (int i = 0; i < degrees.length; i++) {
                if (degrees[i] % 2 != 0) {
                    oddVertices.add(i);
                }
            }
            return oddVertices;
        }

        private int calculateCost(List<Edge> edges) {
            int totalCost = 0;
            for (Edge edge : edges) {
                totalCost += edge.cost;
            }
            return totalCost;
        }

        private int[][] floydWarshall(int[][] graph) {
            int n = graph.length;
            int[][] dist = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = graph[i][j];
                }
            }
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (dist[i][k] != INF && dist[k][j] != INF) {
                            dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                        }
                    }
                }
            }
            return dist;
        }

        private int findMinimumMatching(List<Integer> oddVertices, int[][] minPaths) {
            int size = oddVertices.size();
            int[][] cost = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cost[i][j] = minPaths[oddVertices.get(i)][oddVertices.get(j)];
                }
            }

            return matchMinimumCost(cost, size);
        }

        private int matchMinimumCost(int[][] cost, int size) {
            int[] dp = new int[1 << size];
            Arrays.fill(dp, INF);
            dp[0] = 0;

            for (int mask = 0; mask < (1 << size); mask++) {
                for (int i = 0; i < size; i++) {
                    if ((mask & (1 << i)) != 0) continue;
                    for (int j = i + 1; j < size; j++) {
                        if ((mask & (1 << j)) != 0) continue;

                        int nextMask = mask | (1 << i) | (1 << j);
                        dp[nextMask] = Math.min(dp[nextMask], dp[mask] + cost[i][j]);
                    }
                }
            }

            return dp[(1 << size) - 1];
        }
    }
}


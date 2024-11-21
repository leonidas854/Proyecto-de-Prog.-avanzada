package com.empresa.proyeco.empresa.service;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Service;
@Service
public class RouteService {
    private  Graph<String,DefaultWeightedEdge> ciudadGrafo;
    private Map<String,String> Locaciones;

    public RouteService(){
        ciudadGrafo = new SimpleGraph<>(DefaultWeightedEdge.class);
        Locaciones = new HashMap<>();
        }
    private void agregar_Ciudad(String interseccion){
        ciudadGrafo.addVertex(interseccion);
    }

    private void agregar_arco(String origen,String destino,double distancia){
        ciudadGrafo.addEdge(origen,destino);
        ciudadGrafo.setEdgeWeight(ciudadGrafo.getEdge(origen, destino), distancia);

    }
    public double calcular_Distancia(String origen,String destino){
        return ciudadGrafo.getEdgeWeight(ciudadGrafo.getEdge(origen, destino));
    }
}

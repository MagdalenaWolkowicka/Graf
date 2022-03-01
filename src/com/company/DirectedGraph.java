package com.company;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {

    // liczba wierzcholkow
    private final int v;
    // liczba krawedzi
    private int e;
    // listy sasiedztwa
    private List<DirectedEdge>[] neighborhoodLists;

    @SuppressWarnings("unchecked")
    public DirectedGraph(int v) {
        this.v = v;
        this.e = 0;
        this.neighborhoodLists = (List<DirectedEdge>[]) new List[v];
        for (int i = 0; i < v ; i++) {
            neighborhoodLists[i] = new ArrayList<DirectedEdge>();
        }
    }

    public int getNumberOfEdges() {
        return e;
    }

    public int getNumberOfVertices() {
        return v;
    }

    // Dodaje krawędź skierowaną do odpowiedniej listy sąsiedztwa, listy wierzchołka z której zaczyna się krawędź
    public void addEdge(DirectedEdge edge) {
        neighborhoodLists[edge.from()].add(edge);
        e++;
    }

    // Zwraca listę sąsiedztwa danego wierzchołka
    public Iterable<DirectedEdge> getNeighborhoodList(int v) {
        return neighborhoodLists[v];
    }
}

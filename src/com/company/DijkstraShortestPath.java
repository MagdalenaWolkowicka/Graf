package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

public class DijkstraShortestPath {


    class DistanceToEdge implements Comparable<DistanceToEdge> {
        // krawędź
        private final int edge;
        // odległość do tej krawędzi
        private long distance;

        public DistanceToEdge(int edge, long distance) {
            this.edge = edge;
            this.distance = distance;
        }

        public long getDistance() {
            return distance;
        }

        public void setDistance(long distance) {
            this.distance = distance;
        }

        public int getEdge() {
            return edge;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (int) (distance ^ (distance >>> 32));
            result = prime * result + edge;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DistanceToEdge other = (DistanceToEdge) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (distance != other.distance)
                return false;
            if (edge != other.edge)
                return false;
            return true;
        }

        @Override
        public int compareTo(DistanceToEdge param) {
            int cmp = new Long(distance).compareTo(param.getDistance());

            if (cmp == 0) {
                return new Integer(edge).compareTo(param.getEdge());
            }
            return 0;
        }

        private DijkstraShortestPath getOuterType() {
            return DijkstraShortestPath.this;
        }
    }

    ///////////////////////////////////////////////////////////////////////


    // przechowuje krawedz z ktorej jest najblizej do biezacej okreslonej indeksem tablicy
    private DirectedEdge[] edgeTo;

    // przechowuje najkrotsze znalezione odleglosci do danego wierzcholka
    private Long[] distanceTo;

    // kolejka przechowujaca biezace krawedzie uszeregowane od najkrotszej do najdluzszej
    private Queue<DistanceToEdge> priorityQueue;

    public DijkstraShortestPath(DirectedGraph graph, int source) {
        // inicjacja wewnetrznych struktur
        edgeTo = new DirectedEdge[graph.getNumberOfVertices()];
        distanceTo = new Long[graph.getNumberOfVertices()];
        priorityQueue = new PriorityQueue<DistanceToEdge>(
                graph.getNumberOfVertices());

        // dla kazdego wierzcholka v ustawiamy najwieksza mozliwa wartosc jaka moze przechowywac
        for (int v = 0; v < graph.getNumberOfVertices(); v++) {
            distanceTo[v] = Long.MAX_VALUE;
        }
        // odleglosc do wierzcholka zrodlowego to 0
        distanceTo[source] = 0L;

        // wstawiamy wierzcholek zrodlowy do kolejki, od niego rozpoczynamy poszukiwania
        priorityQueue.offer(new DistanceToEdge(source, 0L));

        // przeprowadzamy relaksacje grafu dopoki kolejka nie jest pusta
        while (!priorityQueue.isEmpty()) {
            relax(graph, priorityQueue.poll().getEdge());
        }

    }

    private void relax(DirectedGraph graph, int v) {
        // przegladamy listy sasiedztwa wszystkich wierzcholkow
        for (DirectedEdge edge : graph.getNeighborhoodList(v)) {
            int w = edge.to();

            // sprawdzamy czy zmiana wierzcholka skroci dystans z wierzcholka zrodlowego
            if (distanceTo[w] > distanceTo[v] + edge.getWeight()) {
                distanceTo[w] = distanceTo[v] + edge.getWeight();
                edgeTo[w] = edge;
                DistanceToEdge dte = new DistanceToEdge(w, distanceTo[w]);

                // jesli jest juz krawedz o tej wadze to ja usuwamy, bo znalezlismy lepsza droge
                priorityQueue.remove(dte);
                // wstawiamy nowa krawedz z aktualna znaleziona odlegloscia
                priorityQueue.offer(dte);
            }
        }
    }

    // jesli zwrocona wartosc wynosi Long.MAX_VALUE to oznacza, ze dany wierzcholek jest nieosiagalny ze zrodla
    public long getDistanceTo(int v) {
        return distanceTo[v];
    }

    // jesli istnieje droga do danego wierzcholka jest mniejsza od
    // Long.MAX_VALUE, ktore zostalo inicjalnie ustawione dla wszystkich
    // wierzcholkow
    public boolean hasPathTo(int v) {
        return distanceTo[v] < Long.MAX_VALUE;
    }

    // jesli nie istnieje sciezka do danego wierzcholka zostanie zwrocona pusta
    // kolekcja
    public Iterable<DirectedEdge> getPathTo(int v) {
        Deque<DirectedEdge> path = new ArrayDeque<DirectedEdge>();
        // jesli nie istnieje sciezka zwroc pusta sciezke
        if (!hasPathTo(v)) {
            return path;
        }
        // dopoki istnieje krawedz dodawaj ja do stosu
        // krawedz zrodlowa jest oznaczona jako null
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
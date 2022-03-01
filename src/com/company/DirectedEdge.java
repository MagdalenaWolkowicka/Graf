package com.company;

public class DirectedEdge {

    // wierzcholek zrodlowy
    private final int from;
    // wierzcholek docelowy
    private final int to;
    // waga
    private final long weight;

    public DirectedEdge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public long getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%d->%d (%d) ", from, to, weight);
    }
}

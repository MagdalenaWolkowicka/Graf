package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // n - liczba wierzchołków
        // m - liczba krawędzi
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        // weight - waga w tym przypadku wynosi zawsze 1
        int weight = 1;
        DirectedGraph graph = new DirectedGraph(n + 1);


        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.addEdge(new DirectedEdge(from, to, weight));
        }
        // i - start
        // j - finisz
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        DijkstraShortestPath shortestPath = new DijkstraShortestPath(graph, i);

        // Wyświetlanie najkrótszej ścieżki

        if (i >= 1 && j <= n) {
            if (shortestPath.hasPathTo(j)) {
                System.out.println(shortestPath.getDistanceTo(j));
            } else {
                System.out.println("-1");
            }
        } else {
            System.out.println("Błędne dane");
        }
    }
}
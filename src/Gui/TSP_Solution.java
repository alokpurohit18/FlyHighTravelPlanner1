package Gui;

import java.util.Stack;

public class TSP_Solution {

    private int numberOfNodes;
    private Stack<Integer> stack;

    public TSP_Solution() {
        stack = new Stack<>();
    }

    public String[] findBestRoute(int[][] adjacencyMatrix, String[] locations) {

        int counter = 1;
        numberOfNodes = adjacencyMatrix[1].length - 1;
        int[] visited = new int[numberOfNodes + 1];
        String[] solution = new String[numberOfNodes + 2];
        visited[1] = 1;
        stack.push(1);
        int element, dst = 0, i;
        int min;
        boolean minFlag = false;
        solution[counter] = locations[1];
        solution[numberOfNodes + 1] = locations[1];
        counter++;

        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 1;
            min = Integer.MAX_VALUE;
            while (i <= numberOfNodes) {
                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0) {
                    if (min > adjacencyMatrix[element][i]) {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag) {
                visited[dst] = 1;
                stack.push(dst);
                solution[counter] = locations[dst];
                counter++;
                minFlag = false;
                continue;
            }
            stack.pop();
        }

        return solution;
    }
}
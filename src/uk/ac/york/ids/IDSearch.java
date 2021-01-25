package uk.ac.york.ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class IDSearch {
	private static final int MAX_DEPTH = 90;

	public static void main(String[] args) throws Exception {
		System.out.println("---------- Iterative Deepening Example ----------");
		long startTime = System.nanoTime();

		IDSearch idSearch = new IDSearch();

		idSearch.iterativeDeepening();

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);

		duration = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);

		System.out.println("\nTotal Duration: " + duration + "ms");
	}

	private void iterativeDeepening() throws Exception {
		List<int[][]> problems = getProblems();

		for (int i = 0; i < problems.size(); i++) {
			int[][] problem = problems.get(i);

			long startTime = System.nanoTime();

			System.out.println("\n---------- Solving Problem " + (i + 1) + " ----------");

			for (int j = 1; j <= MAX_DEPTH; j++) {
				boolean solved = depthLimitedSearch(j - 1, problem);

				if (solved) {					
					break;
				}
			}

			long endTime = System.nanoTime();

			long duration = (endTime - startTime);

			duration = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);

			System.out.println("\nExecution Time of IDS: " + duration + "ms");
		}
	}

	private boolean depthLimitedSearch(int depthLimit, int[][] problem) throws Exception {
		Stack<SearchNode> frontier = new Stack<>();
		List<SearchNode> explored = new ArrayList<>();
		SearchNode startNode = init(problem);		

		frontier.add(startNode);
		
		while (!frontier.isEmpty()) {			
			// Get the next node to explore			
			SearchNode currentNode = frontier.pop();			
			
			if (currentNode.getDepth() <= depthLimit) {				
				// Add node to explored set
				explored.add(currentNode);
				
				// Expand node and get reachable neighbours
				for (SearchNode node : currentNode.expand()) {					
					if (problem[node.getY()][node.getX()] != 1) {
						if (problem[node.getY()][node.getX()] == 3) {
							System.out.print("\nProblem Solved at Depth: " + node.getDepth());
							System.out.print("\nGoal Coordinates: x = " + node.getX() + ", y = " + node.getY() + "\n");													
							
							return true;
						} else if (!frontier.contains(node) && !explored.contains(node)) {							
							frontier.add(node);
						}
					}
				}
			}
		}

		return false;
	}
	
	private void printSolution(SearchNode node) {
		List<SearchNode> searchPath = new ArrayList<>();
		
		while(node.getParent() != null) {
			
			searchPath.add(node.getParent());
			
			node = node.getParent();
		}
		
		Collections.reverse(searchPath);
		
		for(SearchNode searchNode : searchPath) {
			System.out.println(searchNode);
		}
	}
	
	private List<int[][]> getProblems() {
		List<int[][]> problems = new ArrayList<>();

		problems.add(Grids.GRID1);
		problems.add(Grids.GRID2);
		problems.add(Grids.GRID3);
		problems.add(Grids.GRID4);
		problems.add(Grids.GRID5);

		return problems;
	}

	private SearchNode init(int[][] problemSpace) {
		SearchNode initialNode = null;

		for (int y = 0; y < problemSpace.length; y++) {
			for (int x = 0; x < problemSpace[y].length; x++) {
				if (problemSpace[y][x] == 2) {
					initialNode = new SearchNode((byte) x, (byte) y, (byte) 0, null);
				}
			}
		}

		return initialNode;
	}
}

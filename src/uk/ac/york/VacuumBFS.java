package uk.ac.york;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VacuumBFS {
	public static void main(String[] args) throws Exception {
		VacuumBFS vacuum = new VacuumBFS();

		for (int i = 1; i <= 1000; i++) {
			System.out.println("---------- Vacuum Problem Attempt " + i + " ----------\n");
			vacuum.solve();
		}
	}

	public void solve() throws Exception {
		Queue<Integer> frontier = new LinkedList<>();
		List<Integer> explored = new ArrayList<>();
		int[][] states = getStateMap();		
		int initialState = getInitialState();
		int counter = 1;
		
		frontier.add(initialState);
		
		System.out.println("Selected Initial State: " + initialState);

		while (true) {
			counter ++;
			
			// If frontier empty then no solution
			if (frontier.isEmpty()) {
				throw new Exception("Problem has no solution");
			}

			// Get the next item from the queue
			Integer currentNode = frontier.poll();				
			
			System.out.println("Popped: " + currentNode);
			
			// Add to the explored list
			explored.add(currentNode);

			// Get the state of the current node from the map
			int[] currentState = states[currentNode - 1];
			
			// Expand node
			for (int i = 1; i < currentState.length; i++) {
				
				// check if this is the goal
				if (goalTest(currentState[i])) {
					System.out.println("\nFound Goal Node: " + currentState[i]);
					System.out.println("\nProblem Solved In " + counter + " Iterations\n\n");

					return;
				}		
				
				// Add state to the frontier if not already explored or a candidate to be
				// explored
				if (!frontier.contains(currentState[i]) && !explored.contains(currentState[i])) {										
					frontier.add(currentState[i]);
					
					System.out.println("Frontier: " + frontier);
				}
			}
		}
	}
	
	private int[][] getStateMap() {
		int[][] stateMap = { 
				{ 1, 2, 3 }, 
				{ 2, 1, 6 }, 
				{ 3, 1, 4 }, 
				{ 4, 3, 8 }, 
				{ 5, 6, 7 }, 
				{ 6, 2, 5 }, 
				{ 7, 5, 8 },
				{ 8, 4, 7 } 
			};
		
		return stateMap;
	}
	
	private int getInitialState() {
		int startPos = ((int)(Math.random() * 100) & 1) + 1;
		
		return startPos;
	}

	private boolean goalTest(int s) {
		boolean solved = false;

		if (s == 7 || s == 8) {
			solved = true;
		}

		return solved;
	}
}

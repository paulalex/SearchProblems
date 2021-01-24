package uk.ac.york;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Vacuum {
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int SUCK = 2;
	
	public static void main(String[] args) throws Exception {
		Vacuum vacuum = new Vacuum();
		
		for(int i = 0; i <= 1000; i++) {
			vacuum.solve();	
		}		
	}
	
	public void solve() throws Exception {
		Queue<Integer[]> frontier = new LinkedList<>();
		List<Integer[]> explored = new ArrayList<>();			
		int dirtLeft = getZeroOrOne();
		int dirtRight = getZeroOrOne();
		int startPos = getZeroOrOne();
		int iterationCount = 0;
		
		Integer[] initialState = {dirtLeft, dirtRight, startPos};
				
		frontier.add(initialState);
		
		System.out.println("---------- Initial Vacuum Parameters ----------\n");
		System.out.println("Starting in position: " + startPos);
		System.out.println("Dirt in left: " + dirtLeft);
		System.out.println("Dirt in right: " + dirtRight);
		
		while(true) {		
			// If frontier empty then no solution
			if(frontier.isEmpty()) {
				throw new Exception("Problem has no solution");
			}
			
			// Get the next item from the queue
			Integer[] currentState = frontier.poll();
									
			// Add to the explored list
			explored.add(currentState);
						
			// Get a list of actions
			List<Integer> actions = actions(currentState);
			
			// Choose a suitable action
			int action = getAction(actions);
			
			// Get the result of performing the selected action on the current state
			Integer[] newState = result(currentState, action);
			
			iterationCount++;
			
			// check if this is the goal
			if(goalTest(newState)) {
				System.out.println("\nProblem Solved in " + iterationCount + "\n\n");
				
				return;
			}
			
			// If the current state has not been explored and it is not in the frontier already
			// then enqueue it in the frontier
			if(!explored.contains(newState) && !frontier.contains(newState)) {				
				frontier.add(newState);
			}						
		}			
	}
	
	public int getAction(List<Integer> actions) {
		int action = -1;
		
		if(actions.contains(SUCK)) {
			action = SUCK;
		} else if(actions.contains(LEFT)) {
			action = LEFT;
		} else if(actions.contains(RIGHT)) {
			action = RIGHT;
		}
		
		return action;
	}
			
	public List<Integer> actions(Integer[] s) {
		// State takes the form [dirtLeft, dirtRight, vacuumLocation]
		// where 0 is falsy and 1 is true and 0 if left and 1 is right (based on array indexes)		
		List<Integer> actions = new ArrayList<>();
		
		// If the robot can move left
		if(s[2] == 1) {
			actions.add(LEFT);
		}
		
		// If the robot can move right
		if(s[2] == 0) {
			actions.add(RIGHT);
		}
		
		// If there is dirt wherever the robot is
		if((s[0] == 1 && s[2] == 0) || s[1] == 1 && s[2] == 1) {
			actions.add(SUCK);
		}
		
		return actions;
	}
	
	public Integer[] result(Integer[] currentState, int a) {
		Integer[] newState = {currentState[0], currentState[1], currentState[2]};
		
		if(a == SUCK && currentState[2] == LEFT) {
			System.out.println("Clearing dirt from: " + currentState[2]);
			newState[LEFT] = 0;
		} else if(a == SUCK && currentState[2] == RIGHT) {
			System.out.println("Clearing dirt from: " + currentState[2]);
			newState[RIGHT] = 0;
		}
		
		if(a == LEFT && currentState[2] == RIGHT) {			
			newState[2] = 0;
			System.out.println("Moving left from: " + currentState[2] + " to " + newState[2]);
		} else if(a == RIGHT && currentState[2] == LEFT) {			
			newState[2] = 1;
			System.out.println("Moving right from: " + currentState[2] + " to " + newState[2]);
		}		
		
		return newState;
	}
	
	public int getZeroOrOne() {
		int zeroOrOne = ((int)(Math.random() * 100) & 1);
		
		return zeroOrOne;
	}
		
	
	public boolean goalTest(Integer[] s) {
		boolean solved = false;
		
		if(s[0] == 0 && s[1] == 0) {
			solved = true;
		}
		
		return solved;
	}
}

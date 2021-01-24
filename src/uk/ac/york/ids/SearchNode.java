package uk.ac.york.ids;

import java.util.ArrayList;
import java.util.List;

public class SearchNode {
	private byte x;
	private byte y;
	private byte depth;
	
	public SearchNode(byte x, byte y, byte depth) {
		super();
		this.x = x;
		this.y = y;
		this.depth = depth;
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public byte getDepth() {
		return depth;
	}
	
	public List<SearchNode> expand() {
		List<SearchNode> neighbours = new ArrayList<SearchNode>();
		byte depth = (byte) (this.getDepth() + 1);
		byte x = this.getX();
		byte y = this.getY();
		
		// if column is 0 no left neighbour
		if(x != 0) {
			SearchNode left = new SearchNode((byte) (x - 1), y, depth);
			neighbours.add(left);
		}
		
		// If column is 14 no right neighbour
		if(x != 14) {
			SearchNode right = new SearchNode((byte) (x + 1), y, depth);
			neighbours.add(right);
		}
		
		// If row is 0 no up neighbour
		if(y != 0) {
			SearchNode up = new SearchNode(x, (byte) (y - 1), depth);
			neighbours.add(up);
		}

		// if row is 14 no down neighbour
		if(y != 14) {
			SearchNode down = new SearchNode(x, (byte) (y + 1), depth);
			neighbours.add(down);
		}
		
		return neighbours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
		result = prime * result + x;
		result = prime * result + y;
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
		SearchNode other = (SearchNode) obj;		
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SearchNode [x=" + x + ", y=" + y + ", depth=" + depth + "]";
	}		
}

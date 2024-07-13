
public class Node { // Node class makes sure all nodes have necessary node features.
	int x;
	int y;
	String label;
	boolean highlighted;
	
	public Node(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
		highlighted= false;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public boolean getHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}

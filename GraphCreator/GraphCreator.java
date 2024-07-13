/*
 * Emma Shu, 5/17/19: Graph Creator is a project that creates nodes and edges, allowing the user to label these
 * nodes and edges, and finding the least cost path between the nodes based on the labels of the edges.
 * The Graph Creator class has a Graph Panel that keeps track of all the nodes and edges.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreator implements ActionListener, MouseListener {

    JFrame frame = new JFrame();
    GraphPanel panel = new GraphPanel();
    JButton nodeB = new JButton("Node");
    JButton edgeB = new JButton("Edge");
    JButton connectedB = new JButton("Test Connected");
    JButton salesmanB = new JButton("Shortest Path");
    JTextField labelsTF = new JTextField("A");
    JTextField firstNode = new JTextField("First");
    JTextField secondNode = new JTextField("Second");
    JTextField salesmanStartTF = new JTextField("A");
    final int NODE_CREATE = 0;
    final int EDGE_FIRST = 1;
    final int EDGE_SECOND = 2;
    int state = NODE_CREATE;
    Node first = null;
    Container west = new Container();
    Container east = new Container();
    Container south = new Container();
    ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
    ArrayList<Integer> costs = new ArrayList<Integer>();

    public GraphCreator() { //Layout of the display
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        west.setLayout(new GridLayout(3, 1));
        west.add(nodeB);
        west.add(edgeB);
        west.add(labelsTF);
        nodeB.addActionListener(this);
        edgeB.addActionListener(this);
        panel.addMouseListener(this);
        frame.add(west, BorderLayout.WEST);
        east.setLayout(new GridLayout(3, 1));
        east.add(firstNode);
        east.add(secondNode);
        east.add(connectedB);
        connectedB.addActionListener(this);
        frame.add(east, BorderLayout.EAST);
        south.setLayout(new GridLayout(1, 2));
        south.add(salesmanStartTF);
        south.add(salesmanB);
        salesmanB.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        nodeB.setBackground(Color.GREEN);
        edgeB.setBackground(Color.LIGHT_GRAY);
        state = NODE_CREATE;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GraphCreator();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Adds node to node list which will then be painted through paintComponent method in GraphPanel class during frame.repaint
        if (state == NODE_CREATE) {
            panel.addNode(e.getX(), e.getY(), labelsTF.getText());
        } // Highlights first node
        else if (state == EDGE_FIRST) {
            Node n = panel.getNode(e.getX(), e.getY());
            if (n != null) {
                first = n;
                state = EDGE_SECOND;
                first.setHighlighted(true);
            }
        } // Draws the line through edgeList and GraphPanel class, makes sure specifications are met
        else if (state == EDGE_SECOND) {
            Node n = panel.getNode(e.getX(), e.getY());
            if (n != null && !first.equals(n)) {
                String s = labelsTF.getText();
                boolean valid = true;
                for (int i = 0; i < s.length(); i++) {
                    if (!Character.isDigit(s.charAt(i))) {
                        valid = false;
                    }
                }
                if (valid) {
                    first.setHighlighted(false);
                    panel.addEdge(first, n, labelsTF.getText());
                    first = null;
                    state = EDGE_FIRST;
                } else {
                    JOptionPane.showMessageDialog(frame, "Can only have digits in edge labels.");
                }
            }
        }
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Change button colors
        if (e.getSource().equals(nodeB)) {
            nodeB.setBackground(Color.GREEN);
            edgeB.setBackground(Color.LIGHT_GRAY);
            state = NODE_CREATE;
        }
        if (e.getSource().equals(edgeB)) {
            edgeB.setBackground(Color.GREEN);
            nodeB.setBackground(Color.LIGHT_GRAY);
            state = EDGE_FIRST;
            panel.stopHighlighting();
            frame.repaint();
        }
        // Check for connections
        if (e.getSource().equals(connectedB)) {
            if (panel.nodeExists(firstNode.getText()) == false) {
                JOptionPane.showMessageDialog(frame, "First node is not in your graph.");
            } else if (panel.nodeExists(secondNode.getText()) == false) {
                JOptionPane.showMessageDialog(frame, "Second node is not in your graph.");
            } else { // Calls to class queue, composition relationship
                Queue queue = new Queue();
                ArrayList<String> connectedList = new ArrayList<String>();
                connectedList.add(panel.getNode(firstNode.getText()).getLabel());
                ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
                for (int i = 0; i < edges.size(); i++) {
                    queue.enqueue(edges.get(i));
                }
                while (!queue.isEmpty()) {
                    String currentNode = queue.dequeue();
                    if (!connectedList.contains(currentNode)) {
                        connectedList.add(currentNode);
                    }
                    edges = panel.getConnectedLabels(currentNode);
                    for (int i = 0; i < edges.size(); i++) {
                        if (!connectedList.contains(edges.get(i))) {
                            queue.enqueue(edges.get(i));
                        }
                    }
                }
                if (connectedList.contains(secondNode.getText())) { // Displays status
                    JOptionPane.showMessageDialog(frame, "Connected!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Not connected.");
                }
            }
        }
        if (e.getSource().equals(salesmanB)) { // Least cost path.
            if (panel.getNode(salesmanStartTF.getText()) != null) {
                ArrayList<Node> nodes = new ArrayList<Node>();
                nodes.add(panel.getNode(salesmanStartTF.getText()));
                travelling(panel.getNode(salesmanStartTF.getText()), nodes, 0);
                if (costs.size() > 0) {
                    int smallest = costs.get(0);
                    int index = 0;
                    for (int i = 0; i < costs.size(); i++) {
                        if (costs.get(i) < smallest) {
                            smallest = costs.get(i);
                            index = i;
                        }
                    }
                    for (int i = 0; i < completed.get(index).size(); i++) {
                        System.out.println(completed.get(index).get(i).getLabel());
                    }
                    System.out.println("Least cost path: " + smallest);
                }
                completed = new ArrayList<ArrayList<Node>>();
                costs = new ArrayList<Integer>();

            } else {
                JOptionPane.showMessageDialog(frame, "Not a valid starting node.");
            }
        }
    }

    public void travelling(Node n, ArrayList<Node> path, int total) {
        if (panel.getNodeList().size() == path.size()) { // Base case
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int i = 0; i < path.size(); i++) {
                temp.add(path.get(i));
            }
            completed.add(temp);
            path.remove(path.size() - 1);
            costs.add(total); // Add total of the pathways to a mirroring array list.
        } else {

            for (int i = 0; i < panel.getEdgeList().size(); i++) {
                Edge e = panel.getEdgeList().get(i);
                if (e.getOtherEnd(n) != null) {
                    if (!path.contains(e.getOtherEnd(n))) {
                        path.add(e.getOtherEnd(n));
                        travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel()));
                    }
                }
            }
            if (panel.getEdgeList().size() == 0) {
                path.add(n);
                travelling(n, path, 0);
            } else {
                path.remove(path.size() - 1);
            }

        }

    }

}

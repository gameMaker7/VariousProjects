package recursion;

import java.util.ArrayList;
import java.util.Scanner;

public class Node {

	private static int n;
	private static int found;
	private Node left = null;
	private Node right = null;
	private int value = 0;
	private int key = 0;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(new Node(null, null, 11, 25));
		Node node2 = new Node(null, null, 14, 24);
		Node node3 = new Node(null, null, 4, 7);
		Node node4 = new Node(node1, node2, 12, 76);
		Node node5 = new Node(node3, node4, 10, 42);
		printTree(node5);
//		while(true){
//			System.out.println("Enter Key");
//			n = input.nextInt();
//			if(n == 0){
//				break;
//			}
//			found = find(n, node5);
//			if(found != -1){
//				System.out.println("Value " + found);
//			}else{
//				System.out.println("Invalid Command");
//			}
//		}

	}
	private static void printTree(Node node) {
		if(node != null){	
			printTree((node.getLeft()));	
			printTree((node.getRight()));	
			System.out.println(node.getKey());

		}
		
		
	}
	private static int find(int n2, Node node) {
		if(node == null)return -1;
		if(n2 == node.getKey()){
			return node.getValue();
		}else if(n2<node.getKey()){
			return find(n2, node.getLeft());
		}
		return find(n2, node.getRight());
	}
	public Node(Node left, Node right, int key, int value){
		this.left = left;
		this.right = right;
		this.value = value;
		this.key = key;
	}
	public Node(Node node, int nextInt, int nextInt2) {
		position(node, nextInt);
		
	}
	private void position(Node node, int nextInt) {

		if(node.getKey() == nextInt){
			System.out.println("exsists");
		}
		if(node.getKey() < nextInt){
			if(node.getLeft() == null){
				Node add = new Node(null, null, nextInt, Gen.gen.nextInt());
			}
			position(node.getLeft(), nextInt);
		}
		if(node.getKey() > nextInt){
			position(node.getRight(), nextInt);
			Node add = new Node(null, null, nextInt, Gen.gen.nextInt());
		}
		
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}



}

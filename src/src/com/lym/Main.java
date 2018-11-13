package src.com.lym;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Thread{

	//public static Node[] nodes;
	public static ArrayList<Node> nodes;
	public static Thread[] nodeThread = new Thread[100];
	public static int startingPort;
	
	public static void main(String[] args) throws Exception {

		int numNodes = 6;
		startingPort = 200;
		nodes = new ArrayList<Node>();
		Scanner scanner = new Scanner(System.in);
		
		/* initialise each node and assign it a unique port */
		for(int i = 0; i < numNodes; i++){
			nodes.add(new Node(i, startingPort + i,numNodes));
		}
		
		/* create the window */
		Canvas canvas = new Canvas();
		canvas.setVisible(true);
		
		/* redraw the window every 1/60th of a second */
		(new Main()).start();
		
		for(int i = 0; i < numNodes; i++){
			nodeThread[i] = new Thread(nodes.get(i));
			nodeThread[i].start();
		}
		
		nodes.get(0).hasToken = true;
		
		String input = "";
		
		do{
			System.out.println("Enter the information in the following format:");
			System.out.println("1. \"num,num\" \t(first num means send node,second num means destination node.)");
			System.out.println("2. \"add\" \t\t(add one node)");
			System.out.println("3. \"delete\" \t(delete one node)");
			System.out.println("4. \"-1\" \t\t(exit)");
			input = scanner.nextLine();
			
			int[] values = checkFormat(input);
			
			if(values != null){
				
				nodes.get(values[0]).toSend = true;
				nodes.get(values[0]).destNode = values[1];
				
			} else if(input.equals("add")){
				numNodes++;
				nodes.add(new Node(numNodes-1, startingPort + numNodes-1, numNodes));
				nodeThread[numNodes - 1] = new Thread(nodes.get(nodes.size()-1));
				nodeThread[numNodes - 1].start();
				nodes.get(nodes.size()-2).changePort();

			} else if(input.equals("delete")){
				nodeThread[numNodes - 1].interrupt();
				nodeThread[numNodes - 1] = null;
				nodes.get(nodes.size()-1).socket.close();
				nodes.remove(nodes.size()-1);
				nodes.get(nodes.size()-1).nextPort = nodes.get(0).port;
				numNodes--;
			} else if(!input.equals("-1")){
				System.out.println("Error in parsing input");
			}
			
		}while(!input.equals("-1"));
		
		scanner.close();
		System.exit(1);
		
	}
	
	public static int[] checkFormat(String input){
		
		int[] parsedValues = new int[2];
		
		if(!input.contains(",")){
			return null;
		}
		
		String[] splits = input.split(",");
		
		if(splits.length != 2){
			return null;
		}
		
		try{
			parsedValues[0] = Integer.parseInt(splits[0]);
			parsedValues[1] = Integer.parseInt(splits[1]);
		} catch(Exception e){
			System.out.println("Error parsing input");
		}
		
		return parsedValues;
		
	}
	
	public void run(){
		
		while(true){
			
			Canvas.window.repaint();
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				System.out.println("Crashed when trying to wake the repaint thread");
			}
		}
	}
}

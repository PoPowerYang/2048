import java.util.Scanner;

public class Game2048{
	//main method
	public static void main(String[] args) {
		Game2048 g = new Game2048();
		g.start();
	}
	
	
	
	//a matrix that stores numbers
	protected int [][] matrix  = new int[4][4];
	
	//protected int [][] matrix = {{2,128,8,4},{4,1024,4,64},{8,8,8,2},{4,2,2,4}};
	protected String win = "";
	protected int validOperations = 0;
	protected int nOfMove = 0;
	
	public void start() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		String userChoice = "";
		String choice = "";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Operations o = new Operations();
		System.out.println("Welcome to 2048! Enter q for quit, r for restart, otherwise, use w, s, a, d to move! ");
		o.pop_number(); o.pop_number(); //generate two random numbers
		o.print2DArray();
		System.out.println("The number of operations: "+nOfMove);
		o.findMax();
		while (!userChoice.equals("q")) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("\n");
			System.out.print("Enter your move, or enter q for quit, r for restart: ");
			userChoice = sc.next();
			if(!userChoice.equals("q")) {
				if(userChoice.equals("w")) {  //up direction
					o.pushUp();
					nOfMove++;
					choice = "w";
				} else if (userChoice.equals("s")) { //down direction
					o.pushDown();
					nOfMove++;
					choice = "s";
				} else if (userChoice.equals("d")) { //right direction
					o.pushRight();
					nOfMove++;
					choice = "d";
				} else if (userChoice.equals("a")) { //left direction
					o.pushLeft();
					nOfMove++;
					choice = "a";
				} else if (userChoice.equals("r")) { //reset option
					o.reset();
					validOperations = 0;
					nOfMove = 0;
					choice = "r";
				} else if (userChoice.equals("q")) {
					choice = "q";
				}
				if(o.canMove) {
					if(!userChoice.equals("r")) {
						o.pop_number();
						validOperations ++; 
					} //add the valid operation every time the element moved
				} else if (o.unMovable() && o.isEmpty()) {
					userChoice = "q";
				}
				if (o.findMax() == 2048) {
					System.out.println("Activated");
					win = "win";
					userChoice = "q";
				}
				o.print2DArray();
				System.out.println("The direction moved: " + choice);
				System.out.println("The valid operation: "+validOperations + " " + o.canMove);
				System.out.println("The number of operations: "+nOfMove);
				System.out.println("The max number = " + o.findMax());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
		}
		if(userChoice.equals("q")) {
			if(o.unMovable()) {
				System.out.println("\n\n\n\n\n\n\nGame Over! You Lose!");
			} else if (win.equals("win")) {
				System.out.println("\n\n\n\n\n\n\nGame Over! You Win!");
			} else {
				System.out.println("\n\n\n\n\n\n\nGame Over");
			}
			System.out.println("Thank you for playing!");
			System.out.println("The number of operations: "+nOfMove);
			System.out.println("The valid operation: "+validOperations);
			System.out.println("The max number = " + o.findMax());
		} 
	}
}
	
	
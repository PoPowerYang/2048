import java.util.ArrayList;
import java.util.Random;

public class Operations {
	
	private Random r = new Random();
	protected boolean canMove = false;
	protected int [][] matrix = new Game2048().matrix;
	protected ArrayList<Integer> empty_row = new ArrayList<Integer>();
	protected ArrayList<Integer> empty_column = new ArrayList<Integer>();
	
	protected void print2DArray() {
		for(int[] x : matrix) {
			System.out.format("%6d%6d%6d%6d%n", x[0], x[1], x[2], x[3]); //a method to print a 2d array
		}
		System.out.format("%n");
	}
	
	protected boolean unMovable() {
		boolean unMovable = false;
		
		int [][] myInt = new int[matrix.length][];
		
		for(int i = 0; i < matrix.length; i++){ //make a copy of the array
		  int[] aMatrix = matrix[i];
		  int aLength = aMatrix.length;
		  myInt[i] = new int[aLength];
		  System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
		}
		
		boolean canMoveCopy = canMove;
		
		if(canMove == false) { //check in every direction
			pushUp(); 	
		}
		if(canMove == false) {
			pushDown();
		}
		if(canMove == false) {
			pushLeft();
		} 
		if(canMove == false) {
			pushRight();
		}
		
		if(canMove == false) {
			unMovable = true; //if still false that means that are no other move
		} else {
			unMovable = false; //otherwise, there is still a move
		}
		
		for(int i = 0; i < matrix.length; i++){ //restore the matrix
			 int[] aMatrix = myInt[i];
			 int aLength = aMatrix.length;
			 myInt[i] = new int[aLength];
			 System.arraycopy(aMatrix, 0, matrix[i], 0, aLength);
		}
		
		canMove = canMoveCopy; //restore the canMove value
		
		return unMovable;
	}
	
	protected void pop_number() {
		///*create two arraylist to store all coordinates where the value of that coordinate equals to 0, which means an empty spot
		empty_row.clear();
		empty_column.clear();
		for (int row = 0; row <4; row++) //check for where the empty spots are
			for(int column = 0; column < 4; column++ ) {
				if(matrix[row][column]==0) { //add them into the array list
					empty_row.add(new Integer(row));
					empty_column.add(new Integer(column));
				}
			}
		if(empty_row.size()>0) {
			int choice = r.nextInt(empty_row.size()); //give a random coordinate that is empty to put a number in
			int numberChooser = r.nextInt(9); //give a random number to determine next number to be added is 2 or 4
		
			int newNumber = 2; //80% of chance the number would be 2
			if(numberChooser == 0 || numberChooser == 1) {newNumber = 4;} //20% of chance the number would be 4
		
			int row = empty_row.get(choice); //get the empty coordinate
			int column = empty_column.get(choice); //get the empty coordinate
			
			matrix[row][column] = newNumber; //add the 2 or 4 to the empty coordinate in the array
			empty_row.remove(choice);
			empty_column.remove(choice);
		} 
	}
	
	protected boolean isEmpty() {
		boolean empty = false;
		if(empty_row.size()<=0) {
			empty = true;
		}
		return empty;
		
	}
	
	public int findMax() {
		int maxValue = 0;
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		        if (matrix[i][j] > maxValue) {
		           maxValue = matrix[i][j];
		        }
		    }
		}
		return maxValue;
	}
	
	public void reset() {
		for(int row = 0; row < 4; row ++) {
			for(int column = 0; column < 4; column ++) {
				matrix[row][column] = 0;
			}
		}
		
		pop_number(); pop_number();
	}
	
	public void pushUp() {
		canMove = false;
		//System.out.println("Push up");
		for (int column = 0; column < 4; column++) {
			boolean[] alreadyCombined = {false, false, false, false}; //determine where the corresponding 
																	//element in the array has already been combined in this round
			for(int row = 1; row < 4; row ++) {
				if (matrix [row][column] != 0) {
					int value = matrix[row][column];//stores the value first
					int row_copy = row-1;
					
					while((row_copy>=0) && (matrix[row_copy][column] == 0) ) { //check from bottom to the top
						row_copy --;
					}
					
					if(row_copy==-1) { //if haven't combined and haven't been blocked, place it in the first row.
						if(row == row_copy+1 && canMove != true) { canMove = false; } 
						else { canMove = true; }
						matrix[row][column] = 0;
						matrix[0][column] = value;
					} else if (matrix[row_copy][column] != value) {
						if(row == row_copy+1 && canMove != true) { canMove = false; } 
						else { canMove = true; }
						matrix[row][column] = 0;
						matrix[row_copy+1][column] = value;
					} else {
						if(alreadyCombined[row_copy]) { //if the number have been combined in this round, then will not combine again in this round
							if(row == row_copy+1 && canMove != true) { canMove = false; } 
							else { canMove = true; }
							matrix[row][column] = 0;
							matrix[row_copy+1][column] = value;
						} else if (value == matrix[row_copy][column]) { //otherwise combine them
							canMove = true;
							matrix[row][column] = 0;
							matrix[row_copy][column] *= 2;
							alreadyCombined[row_copy] = true;
						}
					}
				}
			}
		}
	}	

	
public void pushDown(){
	canMove = false;
	//System.out.println("Push down");
	//determine where the corresponding element in the array has already been combined in this round
	//iterate over the matrix to check for nonzero number
	for (int column = 0; column < 4; column++) {
		boolean[] alreadyCombined1 = {false, false, false, false};
		for(int row = 2; row > -1; row --) {
			if (matrix [row][column] != 0) {
				//stores the value first
				int value = matrix[row][column];
				int row_copy = row+1;
				//check from top to the bottom
				while((row_copy<=3) && (matrix[row_copy][column] == 0) ) {
					row_copy ++;
				}
				//if haven't combined and haven't been blocked, place it in the last row.
				if(row_copy==4) {
					matrix[row][column] = 0;
					matrix[3][column] = value;
					if(row == row_copy-1 && canMove != true) { canMove = false; } 
					else { canMove = true; }
				} else if (matrix[row_copy][column] != value) {
					if(row == row_copy-1 && canMove != true) { canMove = false; } 
					else { canMove = true; }
					matrix[row][column] = 0;
					matrix[row_copy-1][column] = value;
				} else {
					if(alreadyCombined1[row_copy]) {
						if(row == row_copy-1 && canMove != true) { canMove = false; } 
						else { canMove = true; }
						matrix[row][column] = 0;
						matrix[row_copy-1][column] = value;
					} else if (value == matrix[row_copy][column]) {
						canMove = true;
						matrix[row][column] = 0;
						matrix[row_copy][column] *= 2;
						alreadyCombined1[row_copy] = true;
					}
				}
			}
		}
	}
}
public void pushLeft() {
	canMove = false;
	//System.out.println("Push left");
	for (int row = 0; row < 4; row++) {
		boolean[] alreadyCombined2 = {false, false, false, false};
		for(int column = 1; column < 4; column ++) {
			if (matrix [row][column] != 0) {
				//stores the value first
				int value = matrix[row][column];
				//check from right to the left
				int column_copy = column-1;
				while((column_copy >= 0) && (matrix[row][column_copy] == 0)) {
					column_copy --;
				}
				//if haven't combined and haven't been blocked, place it in the first column.
				if(column_copy==-1) {
					if(column == column_copy+1 && canMove != true) {canMove = false;}
					else {canMove = true;}
					matrix[row][column] = 0;
					matrix[row][0] = value;
				} else if (matrix[row][column_copy] != value) {
					if(column == column_copy+1 && canMove != true) {canMove = false;}
					else {canMove = true;}
					matrix[row][column] = 0;
					matrix[row][column_copy+1] = value;
				} else {
					if(alreadyCombined2[column_copy]) {
						if(column == column_copy+1 && canMove != true) {canMove = false;}
						else {canMove = true;}
						matrix[row][column] = 0;
						matrix[row][column_copy+1] = value;
					} else if (value == matrix[row][column_copy]) { //otherwise combine
						canMove = true;
						matrix[row][column] = 0;
						matrix[row][column_copy] *= 2;
						alreadyCombined2[column_copy] = true;
					}
				}
			}
		}
	}
}
	public void pushRight() {
		canMove = false;
		//System.out.println("Push right");
		
		for (int row = 0; row < 4; row++) { //iterate over the matrix to check for nonzero number
			boolean[] alreadyCombined3 = {false, false, false, false};//determine where the corresponding element in the array has already been combined in this round
			for(int column = 2; column > -1; column --) {
				if (matrix [row][column] != 0) {
					//stores the value first
					int value = matrix[row][column];
					//check from right to the left
					int column_copy = column+1;
					
					while((column_copy <= 3) && (matrix[row][column_copy] == 0)) {
						column_copy ++;
					}
					if(column_copy==4) { // if haven't combined and haven't been blocked, place it in the last column.have not been blocked
						if(column == column_copy-1 && canMove != true) {canMove = false;} //means the element didn't move
						else {canMove = true;} 
						matrix[row][column] = 0; //set the original position to zero
						matrix[row][3] = value; //set the position for that the element
					} 
					else if (matrix[row][column_copy] != value) {
						if(column == column_copy-1 && canMove != true) {canMove = false;} 
						else {canMove = true;}
						matrix[row][column] = 0;
						matrix[row][column_copy-1] = value;
					} else {
						if(alreadyCombined3[column_copy]) {
							if(column == column_copy-1 && canMove != true) {canMove = false;} 
							else {canMove = true;}
							matrix[row][column] = 0;
							matrix[row][column_copy-1] = value;
						} else if (value == matrix[row][column_copy]) { //otherwise, combine
							canMove = true;
							matrix[row][column] = 0;
							matrix[row][column_copy] *= 2;
							alreadyCombined3[column_copy] = true;
						}
					}
				}
			}
		}
	}		
}

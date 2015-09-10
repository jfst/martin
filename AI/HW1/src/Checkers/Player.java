package Checkers;

import java.util.*;

public class Player {
    /**
     * Performs a move
     *
     * @param pState
     *            the current state of the board
     * @param pDue
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState pState, final Deadline pDue) {

        Vector<GameState> lNextStates = new Vector<GameState>();
        pState.findPossibleMoves(lNextStates);

        if (lNextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(pState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        
        if (pState.isBOG()){
        	/**
        	 * In the beginning of the game it doesn't matter which
        	 * piece we move so therefore we can move any of the pieces
        	 * in the front row
        	 */
            Random random = new Random();
            return lNextStates.elementAt(random.nextInt(lNextStates.size()));
        }
        /**
         * Here we want to make the best move. We want to eliminate
         * the opponents pieces by jumping over them. If we can do
         * multiple jumps in a single move then that is the best move
         * possible. Thus we check if any of the next states gives
         * us a chance to make another jump.
         */
        if(lNextStates.elementAt(0).getMove().isJump()){
/*
        	int[] prioList = new int[lNextStates.size()];
            for(int i = 0; i < lNextStates.size(); i++){
            	Vector<GameState> temp = new Vector<GameState>();            	
            	lNextStates.elementAt(i).findPossibleMoves(temp);
            	if(temp.elementAt(0).getMove().isJump())
            		return temp.elementAt(0);
            	}
*/			
        	int[] prioList = new int[lNextStates.size()];
        	for(int i = 0; i < lNextStates.size(); i++){
        		prioList[i] = lNextStates.elementAt(i).getMove().length();
        	}
//        	Arrays.sort(prioList);
        	/**
        	 * Finds the move with the largest amount of jumps
        	 */
        	int largestJump = prioList[0];
        	int index = 0;
        	for(int i = 0; i < prioList.length; i++){
        		if(prioList[i] > largestJump){
        			largestJump = prioList[i];
        			index = i;
        		}
        	}
        	return lNextStates.elementAt(index);
        }
        	
        
        /**
         * If there is no jump to make we want to make a move that 
         * does not put our piece in danger, i.e. a move that gives 
         * the opponent the chance to jump over the piece the next round
         */
    	assert(lNextStates.elementAt(0).getMove().isNormal());
    	GameState thisState;
    	int row, col, cellNum, moveLength;
    	/**
    	 * If the next states are empty we know that someone has won
    	 */
    	if (lNextStates.isEmpty()){
    		if (pState.isRedWin()){
    			System.out.print("Red wins");
    			return pState;
    		}
    		else if (pState.isWhiteWin()){
    			System.out.print("White wins");
    			return pState;
    		}
    		else{
    			return null;
    		}
    	}
    	for (int i = 0; i < lNextStates.size(); i++){
    		thisState = lNextStates.elementAt(i);
    		moveLength = thisState.getMove().length();
    		cellNum = thisState.getMove().at(moveLength-1);	//Gets the position where the piece ended up
    		row = GameState.cellToRow(cellNum);
    		col = GameState.cellToCol(cellNum);
    		if (thisState.get(row+1, col-1) != Constants.CELL_INVALID &&
    			thisState.get(row+1, col-1) == Constants.CELL_RED){
    			//If the cell contains the opponents color then we have to
    			//check the opposite position
    			if (thisState.get(row-1, col+1) == Constants.CELL_EMPTY ||
    				thisState.get(row-1, col+1) == Constants.CELL_WHITE){
    				//Here we have to check if there is any of the 
    				//opponents pieces to the right
    				if (thisState.get(row+1, col+1) != Constants.CELL_INVALID &&
    					thisState.get(row+1, col+1) == Constants.CELL_RED){
    					//Then we do the same thing as before for this side
    		 			if (thisState.get(row-1, col-1) == Constants.CELL_EMPTY ||
    		    			thisState.get(row-1, col-1) == Constants.CELL_WHITE){
    		 				//Here we know for sure that there is no way for
    		 				//the opponent to kill the piece we just moved
    		 				//So for that reason we can execute this move
    		 				
    		 				return thisState;
    		 			}
    		    					
    				}
    			}
    		}
    	}
//        pState.getMove()
    	/**
    	 * If there is no jumping move available or any move that
    	 * wont put the piece in danger, then the only thing to do 
    	 * is to choose between the available moves at random
    	 */
        Random random = new Random();
        return lNextStates.elementAt(random.nextInt(lNextStates.size()));

    }
}

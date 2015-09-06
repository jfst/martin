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
        
/*        if (pState.isBOG()){
        	*//**
        	 * In the beginning of the game it doesn't matter which
        	 * piece we move so therefore we can move any of the pieces
        	 * in the front row
        	 *//*
            Random random = new Random();
            return lNextStates.elementAt(random.nextInt(lNextStates.size()));
        }
*/        /**
         * If we are able to do a jump then we must do it
         */
//        for (int i = 0; i < lNextStates.size(); i++){
//        	lNextStates.elementAt(i).;
        	
//        }
        
        Random random = new Random();
        return lNextStates.elementAt(random.nextInt(lNextStates.size()));

    }
}

package edu.miami.c08159659.tictactoc;

public class TicTacToe {
	    private Player[][] ticTacToe = new Player[3][3];
	    private int emptySpaces = 9;
	    
	    public enum Player {
	    	A, B
	    }

	    private Player winner = null;

	    public Player getWinner()
	    {
	        return this.winner;
	    }

	    public boolean isSolved()
	    {
	        this.checkSolved();
	        return this.winner != null || emptySpaces == 0;
	    }
	    
	    public void setPlay(int row, int col, Player player) {
	    	if (row < 3 && col < 3) {
	    		ticTacToe[row][col] = player;
	    		emptySpaces--;
	    	}
	    }

	    private void checkSolved()
	    {
	        for(int i = 0; i < ticTacToe.length; i++)
	        {
	            Player win = checkRow(i);
	            if(win != null || (win = checkColumn(i)) != null)
	            {
	                this.winner = win;
	                return;
	            }
	        }
	        //Check diagonal top left to bottom right
	        if(this.ticTacToe[0][0] != null)
	        {
	            if(this.ticTacToe[0][0].equals(this.ticTacToe[1][1]) &&
	               this.ticTacToe[1][1].equals(this.ticTacToe[2][2]))
	            {
	                this.winner = this.ticTacToe[0][0];
	                return;
	            }
	        }
	        //Check diagonal top right to bottom left
	        if(this.ticTacToe[0][2] != null)
	        {
	            if(this.ticTacToe[0][2].equals(this.ticTacToe[1][1]) &&
	               this.ticTacToe[1][1].equals(this.ticTacToe[2][0]))
	            {
	                this.winner = this.ticTacToe[0][2];
	                return;
	            }
	        }
	    }

	    private Player checkRow(int row)
	    {
	        if(this.ticTacToe[row][0] == null)
	        {
	            return null;
	        }
	        if(this.ticTacToe[row][0].equals(this.ticTacToe[row][1]) &&
	           this.ticTacToe[row][1].equals(this.ticTacToe[row][2]))
	        {
	            return this.ticTacToe[row][0];
	        }
	        return null;
	    }

	    private Player checkColumn(int column)
	    {
	        if(this.ticTacToe[0][column] == null)
	        {
	            return null;
	        }
	        if(this.ticTacToe[0][column].equals(this.ticTacToe[1][column]) &&
	           this.ticTacToe[1][column].equals(this.ticTacToe[2][column]))
	        {
	            return this.ticTacToe[0][column];
	        }
	        return null;
	    }
}

package com.example.thierryjudge.tictactoe;

public class GameController
{


    char player = 'X';
    char computer = 'O';
    public class Move
    {
        public int row;
        public int col;

        Move(int x, int y)
        {
            this.row = x;
            this.col = y;
        }
        Move(){}

        @Override
        public String toString() {
            return "Move = Row: " + row + ", Column: " + col;
        }
    }

    public int checkWin(char[][] localGrid)
    {
        for(int i = 0; i < 3; i++)
        {
            if(localGrid[i][0] == localGrid[i][1] && localGrid[i][0] == localGrid[i][2])
            {
                if(localGrid[i][0] == player)
                {
                    return 1;
                }
                else if(localGrid[i][0] == computer)
                {
                    return 2;
                }
            }
        }

        for(int j = 0; j < 3; j++)
        {
            if(localGrid[0][j] == localGrid[1][j] && localGrid[0][j] == localGrid[2][j])
            {
                if(localGrid[0][j] == player)
                {
                    return 1;
                }
                else if(localGrid[0][j] == computer)
                {
                    return 2;
                }
            }
        }

        if(localGrid[0][0] == localGrid[1][1] && localGrid[0][0] == localGrid[2][2])
        {
            if(localGrid[0][0] == player)
            {
                return 1;
            }
            else if(localGrid[0][0] == computer)
            {
                return 2;
            }
        }
        if(localGrid[0][2] == localGrid[1][1] && localGrid[1][1] == localGrid[2][0])
        {
            if(localGrid[1][1] == player)
            {
                return 1;
            }
            else if(localGrid[1][1] == computer)
            {
                return 2;
            }
        }

        int count = 0;
        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(localGrid[i][j] == 'X' || localGrid[i][j] == 'O' || localGrid[i][j] == 'D' )
                {
                    count++;
                }
            }
        }
        if(count == 9)
        {
            return 3;
        }


        return 0;
    }

    int miniMax(char[][] grid, int depth, boolean compTurn)
    {
        int score = checkWin(grid); // for the maxed player
        if(score == 1) {return 10;} //player win
        else if(score == 2) {return -10;} // computer win
        else if(score == 3) {return 0;} // draw
        else
        {
            if(compTurn)  // maximised player
            {
                int best = -1000;
                for (int i = 0; i<3; i++)
                {
                    for (int j = 0; j<3; j++)
                    {
                        // Check if cell is empty
                        if (grid[i][j] != player || grid[i][j] != computer)
                        {
                            // Make the move
                            grid[i][j] = computer;

                            // Call minimax recursively and choose the maximum value
                            best = Math.max(best, miniMax(grid, depth+1, !compTurn));

                            // Undo the move
                            grid[i][j] = '_';
                        }
                    }
                }
                return best;
            }
            // If this minimizer's move
            else
            {
                int best = 1000;

                // Traverse all cells
                for (int i = 0; i<3; i++)
                {
                    for (int j = 0; j<3; j++)
                    {
                        // Check if cell is empty
                        if (grid[i][j] != player || grid[i][j] != computer)
                        {
                            // Make the move
                            grid[i][j] = player;

                            // Call minimax recursively and choose
                            // the minimum value between
                            best = Math.min(best,miniMax(grid, depth+1, !compTurn));

                            // Undo the move
                            grid[i][j] = '_';
                        }
                    }
                }
                return best;
            }
        }
    }

    // This will return the best possible move for the player
    Move findBestMove(char[][] grid)
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evalutae minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                // Check if celll is empty
                if (grid[i][j] != player || grid[i][j] != computer)
                {
                    // Make the move
                    grid[i][j] = computer;

                    // compute evaluation function for this
                    // move.
                    int moveVal = miniMax(grid, 0, false);

                    // Undo the move
                    grid[i][j] = '_';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }



        return bestMove;
    }



}

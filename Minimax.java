package TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class Minimax {
    private boolean gameOver(int[] position) {
        return child(position).size() == 0;
    }

    private boolean check(int player, int[] position) {
        if(position[0] == player && position[0] == position[1] && position[1] == position[2]) return true;
        else if(position[3] == player && position[3] == position[4] && position[4] == position[5]) return true;
        else if(position[6] == player && position[6] == position[7] && position[7] == position[8]) return true;
        else if(position[0] == player && position[0] == position[3] && position[3] == position[6]) return true;
        else if(position[1] == player && position[1] == position[4] && position[4] == position[7]) return true;
        else if(position[2] == player && position[2] == position[5] && position[5] == position[8]) return true;
        else if(position[0] == player && position[0] == position[4] && position[4] == position[8]) return true;
        else if(position[2] == player && position[2] == position[4] && position[4] == position[6]) return true;
        return false;
    }

    private int heuristic(int[] position) {
        if(check(2, position)) return 10;
        else if(check(1, position)) return -10;

        int count = 0, player = 2;
        boolean ver_1 = true, ver_2 = true, ver_3 = true,
        hor_1 = true, hor_2 = true, hor_3 = true,
        cross_1 = true, cross_2 = true;

        if(position[0] == 3 - player) {
            ver_1 = false; cross_1 = false; hor_1 = false;
        }
        if(position[1] == 3 - player) {
            ver_1 = false; hor_2 = false;
        }
        if(position[2] == 3 - player) {
            ver_1 = false; cross_2 = false; hor_3 = false;
        }
        if(position[3] == 3 - player) {
            ver_2 = false; hor_1 = false;
        }
        if(position[4] == 3 - player) {
            ver_2 = false; hor_2 = false; cross_1 = false; cross_2 = false;
        } 
        if(position[5] == 3 - player) {
            ver_2 = false; hor_3 = false;
        }
        if(position[6] == 3 - player) {
            ver_3 = false; hor_1 = false; cross_2 = false;
        }
        if(position[7] == 3 - player) {
            ver_3 = false; hor_2 = false;
        }
        if(position[8] == 3 - player) {
            ver_3 = false; hor_3 = false; cross_1 = false;
        }
        if(ver_1) count++;
        if(ver_2) count++;
        if(ver_3) count++;
        if(hor_1) count++;
        if(hor_2) count++;
        if(hor_3) count++;
        if(cross_1) count++;
        if(cross_2) count++;
        return count;
    }

    private List<Integer> possibleMoves(int[] position) {
        List<Integer> moves = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            if(position[i] == 0) {
                moves.add(i);
            }
        }
        return moves;
    }

    private List<int[]> child(int[] position) {
        List<int[]> child = new ArrayList<>();
        int[] current_state = new int[9];
        for(int i = 0; i < 9; i++) current_state[i] = position[i];
        for(int i = 0; i < 9; i++) {
            if(current_state[i] == 0) {
                current_state[i] = 2;
                child.add(current_state);
                current_state[i] = 0;
            }
        }
        return child;
    }

    private int minimax(int[] position, int depth, boolean player1Turn) {
        if(depth == 0 || gameOver(position))
            return heuristic(position);

        if(player1Turn) {
            int maxValue = -1000;
            int currValue = 0;
            for(int i = 0; i < child(position).size(); i++) {
                currValue = minimax(child(position).get(i), depth-1, false);
                maxValue = Math.max(maxValue, currValue);
            }
            return maxValue;
        }
        else {
            int minValue = 1000;
            int currValue = 0;
            for(int i = 0; i < child(position).size(); i++) {
                currValue = minimax(child(position).get(i), depth-1, true);
                minValue = Math.min(minValue, currValue);
            }
            return minValue;
        }
    }

    public int best_solution(int[] position) {
        int location = 0;
        int value = -1000;
        
        // for(int i = 0; i < child(position).size(); i++) {
        //     System.out.print(addPossibleMoves(position).get(i));
        // }
        // System.out.println("\nBest solution");
        for(int i = 0; i < child(position).size(); i++) {
            System.out.print(heuristic(child(position).get(i)));
            if(minimax(child(position).get(i), 0, false) >= value) {
                    value = minimax(child(position).get(i), 0, false);
                    location = possibleMoves(position).get(i);
                    
            }
        }
        return location;
    }
}

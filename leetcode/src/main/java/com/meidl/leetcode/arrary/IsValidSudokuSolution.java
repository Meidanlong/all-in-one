package com.meidl.leetcode.arrary;


import java.util.ArrayList;

/**
 * 有效的数独
 * <p>
 * <p>
 * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
 * <p>
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * ...
 * 上图是一个部分填充的有效的数独。
 * <p>
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * [
 * ["5","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出: true
 * <p>
 * 示例 2:
 * <p>
 * 输入:
 * [
 * ["8","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出: false
 * 解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
 * 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
 * <p>
 * 说明:
 * <p>
 * 一个有效的数独（部分已被填充）不一定是可解的。
 * 只需要根据以上规则，验证已经填入的数字是否有效即可。
 * 给定数独序列只包含数字 1-9 和字符 '.' 。
 * 给定数独永远是 9x9 形式的。
 */
public class IsValidSudokuSolution {

    public boolean isValidSudoku(char[][] board) {
        ArrayList set00 = new ArrayList();
        ArrayList set01 = new ArrayList();
        ArrayList set02 = new ArrayList();
        ArrayList set10 = new ArrayList();
        ArrayList set11 = new ArrayList();
        ArrayList set12 = new ArrayList();
        ArrayList set20 = new ArrayList();
        ArrayList set21 = new ArrayList();
        ArrayList set22 = new ArrayList();


        if (!(board.length == 9 && board[0].length == 9)) {
            //System.out.println("board.length != 9 || board[0].length != 9");
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            //每次遍历完一列（行）后，重新new一个存放列（行）的集合
            ArrayList line = new ArrayList();
            ArrayList row = new ArrayList();
            for (int j = 0; j < board[0].length; j++) {

                //判断列ij和行ji两个字符是否符合要求
                if (!('.' == board[i][j] || ('0' <= board[i][j] && '9' >= board[i][j]))) {
                    //System.out.println("======1======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                    return false;
                }
                if (!('.' == board[j][i] || ('0' <= board[j][i] && '9' >= board[j][i]))) {
                    //System.out.println("======2======the char is "+board[j][i]+" with i = "+i+", j = "+j);
                    return false;
                }

                //列
                if ('0' <= board[i][j] && '9' >= board[i][j]) {
                    if (row.contains(board[i][j])) {
                        //System.out.println("======3======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                        return false;
                    } else {
                        row.add(board[i][j]);
                    }
                }

                //行
                if ('0' <= board[j][i] && '9' >= board[j][i]) {
                    if (line.contains(board[j][i])) {
                        //System.out.println("======4======the char is "+board[j][i]+" with i = "+i+", j = "+j);
                        return false;
                    } else {
                        line.add(board[j][i]);
                    }
                }

                //表中九块区域的判断
                if ('0' <= board[i][j] && '9' >= board[i][j]) {
                    //00
                    if (i / 3 == 0 && j / 3 == 0) {
                        if (set00 != null && set00.size() != 0 && set00.contains(board[i][j])) {
                            //System.out.println("======5======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set00.add(board[i][j]);
                        }
                    }
                    //01
                    if (i / 3 == 0 && j / 3 == 1) {
                        if (set01 != null && set01.size() != 0 && set01.contains(board[i][j])) {
                            //System.out.println("======6======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set01.add(board[i][j]);
                        }
                    }
                    //02
                    if (i / 3 == 0 && j / 3 == 2) {
                        if (set02 != null && set02.size() != 0 && set02.contains(board[i][j])) {
                            //System.out.println("======7======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set02.add(board[i][j]);
                        }
                    }
                    //10
                    if (i / 3 == 1 && j / 3 == 0) {
                        if (set10 != null && set10.size() != 0 && set10.contains(board[i][j])) {
                            //System.out.println("======8======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set10.add(board[i][j]);
                        }
                    }
                    //11
                    if (i / 3 == 1 && j / 3 == 1) {
                        if (set11 != null && set11.size() != 0 && set11.contains(board[i][j])) {
                            //System.out.println("======9======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set11.add(board[i][j]);
                        }
                    }
                    //12
                    if (i / 3 == 1 && j / 3 == 2) {
                        if (set12 != null && set12.size() != 0 && set12.contains(board[i][j])) {
                            //System.out.println("======10======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set12.add(board[i][j]);
                        }
                    }
                    //20
                    if (i / 3 == 2 && j / 3 == 0) {
                        if (set20 != null && set20.size() != 0 && set20.contains(board[i][j])) {
                            //System.out.println("======11======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set20.add(board[i][j]);
                        }
                    }
                    //21
                    if (i / 3 == 2 && j / 3 == 1) {
                        if (set21 != null && set21.size() != 0 && set21.contains(board[i][j])) {
                            //System.out.println("======12======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set21.add(board[i][j]);
                        }
                    }
                    //22
                    if (i / 3 == 2 && j / 3 == 2) {
                        if (set22 != null && set22.size() != 0 && set22.contains(board[i][j])) {
                            //System.out.println("======13======the char is "+board[i][j]+" with i = "+i+", j = "+j);
                            return false;
                        } else {
                            set22.add(board[i][j]);
                        }
                    }

                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'8', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        IsValidSudokuSolution isValidSudokuSolution = new IsValidSudokuSolution();
        System.out.println(isValidSudokuSolution.isValidSudoku(board));

    }
}

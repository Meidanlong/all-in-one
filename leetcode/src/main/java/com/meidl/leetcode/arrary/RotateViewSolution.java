package com.meidl.leetcode.arrary;


import java.util.ArrayList;

/**
 * 旋转图像
 *
 *
 * 给定一个 n × n 的二维矩阵表示一个图像。
 *
 * 将图像顺时针旋转 90 度。
 *
 * 说明：
 *
 * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
 *
 * 示例 1:
 *
 * 给定 matrix =
 * [
 *   [1,2,3],
 *   [4,5,6],
 *   [7,8,9]
 * ],
 *
 * 原地旋转输入矩阵，使其变为:
 * [
 *   [7,4,1],
 *   [8,5,2],
 *   [9,6,3]
 * ]
 *
 * 示例 2:
 *
 * 给定 matrix =
 * [
 *   [ 5, 1, 9,11],
 *   [ 2, 4, 8,10],
 *   [13, 3, 6, 7],
 *   [15,14,12,16]
 * ],
 *
 * 原地旋转输入矩阵，使其变为:
 * [
 *   [15,13, 2, 5],
 *   [14, 3, 4, 1],
 *   [12, 6, 8, 9],
 *   [16, 7,10,11]
 * ]
 */
public class RotateViewSolution {

    public int[][] rotate(int[][] matrix) {

        int length = matrix.length;
        int index = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=1; i<length; i++){
            for(int j=i-1; j>=0;j--){
                list.add(matrix[j][i]);
            }
        }
        for(int i=0; i<length; i++){
            for(int j=i; j<length; j++){
                matrix[i][length-1-j]=matrix[j][i];
            }
            if(i>0){
                for(int k=length-1-i; k<length-1; k++){
                    matrix[i][k+1] = list.get(index++);
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int[][] arr = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        RotateViewSolution rotateViewSolution = new RotateViewSolution();
        arr = rotateViewSolution.rotate(arr) ;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        ;
    }
}

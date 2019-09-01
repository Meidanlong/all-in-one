package com.meidl.leetcode.linkedList;


/**
 * 合并两个有序链表
 *
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例：
 *
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class MergeTwoListsSolution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //特殊情况
        if(l1 == null && l2 == null){
            return null;
        }
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }

        //一般情况
        ListNode newHeadNode = new ListNode(0);//新链表的头结点
        //ListNode resultNode = null;//用于返回的头结点

        return mergeTwoListsByOneStep(newHeadNode,l1,l2);


    }

    public ListNode mergeTwoListsByOneStep(ListNode newHeadNode, ListNode l1, ListNode l2){
        //返回的应该归并后的头结点
        //递归到底的情况
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }

        //一般情况
        if(l1.val <= l2.val){
            newHeadNode = l1;
            l1 = l1.next;
        }else{
            newHeadNode = l2;
            l2 = l2.next;
        }
        newHeadNode.next = mergeTwoListsByOneStep(newHeadNode,l1,l2);

        return newHeadNode;

    }

    //非递归方法
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        //特殊情况
        if(l1 == null && l2 == null){
            return null;
        }
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }

        //一般情况
        ListNode newHeadNode = new ListNode(0);//新链表的头结点
        ListNode resultNode = null;//用于返回的头结点

        resultNode = newHeadNode;

        while(l1 != null || l2 != null){//l1l2都不为空
            //特殊情况
            if(l1 == null){
                newHeadNode.next = l2;
                break;
            }
            if(l2 == null){
                newHeadNode.next = l1;
                break;
            }

            //一般情况
            if(l1.val <= l2.val){
                newHeadNode.next = l1;
                l1 = l1.next;
            }else{
                newHeadNode.next = l2;
                l2 = l2.next;
            }
            newHeadNode = newHeadNode.next;
        }

        return resultNode.next;
    }

    public static void main(String[] args) {
        ListNode l11  = new ListNode(1);
        ListNode l12  = new ListNode(2);
        ListNode l13  = new ListNode(4);
        l11.next = l12;
        l12.next = l13;
        ListNode l21  = new ListNode(1);
        ListNode l22  = new ListNode(3);
        ListNode l23  = new ListNode(4);
        l21.next = l22;
        l22.next = l23;
        ListNode l31  = new ListNode(0);

        MergeTwoListsSolution mergeTwoListsSolution = new MergeTwoListsSolution();
        ListNode returnCurrentNode = mergeTwoListsSolution.mergeTwoLists(l11,l21);

        int i = 10;
        while(returnCurrentNode != null&&i>0){
            if(returnCurrentNode.next == null){
                System.out.print(returnCurrentNode.val);
            }else{
                System.out.print(returnCurrentNode.val);
                System.out.print("->");
            }

            returnCurrentNode = returnCurrentNode.next;

            i--;//防止内存溢出

        }
    }
}

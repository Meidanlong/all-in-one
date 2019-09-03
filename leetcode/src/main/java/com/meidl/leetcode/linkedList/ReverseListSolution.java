package com.meidl.leetcode.linkedList;


/**
 * 206 反转链表
 *
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: dummy<->1 ->2->3->4->5->NULL
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 *
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 */
public class ReverseListSolution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode pre = null;
        while (head != null) {
            ListNode newCurrent = head.next;
            head.next = pre;
            pre = head;
            head = newCurrent;
        }
        return pre;
    }

    public ListNode reverseList1(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = null;
        return reverseList(head,dummy);
    }

    public ListNode reverseList(ListNode currentNode,ListNode lastNode){
        //递归到底的情况
        if(currentNode.next == null){
            currentNode.next = lastNode;
            return currentNode;
        }

        //正常情况
        ListNode nextNode = currentNode.next;
        currentNode.next = lastNode;
        lastNode = currentNode;
        currentNode = nextNode;
        return reverseList(currentNode,lastNode);
    }

    public static void main(String[] args) {
        ListNode nextNode = null;
        ListNode head = null;
        for(int i=5; i>=1; i--){
            ListNode currentNode = new ListNode(i);
            currentNode.next = nextNode;
            nextNode = currentNode;
            if(i == 1){
                head = currentNode;
            }
        }

        ReverseListSolution reverseListSolution = new ReverseListSolution();
        ListNode returnCurrentNode = reverseListSolution.reverseList1(head);
//        ListNode returnCurrentNode = head;

        while(returnCurrentNode != null){
            if(returnCurrentNode.next == null){
                System.out.print(returnCurrentNode.val);
            }else{
                System.out.print(returnCurrentNode.val);
                System.out.print("->");
            }

            returnCurrentNode = returnCurrentNode.next;

        }
    }

}

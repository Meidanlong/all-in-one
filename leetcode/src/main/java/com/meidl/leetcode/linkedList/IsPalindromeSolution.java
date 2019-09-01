package com.meidl.leetcode.linkedList;

import java.util.Stack;

/**
 * 回文链表
 *
 * 请判断一个链表是否为回文链表。
 *
 * 示例 1:
 *
 * 输入: 1->2
 * 输出: false
 * 示例 2:
 *
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
public class IsPalindromeSolution {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public boolean isPalindrome(ListNode head) {
        //思路一：反转链表形成一个新的链表，再挨个比对链表的值，若相等则证明是回文链表
        //问题：链表无法复制一个新的head-->rehead，一旦head.next=null,则rehead.next=null
        ListNode dummyNode = null;
        ListNode reHead = head;

        while(reHead != null){
            ListNode behindNode = reHead.next;
            reHead.next = dummyNode;
            dummyNode = reHead;
            reHead = behindNode;
        }

        while(head != null){
            if(head.val != dummyNode.val){
                return false;
            }
            head = head.next;
            dummyNode = dummyNode.next;
        }

        return true;
    }

    public boolean isPalindrome2(ListNode head) {
        //思路二：将链表压入栈，用栈的性质替代的反转链表
        int count=0;
        Stack<Integer> stack=new Stack<>();
        ListNode n=head;
        while (n!=null){
            stack.push(n.val);
            n=n.next;
            count++;
        }
        for (int i = 1; i <=count/2 ; i++) {
            if (head.val!=stack.peek()){
                return false;
            }
            else {
                head=head.next;
                stack.pop();
            }
        }
        return true;
    }

    public boolean isPalindrome3(ListNode head) {
        //思路三：用快慢指针法找到链表的中点，快指针每次跳两个，慢指针每次跳一个，当快指针到头则慢指针刚好在链表中间
        // 要实现 O(n) 的时间复杂度和 O(1) 的空间复杂度，需要翻转后半部分
        if (head == null || head.next == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        // 根据快慢指针，找到链表的中点
        while(fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = reverse(slow.next);
        while(slow != null) {
            if (head.val != slow.val) {
                return false;
            }
            head = head.next;
            slow = slow.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head){
        // 递归到最后一个节点，返回新的新的头结点
        if (head.next == null) {
            return head;
        }
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }



    public static void main(String[] args) {
        ListNode nextNode = null;
        ListNode head = null;
        /*for(int i=5; i>=1; i--){
            ListNode currentNode = new ListNode(i);
            currentNode.next = nextNode;
            nextNode = currentNode;
            if(i == 1){
                head = currentNode;
            }
        }*/
        ListNode l11  = new ListNode(1);
        ListNode l12  = new ListNode(1);
        ListNode l13  = new ListNode(2);
        ListNode l14  = new ListNode(1);
        l11.next = l12;
        l12.next = l13;
        l13.next = l14;

        head = l11;
        ListNode basicNode = head;
        while(basicNode != null){
            if(basicNode.next == null){
                System.out.print(basicNode.val);
            }else{
                System.out.print(basicNode.val);
                System.out.print("->");
            }
            basicNode = basicNode.next;
        }


        IsPalindromeSolution isPalindromeSolution = new IsPalindromeSolution();
        isPalindromeSolution.isPalindrome(l11);
//        ListNode returnCurrentNode = isPalindromeSolution.isPalindrome(head);
//
//        System.out.println();
//
//        while(returnCurrentNode != null){
//            if(returnCurrentNode.next == null){
//                System.out.print(returnCurrentNode.val);
//            }else{
//                System.out.print(returnCurrentNode.val);
//                System.out.print("->");
//            }
//
//            returnCurrentNode = returnCurrentNode.next;
//
//        }
    }
}

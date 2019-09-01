package com.meidl.leetcode.linkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除链表的倒数第N个节点
 * <p>
 * <p>
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * <p>
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * <p>
 * 你能尝试使用一趟扫描实现吗？
 */
public class RemoveNthFromEndSulotion {


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode current = head;
        List<ListNode> list  = new ArrayList<ListNode>();
        while(current!=null){
            list.add(current);
            current = current.next;
        }

        int index = list.size()-n-1;//待删除前一个node
        if(index==-1) {//说明待删除的是第一个node
            return list.get(0).next;
        }
        list.get(index).next = list.get(index).next.next;

        return list.get(0);
    }

    public ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length  = 0;
        ListNode first = head;
        while (first != null) {
            length++;
            first = first.next;
        }
        length -= n;
        first = dummy;
        while (length > 0) {
            length--;
            first = first.next;
        }
        first.next = first.next.next;
        return dummy.next;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        // Move first to the end, maintaining the gap
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
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

        RemoveNthFromEndSulotion removeNthFromEndSulotion = new RemoveNthFromEndSulotion();
        ListNode returnCurrentNode = removeNthFromEndSulotion.removeNthFromEnd(head,1);

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

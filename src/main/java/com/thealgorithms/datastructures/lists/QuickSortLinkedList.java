package com.thealgorithms.datastructures.lists;

/* 链表快速排序 */

public class QuickSortLinkedList {

    private final SinglyLinkedList list; // The linked list to be sorted
    private SinglyLinkedListNode head; // Head of the list

    /**
     * Constructor that initializes the QuickSortLinkedList with a given linked list.
     *
     * @param list The singly linked list to be sorted
     */
    public QuickSortLinkedList(SinglyLinkedList list) {
        this.list = list;
        this.head = list.getHead();
    }

    /**
     * Sorts the linked list using the QuickSort algorithm.
     * The sorted list replaces the original list within the SinglyLinkedList instance.
     */
    public void sortList() {
        head = sortList(head);
        list.setHead(head);
    }

    /**
     * Recursively sorts a linked list by partitioning it around a pivot element.
     *
     * <p>Each recursive call selects a pivot, partitions the list into elements less
     * than the pivot and elements greater than or equal to the pivot, then combines
     * the sorted sublists around the pivot.</p>
     *
     * @param head The head node of the list to sort
     * @return The head node of the sorted linked list
     */
    private SinglyLinkedListNode sortList(SinglyLinkedListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        SinglyLinkedListNode pivot = head;
        head = head.next;
        pivot.next = null;

        SinglyLinkedListNode lessHead = new SinglyLinkedListNode();
        SinglyLinkedListNode lessTail = lessHead;
        SinglyLinkedListNode greaterHead = new SinglyLinkedListNode();
        SinglyLinkedListNode greaterTail = greaterHead;

        while (head != null) {
            if (head.value < pivot.value) {
                lessTail.next = head;
                lessTail = lessTail.next;
            } else {
                greaterTail.next = head;
                greaterTail = greaterTail.next;
            }
            head = head.next;
        }

        lessTail.next = null;
        greaterTail.next = null;

        SinglyLinkedListNode sortedLess = sortList(lessHead.next);
        SinglyLinkedListNode sortedGreater = sortList(greaterHead.next);

        if (sortedLess == null) {
            pivot.next = sortedGreater;
            return pivot;
        } else {
            SinglyLinkedListNode current = sortedLess;
            while (current.next != null) {
                current = current.next;
            }
            current.next = pivot;
            pivot.next = sortedGreater;
            return sortedLess;
        }
    }
}

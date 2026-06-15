package com.thealgorithms.datastructures.lists;

/* 循环链表 */


/**
 * This class is a circular singly linked list implementation. In a circular linked list,
 * the last node points back to the first node, creating a circular chain.
 *
 * <p>This implementation includes basic operations such as appending elements
 * to the end, removing elements from a specified position, and converting
 * the list to a string representation.
 *
 * @param <E> the type of elements held in this list
 */
@SuppressWarnings("rawtypes")
public class CircleLinkedList<E> {

    /**
     * A static nested class representing a node in the circular linked list.
     *
     * @param <E> the type of element stored in the node
     */

    // 创建一个node类,包含指向下一个节点的指针和当前值
    static final class Node<E> {

        Node<E> next;
        E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    Node<E> head = null;
    private Node<E> tail;

    /**
     * Initializes a new circular linked list. A dummy head node is used for simplicity,
     * pointing initially to itself to ensure the list is never empty.
     * 构造方法,初始化时创建一个node,其value置为null并且指向指针指向他自己
     */
    public CircleLinkedList() {
        head = new Node<>(null, head);
        tail = head;
        size = 0;
    }

    /**
     * Returns the current size of the list.
     *
     * @return the number of elements in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Appends a new element to the end of the list. Throws a NullPointerException if
     * a null value is provided.
     *
     * @param value the value to append to the list
     * @throws NullPointerException if the value is null
     */
    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        // 插入一个新的节点,拿到尾节点,判断是否有后缀.
        if (tail == null) {
            tail = new Node<>(value, head);
            head.next = tail;
        } else {
            tail.next = new Node<>(value, head);
            tail = tail.next;
        }
        size++;
    }

    /**
     * Returns a string representation of the list in the format "[ element1, element2, ... ]".
     * An empty list is represented as "[]".
     *
     * @return the string representation of the list
     */
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        // 使用sb,方便扩展存储链表中的信息
        StringBuilder sb = new StringBuilder("[ ");
        // 经典设计,头节点不存储具体节点
        Node<E> current = head.next;
        // 判断为空的情况就是尾节点的指针指向头节点说明回到开始节点了
        while (current != head) {
            sb.append(current.value);
            if (current.next != head) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Throws an IndexOutOfBoundsException if the position is invalid.
     *
     * @param pos the position of the element to remove
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the position is out of range
     */
    public E remove(int pos) {
        // 根据int的pos进行移除
        if (pos >= size || pos < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }
        // 从头节点开始向后遍历,直到找到待销毁的节点的前一个节点
        Node<E> before = head;
        for (int i = 1; i <= pos; i++) {
            before = before.next;
        }
        Node<E> destroy = before.next;
        E saved = destroy.value;
        before.next = destroy.next;

        if (destroy == tail) {
            tail = before;
        }
        destroy = null;
        size--;
        return saved;
    }
}

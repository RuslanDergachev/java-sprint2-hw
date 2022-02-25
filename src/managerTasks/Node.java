package managerTasks;

import tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class Node <T> {
    private T data;
    private Node<T> next;
    private Node<T> prev;


    public Node(T data) {
        this.data = data;

    }

    public T getData() {
        return data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }
}
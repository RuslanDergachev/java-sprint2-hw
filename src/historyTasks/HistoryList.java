package historyTasks;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryList<T extends Task> {

    private Node<T> head;
    private Node<T> tail;
    private Map<Integer, Node<T>> historyView = new HashMap<>();

    public void addLast(T element) {
        int id = element.getTaskId();
        remove(id);

        final Node<T> newNode = new Node<>(element);

        if (head == null) {
            head = newNode;//
            tail = newNode;
            historyView.put(id, newNode);
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            historyView.put(id, newNode);
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> allHistoryTasks = new ArrayList<>();
        Node<T> node = head;
        while (node != null) {

            Task nextNode = node.getData();
            allHistoryTasks.add(nextNode);
            node = node.getNext();
        }
        return allHistoryTasks;
    }

    public void remove(int id) {
        if (historyView.containsKey(id)) {
            removeNode(historyView.get(id));
            historyView.remove(id);
        }
    }

    public void removeNode(Node<T> node) {
        Node<T> prevNode = node.getPrev();
        Node<T> nextNode = node.getNext();
        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = prevNode;
        }
    }

    public void printHistory(List<Task> listAllHistory){
        for (Task history: listAllHistory){
            System.out.println(history);
        }
    }
}
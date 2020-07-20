import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class duplicateLinkedlistNode {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(1);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(3);
        linkedList.add(4);
        duplicateLinkedlistNode dln = new duplicateLinkedlistNode();
        LinkedList list = dln.removeDuplicateLinkedlist(linkedList);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(dln.removeDuplicateLinkedlist(linkedList).get(i));

        }
    }

    public LinkedList<Integer> removeDuplicateLinkedlist(LinkedList<Integer> list) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (!set.add(list.get(i))) {
                list.remove(i);
            }
        }
        return list;
    }
}

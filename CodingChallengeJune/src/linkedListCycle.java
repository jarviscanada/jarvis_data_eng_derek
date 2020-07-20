/*public class linkedListCycle {
    public boolean hasCycle(ListNode head){
       //two pointer
         if(head==null||head.next==null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        //or ListNode fast = head;
        while(first!=fast){
            if(fast==null||fast.next==null){
                return false;
            }
            first = first.next;
            fast = fast.next.next;
        }
        return true;

    }
}*/

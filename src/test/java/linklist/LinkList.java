package linklist;

import java.util.ArrayList;
import java.util.List;

public class LinkList {
    private Link first;

    /**
     * 空参构造方法
     */
    public LinkList() {
        first=null;
    }

    /**
     * 判断集合是否为空
     * @return
     */
    public boolean isEmpty(){
        return  first==null;
    }

    /**
     * 在集合的第一个位置插入数据
     * @param id
     * @param dd
     */
    public void insertFirst(int id,double dd){
       Link newLink = new Link(id,dd);
        newLink.next=first;
        first=newLink;
    }

    /**
     * 删除第一个数据
     * @return
     */
    public Link deleteFirst(){
        Link temp = first;
        first=first.next;
        return temp;
    }

    /**
     * 遍历集合
     */
    public void displayList(){
        System.out.print("List (first-->last): ");
        Link current=first;

        while (current != null){
            current.displayLink();
            current=current.next;
        }
        System.out.println("");
    }
}

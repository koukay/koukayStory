package linklist2;

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
//    public boolean isEmpty(){
//        return  first==null;
//    }

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
     * 根据key查询集合
     * @param key
     * @return
     */
    public  Link find(int key){
        //从第一个开始
        Link current= first;
        //如果没找到key
        while (current.iDate != key) {
            if (current.next==null) {
                return null;
            }else {
                //查询下一个key
                current=current.next;
            }
        }
        return current;
    }
    /**
     * 根据key删除数据
     * @return
     */
    public Link delete(int key){
        Link current = first;
        Link previous = first;
        while (current.next == null) {
            if (current.next==null){
                return null;
            }else {
                previous=current;
                current=current.next;
            }
        }
        if (current==first){
            first=first.next;
        }else {
            previous.next=current.next;
        }
        return current;
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

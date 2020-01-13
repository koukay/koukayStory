package linklist2;


/**
 * 主应用程序测试类
 */
public class LinkListApp {
    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.insertFirst(22,2.99);
        linkList.insertFirst(44,4.99);
        linkList.insertFirst(66,6.99);
        linkList.insertFirst(88,8.99);
        linkList.displayList();

        Link f = linkList.find(44);
        if (f !=null) {
            System.out.println("Found link with key "+f.iDate);
        }else {
            System.out.println("Can't find link");
        }
        Link d = linkList.delete(66);
        if (d != null) {
            System.out.println("Delete link with key "+d.iDate);
        }else {
            System.out.println("Can't delete link");
        }
        linkList.displayList();
    }
}

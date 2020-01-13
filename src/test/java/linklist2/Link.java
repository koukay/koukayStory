package linklist2;

/**
 * 声明链表类
 */
public class Link {
    public int iDate;    //data item (key)
    public double dDate; //data item
    public Link next;    //next link in list

    /**
     * 构造方法,初始化数据
     * @param iDate
     * @param dDate
     */
    public Link(int iDate, double dDate) {
        this.iDate = iDate;
        this.dDate = dDate;
    }

    /**
     * 展示数据
     */
    public void displayLink(){
        System.out.print("{"+iDate+", "+dDate+"}");
    }
}

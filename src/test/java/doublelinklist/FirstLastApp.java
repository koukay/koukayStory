package doublelinklist;

public class FirstLastApp {
    public static void main(String[] args) {
        FirstLastList theList = new FirstLastList();
        theList.insertFirst(22);
        theList.insertFirst(44);
        theList.insertFirst(66);
        theList.insertFirst(11);
        theList.insertFirst(33);
        theList.insertFirst(55);
        theList.displayList();
        theList.deleteFirst();
        theList.deleteFirst();
        theList.displayList();
    }
}

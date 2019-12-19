public class Person {
    private String firstName;
    private String lasttName;
    private int age;


    /**
     * 构造方法
     * @param firstName  名
     * @param lasttName  姓
     * @param age   年龄
     */
    public Person(String lasttName ,String firstName,int age){
        this.firstName=firstName;
        this.lasttName=lasttName;
        this.age=age;
    }

    /**
     * 打印用户信息
     */
    public void displayPerson(){
        System.out.print(" Last name: "+lasttName);
        System.out.print( " ,First name: "+firstName);
        System.out.println(",Age: "+age);
    }

    /**
     * 获取姓
     * @return
     */
    public String getLastName(){
        return lasttName;
    }

    static class ArrayInOb{
        //声明数组
        private Person[]a;
        //数组索引
        private int nElems;
        //构造方法,创建数组
        public ArrayInOb(int max){
            a=new Person[max];
            nElems=0;
        }

        //将用户插入数组
        public void insert(String last,String first,int age){
            a[nElems]=new Person(last,first,age);
            nElems++;
        }
        //遍历数组
        public void display(){
            for (int i = 0; i < nElems; i++) {
                a[i].displayPerson();
            }
            System.out.println("");
        }

        //插入排序
        public void insertionSort(){
            int in,out;
            for (out =1; out < nElems; out++) {
                Person temp =a[out];
                in=out;

                while (in>0&&a[in-1].getLastName().compareTo(temp.getLastName())>0){
                    a[in]=a[in-1];
                    --in;
                }
                a[in]=temp;
            }
        }
    }

    public static void main(String[] args) {
        int maxSize=100;
        ArrayInOb arr= new ArrayInOb(maxSize);

        //插入数据
        arr.insert("Koukay","baby",18);
        arr.insert("Houkay","baby",20);
        arr.insert("Doukay","baby",43);
        arr.insert("Foukay","baby",35);
        arr.insert("Ioukay","baby",54);
        arr.insert("Aoukay","baby",26);
        arr.insert("Coukay","baby",34);
        arr.insert("Roukay","baby",25);
        arr.insert("Voukay","baby",33);
        arr.insert("Boukay","baby",32);
        arr.insert("Noukay","baby",34);
        arr.insert("Joukay","baby",28);
        arr.insert("Zoukay","baby",43);
        arr.insert("Moukay","baby",56);
        arr.insert("Loukay","baby",32);
        arr.insert("Ooukay","baby",34);

        System.out.println("Before sorting: ");
        arr.display();
        arr.insertionSort();
        System.out.println("After sorting: ");
        arr.display();
    }
}

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试多线程
 */
public class TestMultiThread {
    /**
     * 测试入口函数
     *
     * @param argvArray 命令行参数数组
     */
    static public void main(String[] argvArray) {
        for (int i = 1; i <= 10000; i++) {
            System.out.println("第 " + i + " 次测试");
            (new TestMultiThread()).test4();
        }
    }
    /**
     * 第一个测试
     */
    public void test1(){
        TestUser newUser=new TestUser();
        newUser.currHp=100;

        Thread[] threadArray = new Thread[2];
        for (int i = 0; i <threadArray.length ; i++) {
            threadArray[i]=new Thread(()->{
                newUser.currHp=newUser.currHp-10;
            });
        }
        for (Thread thread : threadArray) {
            thread.start();
        }

        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (newUser.currHp!=80)throw new RuntimeException("当前血量错误,currHp= "+newUser.currHp);
        System.out.println("当前血量正确,currHp= "+newUser.currHp);

    }
    /**
     * 第二个测试
     */
    public void test2(){
        TestUser newUser=new TestUser();
        newUser.currHp=100;

        Thread[] threadArray = new Thread[2];
        for (int i = 0; i <threadArray.length ; i++) {
            threadArray[i]=new Thread(()->{
                newUser.subtractHp(10);
            });
        }
        for (Thread thread : threadArray) {
            thread.start();
        }

        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (newUser.currHp!=80)throw new RuntimeException("当前血量错误,currHp= "+newUser.currHp);
        System.out.println("当前血量正确,currHp= "+newUser.currHp);

    }
    /**
     * 第三个测试
     */
    public void test3(){
        TestUser user1=new TestUser();
        user1.currHp=100;
        TestUser user2=new TestUser();
        user2.currHp=100;

        Thread[] threadArray = new Thread[2];
        threadArray[0]=new Thread(()-> {
            user1.attkUser(user2);
        });
        threadArray[1]=new Thread(()-> {
            user2.attkUser(user1);
        });
        for (Thread thread : threadArray) {
            thread.start();
        }

        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("攻击完成");

    }
    /**
     * 第四个测试
     */
    public void test4(){
        TestUser newUser=new TestUser();
        newUser.safeCurrHp=new AtomicInteger(100);

        Thread[] threadArray = new Thread[2];
        for (int i = 0; i <threadArray.length ; i++) {
            threadArray[i]=new Thread(()->{
                newUser.safeCurrHp.addAndGet(-10);
            });
        }
        for (Thread thread : threadArray) {
            thread.start();
        }

        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (newUser.safeCurrHp.get()!=80)throw new RuntimeException("当前血量错误,currHp= "+newUser.safeCurrHp.get());
        System.out.println("当前血量正确,currHp= "+newUser.safeCurrHp.get());

    }
}

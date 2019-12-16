import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 测试阻塞队列
 */
public class TestBlockingQueue {
    /**
     * 测试函数主入口
     * @param args
     */
    public static void main(String[] args) {
        (new TestBlockingQueue()).test0();
//        (new TestBlockingQueue()).test1();
//        (new TestBlockingQueue()).test2();
    }


    /**
     * 测试阻塞队列
     */
    private void test0() {
        //阻塞队列
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>();
        //第一个线程往里面加数
        Thread T1= new Thread(()->{
            for (int i = 0; i < 10; i++) {
                //往队列里加数
                blockingQueue.offer(i);
            }
        });
        //第二个线程往里面加数
        Thread T2= new Thread(()->{
            for (int i = 10; i < 20; i++) {
                //往队列里加数
                blockingQueue.offer(i);
            }
        });
        //第二个线程从里面取数
        Thread T3= new Thread(()->{
            while (true){
                //从队列里取数并打印
                try {
                    Integer take = blockingQueue.take();
                    System.out.println("消费数字; "+take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        T1.start();
        T2.start();
        T3.start();

        //执行完之后一起退出
        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试阻塞队列
     */
    private void test1() {
        //阻塞队列
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>();
        //第一个线程往里面加数
        Thread T1= new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //往队列里加数
                blockingQueue.offer(i);
            }
        });
        //第二个线程往里面加数
        Thread T2= new Thread(()->{
            for (int i = 10; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //往队列里加数
                blockingQueue.offer(i);
            }
        });
        //第二个线程从里面取数
        Thread T3= new Thread(()->{
           while (true){
               //从队列里取数并打印
               try {
                   Integer take = blockingQueue.take();
                   System.out.println("消费数字; "+take);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });

        T1.start();
        T2.start();
        T3.start();

        //执行完之后一起退出
        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试非阻塞队列
     */
    private void test2() {
        /**
         * 阻塞队列, 阻塞队列中塞入的是 Runnable 接口
         */
        MyExecutorService myExecutorService = new MyExecutorService();
        //第一个线程往里面加数
        Thread T1= new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //往队列里加数
                final int currValue=i;
                myExecutorService.submit(()->{
                    System.out.println("i= " +currValue);
                });
            }
        });
        //第二个线程往里面加数
        Thread T2= new Thread(()->{
            for (int i = 10; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //往队列里加数
                final int currValue=i;
                myExecutorService.submit(()->{
                    System.out.println("i= " +currValue);
                });
            }
        });

        T1.start();
        T2.start();

        try {
            T1.join();
            T2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟 MyExecutorService
     */
    class MyExecutorService{
        /**
         * 阻塞队列,阻塞队列中放入runnable接口
         */
        private final BlockingQueue<Runnable> blockingQueue=new LinkedBlockingDeque<>();

        //内置线程
        private final Thread thread;

        /**
         * 类默认构造器
         */
        MyExecutorService(){
            //创建线程
            this.thread=new Thread(()->{
                //从队列里面取runnable
                while (true){
                    try {
                        Runnable take = this.blockingQueue.take();
                        if (null != take)take.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        /**
         * 提交一个runnable
         * @param r
         */
        private void submit(Runnable r){
            if (null != r) this.blockingQueue.offer(r);
        }
    }
}

package demo.thread.create;

public class MyThreadJoin extends Thread{

    private String name;

    public MyThreadJoin(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程运行开始!");

        for (int i = 0; i < 5; i++) {
            System.out.println("子线程"+name + "运行 : " + i);
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName()+"线程运行结束!");
    }
}

package com.thread.demo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Counter counter = new Counter() ;
        //参数一：Runnable的实现类 参数二：线程名
        Thread thread1 = new Thread(counter , "A") ;
        Thread thread2 = new Thread(counter , "B") ;
        thread1.start();
        thread2.start();

    }

    class Counter implements Runnable{

        private int count ;

        public Counter(){
            count = 0 ;
        }

        public void countAdd(){

            //synchronized代码块
            synchronized (this){
                for (int i = 0; i < 5; i++) {
                    try {
                        Log.d("threadName--->" , Thread.currentThread().getName() +":" + (count++)) ;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        //非synchronized代码块，未对count进行读写操作，所以可以不用synchronized
        public void printAdd(){
            for (int i = 0; i < 5; i++) {
                try {
                    Log.d("threadName--->" , Thread.currentThread().getName() + "count:" + count) ;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            String threadName = Thread.currentThread().getName() ;
            if (threadName.equals("A")){
                countAdd();
            }else if (threadName.equals("B")){
                printAdd();
            }
        }
    }

//    Bcount:0
//    A:0
//    Bcount:1
//    A:1
//    Bcount:2
//    A:2
//    Bcount:3
//    A:3
//    Bcount:4
//    A:4
    /* 以上是运行结果 */
    /* 由结果可知，countAdd()方法是synchronized代码块，printAdd()不是synchronized代码块。
       当一个线程访问一个对象的synchronized代码块时，其他的线程可以访问该对象的非synchronized代码块而不受阻塞*/

}

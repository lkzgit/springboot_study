package com.demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier b) {
        barrier = b;
    }

    public synchronized int getStrides() {
        return strides;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {//没有中断，就不断循环
                synchronized (this) {
                    //模拟马单位时间的移动距离
                    strides += rand.nextInt(3); // Produces 0, 1 or 2
                }
                barrier.await();//<---等待其他马到齐到循环屏障
            }
        } catch (InterruptedException e) {
            // A legitimate way to exit
        } catch (BrokenBarrierException e) {
            // This one we want to know about
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++)
            s.append("*");
        s.append(id);
        return s.toString();
    }
}

 class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<Horse>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause) {
        //初始化循环屏障
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            // 循环多次执行的任务
            public void run() {

                // The fence on the racetrack
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++)
                    s.append("=");
                System.out.println(s);

                //打印马移动距离
                for (Horse horse : horses)
                    System.out.println(horse.tracks());

                //判断有没有马到终点了
                for (Horse horse : horses)
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + "won!");
                        exec.shutdownNow();// 有只马跑赢了，所有任务都结束了
                        return;
                    }

                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });
        // 开跑！
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        new HorseRace(nHorses, pause);
    }
}


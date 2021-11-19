package com.demo.nien;

import com.demo.util.Print;
import com.demo.util.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.demo.util.ThreadUtil.sleepSeconds;

/**
 * @author lkz
 * @date 2021/11/19 15:12
 * @description CompletableFuture异步线程回调
 */
public class CompletableFutureDemo {


    ExecutorService executorService = Executors.newFixedThreadPool(2);

    /**
     * //后一个任务与前一个任务在同一个线程中执行
     * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
     * //后一个任务与前一个任务可以不在同一个线程中执行
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
     * //后一个任务在指定的 executor 线程池中执行
     * public CompletionStage<Void> thenAcceptAsync(
     *  Consumer<? super T> action,Executor executor);
     * @throws Exception
     */
    @Test
    public void thenApply() throws Exception {

        CompletableFuture cf = CompletableFuture.supplyAsync(() ->
        {
            try {
                //休眠2秒
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Print.tco("supplyAsync " + Thread.currentThread().getName());
            return "hello world";
        }, executorService).thenAccept(s ->
        {
            try {
                Print.tco("thenApply_test" + s + "world");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Print.tco(Thread.currentThread().getName());
        while (true) {
            if (cf.isDone()) {
                Print.tco("CompletedFuture...isDown");
                break;
            }
        }
    }

    //无返回值异步调用
    @Test
    public void runAsyncDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
        {
            sleepSeconds(1);//模拟执行1秒
            Print.tco("run end ...");
        });

        //等待异步任务执行完成,现时等待2秒
        future.get(2, TimeUnit.SECONDS);
    }

    //有返回值异步调用
    @Test
    public void supplyAsyncDemo() throws Exception {
//        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() ->
//        {
//            long start = System.currentTimeMillis();
//            sleepSeconds(1);//模拟执行1秒
//            Print.tco("run end ...");
//            return System.currentTimeMillis() - start;
//        });
//
//        //等待异步任务执行完成,现时等待2秒
//        long time = future.get(2, TimeUnit.SECONDS);
//        Print.tco("异步执行耗时（秒） = " + time / 1000);
        CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(i);
            }
            return list;
        });
        List list = future.get();
        System.out.println(list);
    }

    /**
     * whenComplete:
     * 子任务完成时的回调钩子
     * whenCompleteAsync:
     * 子任务完成时的回调钩子，可能不在同一线程执行
     * 区别：
     * whenComplete：是执行当前任务的线程执行继续执行 whenComplete 的任务。
     * whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池 来进行执行。
     * 方法不以 Async 结尾，意味着 Action 使用相同的线程执行，而 Async 可能会使用其他线程 执行（如果是使用相同的线程池，也可能会被同一个线程选中执行）
     * exceptionally: 异常处理的回调钩子
     *
     * @throws Exception
     */
    @Test
    public void whenCompleteDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
        {
            sleepSeconds(1);//模拟执行1秒
            Print.tco("抛出异常！");
            throw new RuntimeException("发生异常");
            //Print.tco("run end ...");
        });
        //设置执行完成后的回调钩子
        future.whenComplete(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void t, Throwable action) {
                Print.tco("执行完成！");
            }
        });
        //设置发生异常后的回调钩子
        future.exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable t) {
                Print.tco("执行失败！" + t.getMessage());
                return null;
            }
        });
        future.get();
    }

    @Test
    public void testWhenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<Object>() {
            @Override
            public Object get() {
                System.out.println(Thread.currentThread().getName() + "\t completableFuture");
                int i = 10 / 0;
                return 1024;
            }
        }).whenComplete(new BiConsumer<Object, Throwable>() {
            @Override
            public void accept(Object o, Throwable throwable) {
                System.out.println("-------o=" + o.toString());
                System.out.println("-------throwable=" + throwable);
            }
        }).exceptionally(new Function<Throwable, Object>() {
            @Override
            public Object apply(Throwable throwable) {
                System.out.println("throwable=" + throwable);
                return 6666;
            }
        });
        System.out.println(future.get());
    }

    /**
     * //后一个任务与前一个任务在同一个线程中执行
     * public CompletionStage<Void> thenRun(Runnable action);
     * //后一个任务与前一个任务可以不在同一个线程中执行
     * public CompletionStage<Void> thenRunAsync(Runnable action);
     * //后一个任务在 executor 线程池中执行
     * public CompletionStage<Void> thenRunAsync(Runnable action,Executor
     * executor);
     * 从方法的声明可以看出，thenRun 方法同 thenApply 方法类似；不同的：前一个任务处理完
     * 成后，thenRun 并不会把计算的结果传给后一个任务，而且后一个任务也没有结果输出。
     * thenRun 系列方法里的 action 参数是 Runnable 类型，所以 thenRun 既不能接收参数也不支
     * 持返回值。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("先执行第一个CompletableFuture方法任务");
                    return "testThenRun";
                }
        );

        CompletableFuture thenRunFuture = orgFuture.thenRun(() -> {
            System.out.println("接着执行第二个任务");
        });

        System.out.println(thenRunFuture.get());

    }

    /**
     * //在执行任务的同一个线程中处理异常和结果
     * public <U> CompletionStage<U> handle(
     *  BiFunction<? super T, Throwable, ? extends U> fn);
     * //可能不在执行任务的同一个线程中处理异常和结果
     * public <U> CompletionStage<U> handleAsync(
     *  BiFunction<? super T, Throwable, ? extends U> fn);
     * //在指定线程池 executor 中处理异常和结果
     * public <U> CompletionStage<U> handleAsync(
     *  BiFunction<? super T, Throwable, ? extends U> fn,
     *  Executor executor);
     * @throws Exception
     */
    @Test
    public void handleDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
        {
            sleepSeconds(1);//模拟执行1秒
            Print.tco("抛出异常！");
            throw new RuntimeException("发生异常");
            //Print.tco("run end ...");
        });
        //设置执行完成后的回调钩子
        future.handle(new BiFunction<Void, Throwable, Void>() {

            @Override
            public Void apply(Void input, Throwable throwable) {
                if (throwable == null) {
                    Print.tcfo("没有发生异常！");

                } else {
                    Print.tcfo("sorry,发生了异常！");

                }
                return null;
            }
        });

        future.get();
    }

    //有返回值异步调用
    @Test
    public void threadPoolDemo() throws Exception {
        //业务线程池
        ThreadPoolExecutor pool = ThreadUtil.getMixedTargetThreadPool();
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() ->
        {
            Print.tco("run begin ...");
            long start = System.currentTimeMillis();
            sleepSeconds(1);//模拟执行1秒
            Print.tco("run end ...");
            return System.currentTimeMillis() - start;
        }, pool);
        //等待异步任务执行完成,现时等待2秒
        long time = future.get(2, TimeUnit.SECONDS);
        Print.tco("异步执行耗时（秒） = " + time / 1000);
    }

    /**
     * //后一个任务与前一个任务在同一个线程中执行
     * public <U> CompletableFuture<U> thenApply(
     *  Function<? super T,? extends U> fn)
     * //后一个任务与前一个任务可以不在同一个线程中执行
     * public <U> CompletableFuture<U> thenApplyAsync(
     *  Function<? super T,? extends U> fn)
     * //后一个任务在指定的 executor 线程池中执行
     * public <U> CompletableFuture<U> thenApplyAsync(
     *  Function<? super T,? extends U> fn, Executor
     * executor)
     * thenApply 三个重载版本有一个共同的参数 fn，该参数表示待串行执行的第二个异步任务，
     * 其类型为 Function。fn 的类型声明涉及到两个范型参数，具体如下：
     * ⚫ 范型参数 T：上一个任务所返回结果的类型。
     * ⚫ 范型参数 U：当前任务的返回值类型。
     * @throws Exception
     */
    @Test
    public void thenApplyDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tco("firstStep outcome is " + firstStep);

                return firstStep;
            }
        }).thenApplyAsync(new Function<Long, Long>() {
            @Override
            public Long apply(Long firstStepOutCome) {
                long secondStep = firstStepOutCome * 2;
                Print.tco("secondStep outcome is " + secondStep);
                return secondStep;
            }
        });

        long result = future.get();
        Print.tco(" future is " + future);
        Print.tco(" outcome is " + result);
    }

    /**
     * thenCompose 方法在功能上与 thenApply、thenAccept、thenRun 一样，可以对两个任务进行
     * 串行的调度操作，第一个任务操作完成时，将其结果作为参数传递给第二个任务
     * @throws Exception
     */
    @Test
    public void thenComposeDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tco("firstStep outcome is " + firstStep);

                return firstStep;
            }
        }).thenCompose(new Function<Long, CompletionStage<Long>>() {
            @Override
            public CompletionStage<Long> apply(Long firstStepOutCome) {
                return CompletableFuture.supplyAsync(new Supplier<Long>() {
                    @Override
                    public Long get() {
                        long secondStep = firstStepOutCome * 2;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
            }

        });
        long result = future.get();
        Print.tco(" outcome is " + result);
    }

    /**
     * 某个任务同时依赖另外两异步任务的执行结果，则需要对另外两异步任务进行合并
     * @throws Exception
     */
    @Test
    public void thenCombineDemo() throws Exception {
        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        Integer firstStep = 10 + 10;
                        Print.tco("firstStep outcome is " + firstStep);
                        return firstStep;
                    }
                });
        CompletableFuture<Integer> future2 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        Integer secondStep = 10 + 10;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
        CompletableFuture<Integer> future3 = future1.thenCombine(future2,
                new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer step1OutCome, Integer step2OutCome) {
                        return step1OutCome * step2OutCome;
                    }
                });
        Integer result = future3.get();
        Print.tco(" outcome is " + result);
    }

    @Test
    public void applyToEitherDemo() throws Exception {
        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        Integer firstStep = 10 + 10;
                        Print.tco("firstStep outcome is " + firstStep);
                        return firstStep;
                    }
                });
        CompletableFuture<Integer> future2 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        Integer secondStep = 100 + 100;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
        CompletableFuture<Integer> future3 = future1.applyToEither(future2,
                new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer eitherOutCome) {
                        return eitherOutCome;
                    }
                });
        Integer result = future3.get();
        Print.tco(" outcome is " + result);
    }

    @Test
    public void allOfDemo() throws Exception {
        CompletableFuture<Void> future1 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务1"));

        CompletableFuture<Void> future2 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务2"));
        CompletableFuture<Void> future3 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务3"));
        CompletableFuture<Void> future4 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务4"));

        CompletableFuture<Void> all =
                CompletableFuture.allOf(future1, future2, future3, future4);
        all.join();
    }


}

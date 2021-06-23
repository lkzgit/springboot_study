package com.demo.two;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 数据多任务模拟
 */
public class ForkDemo {





//    public static void main(String[] args) throws InterruptedException {
//
//
//        //线程操作
//        // ForkDemo.mdtexcle();
//        //任务拆分
//        String pat = HttpUtil.get("10.10.10.66:9999/out/patientOut");
//        List<PatJsonDto> patJsonDtos = JSONArray.parseArray(pat, PatJsonDto.class);
//        System.out.println("kkkk");
//        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
//        Instant start = Instant.now();
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        // 提交可分解的PrintTask任务
//
//        // forkJoinPool.submit(new ForkJoinSumCalculate(docMdtExcleVos,0,docMdtExcleVos.size()));
//        //模拟患者
//        forkJoinPool.submit(new ForkJoinSumCalculate(patJsonDtos,0,patJsonDtos.size()));
//        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
//        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
//        // 关闭线程池
//        forkJoinPool.shutdown();
//        Instant end = Instant.now();
//        System.out.println("单线程耗费时间为：" + Duration.between(start, end).toMillis());
//
//    }
//
//
//    public static void mdtexcle(){
//        String pat = HttpUtil.get("10.10.10.66:9999/out/patientOut");
//        String doc = HttpUtil.get("10.10.10.66:9999/out/doctorOut");
//        List<PatJsonDto> patJsonDtos = JSONArray.parseArray(pat, PatJsonDto.class);
//        List<DocJsonDto> docJsonDtos = JSONArray.parseArray(doc, DocJsonDto.class);
//        List<DocMdtExcleVo> docMdtExcleVos = BeanUtil.copyToList(docJsonDtos, DocMdtExcleVo.class);
//        System.out.println("kkkk");
//        //患者
//        ThreadUtil.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("一个线程池方法开始执行----");
//                Instant start = Instant.now();
//
//                inserPat(patJsonDtos);
//                Instant end = Instant.now();
//                System.out.println("单线程耗费时间为：" + Duration.between(start, end).toMillis());
//                System.out.println("一个线程池方法执行结束----");
//            }
//        });
//        //医生
//        ThreadUtil.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("一个线程池方法开始执行----");
//                Instant start = Instant.now();
//
//                insertDoc(docMdtExcleVos);
//                Instant end = Instant.now();
//                System.out.println("单线程耗费时间为：" + Duration.between(start, end).toMillis());
//                System.out.println("一个线程池方法执行结束----");
//            }
//        });
//    }
//
//    public  static void  inserPat(List<PatJsonDto> list){
//        list.forEach(e->{
//            System.out.println(e);//添加患者
//            //记录添加
//        });
//    }
//
//    public static  void insertDoc(List<DocMdtExcleVo> docList){
//        docList.forEach(e->{
//            System.out.println(e);
//        });
//        // return docList;
//    }


}


//class ForkJoinSumCalculate extends RecursiveAction {
//
//    //private List<DocMdtExcleVo> list;
//    private List<PatJsonDto> list;
//    private int start;
//    private int end;
//
//    public ForkJoinSumCalculate() {
//    }
//
//    public ForkJoinSumCalculate(List<PatJsonDto> list, int start, int end) {
//        this.list = list;
//        this.start = start;
//        this.end = end;
//    }
//
//    private static final long THURSHOLD = 500;  //临界值
//
//    public List<PatJsonDto> getList() {
//        return list;
//    }
//
//    public void setList(List<PatJsonDto> list) {
//        this.list = list;
//    }
//
//    public long getStart() {
//        return start;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    public long getEnd() {
//        return end;
//    }
//
//    public void setEnd(int end) {
//        this.end = end;
//    }
//
//    @Override
//    protected void compute() {
//        int length = end - start;
//        if(length<THURSHOLD){
//            // ExcleController.insertDoc(list);
//            ExcleController.inserPat(list);
//        }else{
//            System.out.println("任务拆解");
//            int t=(start+end)/2;
//            ForkJoinSumCalculate left = new ForkJoinSumCalculate(list, start, t);
//            left.fork();
//            ForkJoinSumCalculate right = new ForkJoinSumCalculate(list, t, end);
//            right.fork();
//        }
//
//    }
//
//}

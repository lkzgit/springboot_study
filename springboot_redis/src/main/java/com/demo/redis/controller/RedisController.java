package com.demo.redis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lkz
 * @date 2021/9/28 14:38
 * @description
 */
@RestController
public class RedisController {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private Redisson redisson;


//    @GetMapping("/buy_goods")
//    public void buy_Goods(){
//
//
//
//        String value = UUID.randomUUID().toString()+Thread.currentThread().getName();
//
//        RLock redissonLock = redisson.getLock(REDIS_LOCK_KEY);
//        redissonLock.lock();
//        try{
//            String result = stringRedisTemplate.opsForValue().get("goods:001");
//            int goodsNumber = result == null ? 0 : Integer.parseInt(result);
//
//            if (goodsNumber > 0){
//                int realNumber = goodsNumber - 1;
//                stringRedisTemplate.opsForValue().set("goods:001",realNumber + "");
//                System.out.println("你已经成功秒杀商品，此时还剩余：" + realNumber + "件"+"\t 服务器端口: "+serverPort);
//                return "你已经成功秒杀商品，此时还剩余：" + realNumber + "件"+"\t 服务器端口: "+serverPort;
//            }else {
//                System.out.println("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort);
//            }
//            return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口: "+serverPort;
//
//        }finally {
//            //还在持有锁的状态，并且是当前线程持有的锁再解锁
//            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()){
//                redissonLock.unlock();
//            }
//
//        }
//    }

    /**
     * 从数据库查询并封装数据::redis分布式锁
     *
     */
    public void getCatalogJsonFromDbWithRedisLock() {

        //1、占分布式锁。去redis占坑      设置过期时间必须和加锁是同步的，保证原子性（避免死锁）
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功...");
            List<Integer> dataFromDb = null;
            try {
                //加锁成功...执行业务
                dataFromDb = dateList();
            } finally {
                //这一步等同于下面的判断
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                //删除锁
                stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);

                //先去redis查询下保证当前的锁是自己的
                //获取值对比，对比成功删除=原子性 lua脚本解锁
                // String lockValue = stringRedisTemplate.opsForValue().get("lock");
                // if (uuid.equals(lockValue)) {
                //     //删除我自己的锁
                //     stringRedisTemplate.delete("lock");
                // }
            }

            System.out.println(dataFromDb);

        } else {
            System.out.println("获取分布式锁失败...等待重试...");
            //加锁失败...重试机制
            //休眠一百毫秒
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             getCatalogJsonFromDbWithRedisLock();     //自旋的方式
        }
    }




    @GetMapping("getList")
    public void getList(){
        List<Integer> dateLit =new ArrayList<>();

        Object dateKey = stringRedisTemplate.opsForValue().get("date");
        if(dateKey==null){
            System.out.println("缓冲数据为空-------");
            this.dateList();
            System.out.println("缓存为空添加: "+dateLit);
            stringRedisTemplate.opsForValue().set("date",JSON.toJSONString(dateLit));
        }else{
            System.out.println("缓存数据中取出数据");
            //JSON跨语言。跨平台兼容。
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String date1 = ops.get("date");
            Object date = stringRedisTemplate.opsForValue().get("date");
            List<Integer> integersList = JSON.parseObject((String) date, new TypeReference<List<Integer>>() {});
            System.out.println("缓存: "+integersList);
        }


    }

    //模拟数据库数据
    private List<Integer> dateList(){
        List<Integer> dateLit =new ArrayList<>();
        for(int i=0;i<10;i++){
            dateLit.add(i);
        }
        return dateLit;
    }


}

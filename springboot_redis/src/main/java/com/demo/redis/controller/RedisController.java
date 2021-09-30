package com.demo.redis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.transaction.annotation.Transactional;
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


    /**
     * @Author lkz
     * @Description //SpringCache start ↓  解决缓存不一致的问题
     * @Date 14:02 2021/9/30
     **/
    /**
     * @Description //TODO
     * 每一个需要缓存的数据我们都来指定要放到那个名字的缓存。【缓存的分区(按照业务类型分)】
     * 代表当前方法的结果需要缓存，如果缓存中有，方法都不用调用，如果缓存中没有，会调用方法。最后将方法的结果放入缓存
     * 默认行为
     *      如果缓存中有，方法不再调用
     *      key是默认生成的:缓存的名字::SimpleKey::[](自动生成key值)
     *      缓存的value值，默认使用jdk序列化机制，将序列化的数据存到redis中
     *      默认时间是 -1：
     *
     *   自定义操作：key的生成  自定义生成的key =" '' " 双包单
     *      指定生成缓存的key：key属性指定，接收一个Spel
     *      指定缓存的数据的存活时间:配置文档中修改存活时间
     *      将数据保存为json格式
     *
     *
     * 4、Spring-Cache的不足之处：
     *  1）、读模式
     *      缓存穿透：查询一个null数据。解决方案：缓存空数据  在配置文件重设置
     *      缓存击穿：大量并发进来同时查询一个正好过期的数据。解决方案：加锁 ? 默认是无加锁的;使用sync = true来解决击穿问题
     *      缓存雪崩：大量的key同时过期。解决：加随机时间。加上过期时间
     *  2)、写模式：（缓存与数据库一致）
     *      1）、读写加锁。
     *      2）、引入Canal,感知到MySQL的更新去更新Redis
     *      3）、读多写多，直接去数据库查询就行
     *
     *  总结：
     *      常规数据（读多写少，即时性，一致性要求不高的数据，完全可以使用Spring-Cache）：写模式(只要缓存的数据有过期时间就足够了)
     *      特殊数据：特殊设计
     *
     *  原理：
     *      CacheManager(RedisCacheManager)->Cache(RedisCache)->Cache负责缓存的读写
     **/
    @GetMapping("testCache")
    @Cacheable(value = {"category","product"},key = " 'testKey'")
    public void testFindSpringCache(){
        System.out.println("查询数据库----");
        this.dateList();
    }

    /**
     * @CacheEvict:失效模式 触发删除从缓存中
     * @CachePut:双写模式，需要有返回值
     * 1、同时进行多种缓存操作：@Caching
     * 2、指定删除某个分区下的所有数据 @CacheEvict(value = "category",allEntries = true)
     * 3、存储同一类型的数据，都可以指定为同一分区
     */
     @Caching(evict = {
             @CacheEvict(value = "category",key = "'getLevel1Categorys'"),//指定那个分区下的某个key
             @CacheEvict(value = "category",key = "'getCatalogJson'")
     })
   // @CacheEvict(value = "category",allEntries = true)       //删除某个分区下的所有数据
    @Transactional(rollbackFor = Exception.class)
     @GetMapping("updateCascade")
    public void updateCascade() {

        RReadWriteLock readWriteLock = redisson.getReadWriteLock("catalogJson-lock");
        //创建写锁
        RLock rLock = readWriteLock.writeLock();
        try {
            rLock.lock();
           this.dateList().add(10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        //同时修改缓存中的数据
        //删除缓存,等待下一次主动查询进行更新
    }


    /**
     * @Description //SpringCache end   ↑
     * @Date 14:02 2021/9/30
     **/


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

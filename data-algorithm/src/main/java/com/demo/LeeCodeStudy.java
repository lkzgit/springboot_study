package com.demo;

import com.demo.domain.ListNode;
import org.junit.Test;

import java.util.*;

/**
 * 力扣算法
 */
public class LeeCodeStudy {

    public static void main(String[] args) {

    }

    /**
     * 求三角形的最大周长
     * 给定由一些正数（代表长度）组成的数组 A，返回由其中三个长度组成的、
     * 面积不为零的三角形的最大周长。
     * 如果不能形成任何面积不为零的三角形，返回 0。
     *
     */
    @Test
    public void trigonperimterTest(){
        int[] arr={3,2,3,4};
        System.out.println(this.trigonMethod(arr));
    }
    public int trigonMethod(int[] arr){
        Arrays.sort(arr);
        if(arr.length<3){
            return 0;
        }
        for(int i=arr.length-1;i>=2;i--){
            if(arr[i-1]+arr[i-2]>arr[i]){
                return arr[i]+arr[i-1]+arr[i-2];
            }
        }
        return 0;
    }

    /**
     * 柠檬水找零
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     * 注意，一开始你手头没有任何零钱。
     * 如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     *
     *
     */
    @Test
    public void ningmengTest(){
        int[] arr={5,5,20};

        System.out.println(this.ningmengMethod(arr));
    }

    public boolean ningmengMethod(int[] arr){
        int five=0,ten=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]==5){
                five++;
            }else if(arr[i]==10){
                if(five==0){
                    return false;
                }
                five--;
                ten++;
            }else{
                if(five>0 &&ten>0){
                    five--;
                    ten--;
                }else if(five>=3){
                    five-=3;
                }else{
                   return false;
                }
            }
        }
        return true;
    }

    /**
     * 最长连续递增序列
     * 输入：nums = [1,3,5,4,7]
     * 输出：3
     * 解释：最长连续递增序列是 [1,3,5], 长度为3。
     * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
     *
     */
    @Test
    public void bestAdd(){
        int[] arr={1,2,3,2,3,4,5,4,5,6,7,8};
        int start=0; int max=0;
        for(int i=0;i<arr.length;i++){
            if(i>0&&arr[i]<=arr[i-1]){
                start=i;
            }
          max=  Math.max(max,i-start+1);
        }
        System.out.println(max);
    }


    /**
     * 子数组最大的平均数
     * 输入：[1,12,-5,-6,50,3], k = 4
     * 输出：12.75
     * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
     *
     */
    @Test
    public void parTest(){
        int[] arr={1,12,-5,-6,50,3};int k=4;

        System.out.println("子数组最大的平均值:"+this.perSum(arr, k));
    }

    public double perSum(int[] nums,int k){
            int sum=0;
            int n=nums.length;
            //算出k位置之前的最大值
            for(int i=0;i<k;i++){
                sum+= nums[i];
            }
            int max=sum;
            for(int i=k;i<n;i++){
                sum=sum-nums[i-k]+nums[i];
                //比较
                max=Math.max(sum,max);
            }
            return 1.0*max/k;
    }

    /**
     * 合并两个数组nums1 nums2将nums2合并到nums1中 nums1成为一个有序数组
     *
     */
    @Test
    public void mergeArrTest(){
        //时间复杂度较高
        int[] nums1=new int[]{1,3,5,7,9,0,0,0,0};int[] nums2=new int[]{2,4,6,8};
        int m=5,n=4;
//        System.arraycopy(nums2,0,nums1,m,n);
//        Arrays.sort(nums1);
//        System.out.println(nums1.toString());
        System.out.println("双指针模式从后向前："+Arrays.toString(this.doublePoint(nums1, m, nums2, n)));
        //System.out.println("双指针模式从前向后："+Arrays.toString(this.doublePointer(nums1, m, nums2, n)));
    }
    //双指针模式 从后向前
    public int[] doublePoint(int[] nums1,int m,int[]nums2,int n){
        int p1=m-1;
        int p2=n-1;
        int p=m+n-1;//nums1的最后一个下标
        while ((p1>=0)&& (p2>=0)){
            nums1[p--]=(nums1[p1]<nums2[p2])?nums2[p2--]:nums1[p1--];
        }
        System.arraycopy(nums2,0,nums1,0,p2+1);
        return nums1;
    }
    //双指针模式 从前向后遍历
    public int[] doublePointer(int[] nums1,int m,int[]nums2,int n){
        int[] copy_nums1=new int[m];
        System.arraycopy(nums1,0,copy_nums1,0,m);
        //设置三个指针分别指向nums1 copy_nums1,nums2
        int p=0,p1=0,p2=0;
        //p1<m表示之后的不在进行
        while (p1<m && p2<n){
            //比较p1与p2指针的指向数字，把数字较小的放入nums1中 指针后移
            nums1[p++]= copy_nums1[p1]<nums2[p2] ? copy_nums1[p1++]:nums2[p2++];
        }
        if(p1<m){
            System.arraycopy(copy_nums1,p1,nums1,p1+p2,m+n-p1-p2);
        }
        if(p2<n){
            System.arraycopy(nums2,p2,nums1,p1+p2,m+n-p1-p2);
        }

        return nums1;
    }

    /**
     * 环形列表
     */
    @Test
    public void AroudListNode(){
        ListNode node5=new ListNode(5,null);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node=new ListNode(1,node2);
        System.out.println(this.huanListNode(node));
        System.out.println(huanListNodes(node));

    }
    public boolean huanListNodes(ListNode node){
        if(node==null){
            return false;
        }
        ListNode slow=node;
        ListNode quick=node.next;
        while (slow!=quick){
            if(quick==null || quick.next==null){
                return false;
            }
            slow=slow.next;
            quick=quick.next.next;
        }
        return true;
    }

    public boolean huanListNode(ListNode head){
        Set<ListNode> set=new HashSet<>();
        while (head!=null){
            if(!set.add(head)){
                return true;
            }
           head= head.next;
        }
        return false;
    }


    /**
     * 排列硬币
     * 题目 总共有k枚硬币 摆成阶梯形状 第k行 正好是k枚硬币
     * 给定一个n 返回完整阶梯行的总行数
     *
     */
    @Test
    public void orderCoin(){
        System.out.println(this.baoliOrderCoin(5));
        System.out.println(this.erfenOrderCoin(5));
        System.out.println(this.arrageCoin(5));
    }
    //牛顿迭代
    public int arrageCoin(int n){
        if(n==0){
            return 0;
        }
       return (int)this.coin(n, n);
    }

    public double coin(double x,int n){
        double res=(x+(2*n-x)/x)/2;
        if(res==x){
            return x;
        }else{
           return this.coin(res,n);
        }
    }

    //二分查找
    public int erfenOrderCoin(int n){
        int low=0,right=n;
        while (low<=right){
            //算出行数
           int mid= (right-low)/2+low;
           //求mid行耗费的硬币数
           int cost=(mid*(mid+1))/2;
           if(cost==0){
               return mid; //返回行数
           }else if(cost>n){
               right=mid-1;
           }else{
               low=mid+1;
           }

        }
        return right;
    }
    //暴力方法
    public int baoliOrderCoin(int n){
        for(int i=1;i<=n;i++){
            n=n-i;
            if(n<=i){
                return i;
            }
        }
        return 0;
    }



    /**
     * 斐波那契数列 求第n位的值
     * 每一位的值等于他前两位数字之和 前两位固定 0,1,1,2,3,5,8,,,,
     */
    @Test
    public void feibonaqishulie(){
        int tar=10;
        int shulie = this.shulie(tar);
        System.out.println(shulie);

        int[] t=new int[tar+1];
        System.out.println(this.shulieT(t,tar));
        System.out.println("双指针算法");
        System.out.println(this.interate(tar));


    }

    //双指针算法
    public int interate(int num){
        int low=0,high=1;
        for(int i=2;i<=num;i++){
            int sum=low+high;
            low=high;
            high=sum;
        }
        return high;
    }
    //去重递归
    public int shulieT(int[] nums,int tar){
        if(tar==0){
            return 0;
        }
        if(tar==1){
            return 1;
        }
        if(nums[tar]!=0){
           return nums[tar];
        }
        nums[tar]=shulieT(nums,tar-1)+shulieT(nums,tar-2);
        return nums[tar];
    }

    //暴力解法
    public  int shulie(int t){
        if (t == 0) {
            return 0;
        }
        if(t==1){
            return 1;
        }else{
            return shulie(t-1)+shulie(t-2);
        }


    }


    /**
     * 三个数最大的乘积
     */
    @Test
    public void threeSums(){
        int[] nums={1,2,3,4,5,6};
        Arrays.sort(nums);//排序

        int n=nums.length;
        int max = Math.max(nums[0] * nums[1] * nums[n - 1], nums[n - 1] * nums[n - 2] * nums[n - 3]);
        System.out.println(max);
        System.out.println("解法二");
        int mx1 = Integer.MIN_VALUE, mx2 = Integer.MIN_VALUE, mx3 = Integer.MIN_VALUE;
        int mn1 = Integer.MAX_VALUE, mn2 = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > mx1) {
                mx3 = mx2;
                mx2 = mx1;
                mx1 = num;
            } else if (num > mx2) {
                mx3 = mx2; mx2 = num;
            } else if (num > mx3) {
                mx3 = num;
            }
            if (num < mn1) {
                mn2 = mn1; mn1 = num;
            } else if (num < mn2) {
                mn2 = num;
            }
        }
        int max1 = Math.max(mx1 * mx2 * mx3, mx1 * mn1 * mn2);
        System.out.println(max1);


    }



    /**
     * x的平方根
     */
    @Test
    public void xPingfanggen(){
        int x=24;
        int index=-1,left=0,right=x;

        //二分法
//        while (left<=right){
//            int mid=left+(right-1)/2;
//           if(mid*mid<=x){
//               index=mid;
//               left=mid+1;
//           }else {
//               right=mid-1;
//
//           }
//        }
        if(x==0){
            return;
        }
        System.out.println(index);
        double sqrt = LeeCodeStudy.sqrt(x, x);
        System.out.println(sqrt);
    }

    private static double sqrt(double i,int x){
        double res=(i+x/i)/2;
        if(res==i){
            return i;
        }else{
            return sqrt(res,x);
        }
    }


    /**
     * 九九乘法表
     */
    @Test
    public void jiujiu(){
        print();
    }

    public static void print(){
        for(int i = 1;i <= 9;i++){
            for(int j = 1;j <= i;j++){
                System.out.print(i + "*" + j + "=" + (i * j)+"--");
            }
            System.out.println();
        }
    }


    //楼梯有几种方法
    @Test
    public  void test1(){
        // System.out.println(louti(5));
        System.out.println(jumpByFor(5));
    }
    // 楼梯递归方法
    public int louti(int num){
        if(num<1){
            throw new IllegalArgumentException(num+"参数不能小于1");
        }
        if(num ==1 || num ==2){
            return num;
        }
        return louti(num-2)+(num-1);
    }
    //for循环方式
    public long jumpByFor(int n){
        int first = 1;
        int second = 2;
        int third = 0;
        for(int i = 3; i <= n; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }

    // 鸡兔同笼
    @Test
    public void test2(){
        jitu(35,94);
    }
    // 鸡有两只叫 兔子四只脚
    public  void jitu(int head, int foot){
        /**
         * 鸡头+秃头=head
         * 鸡+兔脚=foot
         *
         */
//        int ji;int tu;
//        if(foot%2==0){
//            ji = head * 2 - foot/2;
//            tu = head - ji;
//            if(ji>0&&tu>0) {
//                System.out.println("鸡的数量为：" +ji +"兔子的数量为：" + tu);
//            }else {
//                System.out.println("不存在");
//            }
//
//        }
        int ji;
        for(int i=0;i<=head;i++){
            ji=head-i;
            if(2*i+ji*4==foot){
                System.out.println(i+"--"+ji);
            }
        }

    }


    /**
     * 排序 [1,3,5,6,4] 结果 [1,3,4,5,6]
     */
    @Test
    public void orderBy(){
        int[] num={1,3,6,5,4};

    }

    /**
     * 寻找数组的中心下标
     */
    @Test
    public void arrCentreIndex(){

    }



    /**
     * 检测概数是不是2n次方
     */
    @org.junit.Test
    public void test(){
        int a=10;
        while (a%2==0){
            a=a/2;
            System.out.println(a);
            if(a==1){
                System.out.println("是："+a);
            }
            System.out.println(a);
        }

    }
    /**
     * 给定一个升序的数组 ，满足两数相加等于目标值
     * 两数之和
     */
    @Test
    public void twoSums(){
        int[] nums={1,2,3,5,6};
        int[] erfen = this.erfen(nums);
        System.out.println(Arrays.toString(erfen));
        System.out.println("指针查找");

        this.twoPoint(nums,8);
    }
    //指针模式
    public void twoPoint(int[] nums,int target){
        int low=0,high=nums.length-1;
        while (low<high){
           int sum=nums[low]+nums[high];
            if(sum==target){
                System.out.println(low+","+high);
            }else if(sum < target){
                low++;
            }else{
                high--;
            }
        }
    }

    public int[] erfen(int[] nums){
        int target=10;
        for(int i=0;i<nums.length;i++){
            int low=i,high=nums.length-1;
            while (low<=high){
                int mid=(high-low)/2+low;
                if(nums[mid]==target-nums[i]){
                   return new int[]{i,mid};
                }else if(nums[mid]>target-nums[i]){
                    high=mid-1;
                }else{
                    low=mid+1;
                }
            }
        }
        return new int[]{0};
    }

    /**
     * 力扣算法第一题 求两数之和 无序数组
     * 给定一个数组 从数组中找出一个满足两数相加等于目标值 并返回下标
     */
    @org.junit.Test
    public void twoSum(){
        int [] nums={1,2,6,5};
        int target=6;
        //暴力
        for(int i=0;i<nums.length;i++){
           for(int j=i+1;j<nums.length;j++){
               if(target-nums[i]==nums[j]){
                   System.out.println(i+","+j);
               }
           }
        }
        System.out.println("---------------");
        // 方法二
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                System.out.println(map.get(nums[i])+","+i);
            }
            map.put(target - nums[i], i);
        }
        System.out.println(map);

    }
    /**
     * 链表翻转
     * 输入1,2,3
     * 输出3,2,1
     */
    @Test
    public void linkFanzhuang(){
        ListNode node5=new ListNode(5,null);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node=new ListNode(1,node2);
        //迭代
        ListNode listNode = ListNode.getListNode(node);
        System.out.println(listNode);
        //递归
        ListNode dIgui = ListNode.getDIgui(node);
        System.out.println("递归："+dIgui);
    }

    /**
     * 统计素数的个数 0,1不算
     */
    @Test
    public void sushu(){
        int a=0;

        for(int i=2;i<=50;i++){
          a+= findSuShu(i)?1:0;
        }

        System.out.println(a);
    }

    public Boolean findSuShu(int x){
        for(int j=2;j<x;j++){
            if(x%j==0){
                System.out.println(x+"不是素数");
                return false;
            }
        }
        System.out.println(x+"是素数");
        return true;
    }

    /**
     * 埃氏筛选法查找素数
     */
    public static int eratosthenes(int n){
        boolean [] isPrime=new boolean[n];//false代表素数
        int count=0;
        for(int i=2;i<n;i++){
            if(!isPrime[i]){
                count++;
                for(int j=2* i;j<n;j+=i){
                    isPrime[j]=true;
                }
            }

        }
        return count;
    }

    /**
     * 输出start到end之间的素数
     *
     * @param start 起始数字
     * @param end   结束数字
     */
    public static void calPrimeNum(int start, int end) {
        // 用作标识能被除1和本身外,多少数字整除
        int state = 0;
        // 用于循环判断start到end是否为质数
        for (int i = start; i <= end; i++) {
            // 用于从i到0依次求余，判断是否除1和本身外有其他数字能整除
            for (int j = i; j > 0; j--) {
                // 判断是否除1和本身外有其他数字能整除
                if (i % j == 0 && j > 1 && j < i) {
                    // 若存在，将状态+1
                    state++;
                }
            }
            if (state > 0) {
                // 清空状态，用于下一个数的判断。注：不清空会影响后面程序的运行结果
                state = 0;
            } else {
                // 输出是素数的数
                System.out.println(i + "是素数");
            }
        }
    }

    /**
     * 删除排序数组中的重复项 要求在原数组中删除
     * 例如:[1,2,2,3,3,4]
     * 返回 删除后的数组长度
     */
    @Test
    public void deleArryRepe(){
        int[] arr={1,2,2,2,3,4};
        if(arr==null){
            System.out.println("-------");
        }
        int i=0;
        for(int j=1;j<arr.length;j++){
            if(arr[j]!=arr[i]){
            i++;
            arr[i]=arr[j];
            }
        }
        System.out.println("kkkk:"+i);
    }



}

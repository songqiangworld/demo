package com.example.demo;

import com.example.demo.config.TestConfigProperties;
import com.example.demo.dao.TestDao;
import com.example.demo.service.impl.MessageServiceImpl;
import com.example.demo.utils.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private MessageServiceImpl messageService;

    @Test
    public void contextLoads() {
        int x = 1000;
        Integer a = x;
        int length = a.toString().length();
        System.out.println(length);
    }

    public long digPow(int n, int p) {
        // your code
        Integer a = n;
        int length = a.toString().length();
        for (int x = 1; x <= length; x++) {
            int num = n;
            int shi = 1;
            for (int y = 0; y < length - x; y++) {
                num = num / 10;
                shi *= 10;
            }
            n = n - num * shi;
            System.out.println(num);
        }
        return -1;
    }

    @Test
    public void main2() {

        System.out.println(sumDigPow(1 , 10));
    }

    public List<Long> sumDigPow(long a, long b) {

        List<Long> longs = new ArrayList<>();
        int i1 = (int) a;
        int i2 = Integer.parseInt(String.valueOf(b));
        for(int y = i1;y<=i2;y++){
            String str = String.valueOf(y);
            int sum = 0;
            for(int x = 0;x<str.length();x++){
                int i = Integer.parseInt(String.valueOf(str.charAt(x)));
                if(x==0){
                    sum += i;
                }else {
                    for (int z = 0;z<x;z++){
                        sum += z * z;
                    }
                }

            }

            if (sum == y){
                longs.add((long) y);
            }
        }
        return longs;
    }

    public static int ConvertBinaryArrayToInt(List<Integer> binary) {
        int sum = 0;
        // Your Code
        for (int x = 0;x<binary.size();x++){

            if (1==binary.get(x)){
                switch (x){
                    case 0 : sum += 8;break;
                    case 1: sum += 4;break;
                    case 2: sum += 2;break;
                    case 3: sum += 1;break;
                }
            }
        }
        return sum;
    }

    @Test
    public void main3() {
        for (String path : FileUtils.getSuffixFilesPath("E:\\test\\api", "xls")) {
            FileUtils.copyFile(path,path.replace("xls","index"));
        }
        ;
    }

    @Test
    public void main4() {
        int[] nums1 = {1,2};
        int[] nums2 = {3,4};
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : nums1) {

           list.add(i);
        }
        for (int i : nums2) {
            list.add(i);
        }
        Collections.sort(list);
        double z = 0;
        if (list.size()%2==0){
            int i = list.size() / 2;
            z = (list.get(i) + list.get(i - 1))/2.0;
        }else {
            z = list.get(list.size() / 2);
        }

        System.out.println(z);
    } 

    @Test
    public void main5() throws Exception{
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-30 10:48:19");
        Calendar c = Calendar.getInstance();
        c.setTime(beginTime);
        c.add(c.DATE, 1);  //减一天  -1
        Date date = c.getTime();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));

    }

    public List<String> findAndReplacePattern(String[] words, String pattern) {
        ArrayList<Integer> integers = new ArrayList<>();
        int start = 0;
            int count = 1;
            char[] chars = pattern.toCharArray();
            for (int y =1;y<pattern.length();y++) {
                if (chars[start] == chars[y]) {
                    count++;
                }else {
                    if(count>1){
                        integers.add(count);
                        start+=count;
                    }else {
                        integers.add(count);
                        start+=count;
                    }
                    count = 1;
                }

                if (y>=pattern.length()-1){
                    integers.add(count);
                }
            }
        System.out.println(integers);




        /*for (int x =0;x<words.length;x++){
            String word = words[x];
            char[] chars2 = word.toCharArray();
            int count2 = 0;
            int num = 0;
            for (int y =0;y<word.length();y++) {
                for (int z =1;z<word.length();z++) {
                    if (chars[y] == chars[z]) {
                        count++;
                    }
                }
                y+=count;

                if (count==integers.get(num)){
                    System.out.println(word);
                }
                num++;
            }


        }*/


        return null;
    }

    @Test
    public void main6() {
        String s  = "Hello World";
        String trim = s.trim();
        String s2 = trim.replaceAll("\"", "");
        int i = trim.lastIndexOf(" ");
        String s1 = trim.substring(i+1);
        System.out.println(s1);
        System.out.println(s1.length());
    }
    @Autowired
    private TestDao TestDao;
    private static TestConfigProperties configProperties2;

@Test
    public void test1(){

    TestDao.update1();

    }



}

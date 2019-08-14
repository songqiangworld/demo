package com.example.demo.utils;

import java.util.Iterator;
import java.util.TreeSet;

public class Hongbao {
    private final int[] moneyList;
    private final int number;
    private final int totalMoney;
    private int cur;
    public Hongbao(int number, int totalMoney) {
        if (number > totalMoney)
            throw new IllegalArgumentException("number <= totalMoney");
        this.number = number;
        this.totalMoney = totalMoney;
        moneyList = new int[number + 1];
        TreeSet<Integer> moneySet = new TreeSet<>();
        initial(moneySet);
        int i = -1;
        Iterator<Integer> iter = moneySet.iterator();
        while (iter.hasNext()) {
            moneyList[++i] = iter.next();
        }
        cur = 0;
    }
    protected void initial(TreeSet<Integer> moneySet) {
        moneySet.add(0);
        moneySet.add(totalMoney);
        for (int i = 1; i < number; ++i) {
            int tempMoney = rand(totalMoney);
            while (moneySet.contains(tempMoney)) {
                ++tempMoney;
                tempMoney %=  totalMoney;
            }
            moneySet.add(tempMoney);
        }
    }
    protected int rand(int n) {
        return (int)(Math.random() * (n - 1) + 1);
    }
    public int getOne() {
        return moneyList[++cur] - moneyList[cur - 1];
    }

    public static void main(String[] args) {
        Hongbao h = new Hongbao(2, 1000);

        System.out.println( h.getOne());
        System.out.println( h.getOne());
   }
}

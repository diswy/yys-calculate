package com.yys.king_of_europe.fu.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by @author xiaofu on 2019/5/22.
 */
@Entity
public class YuHunResult {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double gongji;
    private double gongjijiacheng;
    private double fangyu;
    private double fangyujiacheng;
    private double shengming;
    private double shengmingjiacheng;
    private double sudu;
    private double mingzhong;
    private double dikang;
    private double baoji;
    private double baojishanghai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGongji() {
        return gongji;
    }

    public void setGongji(double gongji) {
        this.gongji = gongji;
    }

    public double getGongjijiacheng() {
        return gongjijiacheng;
    }

    public void setGongjijiacheng(double gongjijiacheng) {
        this.gongjijiacheng = gongjijiacheng;
    }

    public double getFangyu() {
        return fangyu;
    }

    public void setFangyu(double fangyu) {
        this.fangyu = fangyu;
    }

    public double getFangyujiacheng() {
        return fangyujiacheng;
    }

    public void setFangyujiacheng(double fangyujiacheng) {
        this.fangyujiacheng = fangyujiacheng;
    }

    public double getShengming() {
        return shengming;
    }

    public void setShengming(double shengming) {
        this.shengming = shengming;
    }

    public double getShengmingjiacheng() {
        return shengmingjiacheng;
    }

    public void setShengmingjiacheng(double shengmingjiacheng) {
        this.shengmingjiacheng = shengmingjiacheng;
    }

    public double getSudu() {
        return sudu;
    }

    public void setSudu(double sudu) {
        this.sudu = sudu;
    }

    public double getMingzhong() {
        return mingzhong;
    }

    public void setMingzhong(double mingzhong) {
        this.mingzhong = mingzhong;
    }

    public double getDikang() {
        return dikang;
    }

    public void setDikang(double dikang) {
        this.dikang = dikang;
    }

    public double getBaoji() {
        return baoji;
    }

    public void setBaoji(double baoji) {
        this.baoji = baoji;
    }

    public double getBaojishanghai() {
        return baojishanghai;
    }

    public void setBaojishanghai(double baojishanghai) {
        this.baojishanghai = baojishanghai;
    }
}

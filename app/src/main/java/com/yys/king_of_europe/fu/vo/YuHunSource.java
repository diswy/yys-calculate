package com.yys.king_of_europe.fu.vo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * Created by @author xiaofu on 2019/5/16.
 */
@Entity
public class YuHunSource {
    @NonNull
    @SerializedName("御魂ID")
    @PrimaryKey
    private String id;

    @SerializedName("御魂类型")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("位置")
    @ColumnInfo(name = "pos")
    private int pos;

    @SerializedName("御魂等级")
    @ColumnInfo(name = "rank")
    private int rank;

    @SerializedName("御魂星级")
    @ColumnInfo(name = "quality")
    private int quality;

    @SerializedName("攻击")
    @ColumnInfo(name = "gongji")
    private double gongji;

    @SerializedName("攻击加成")
    @ColumnInfo(name = "gongjijiacheng")
    private double gongjijiacheng;

    @SerializedName("防御")
    @ColumnInfo(name = "fangyu")
    private double fangyu;

    @SerializedName("防御加成")
    @ColumnInfo(name = "fangyujiacheng")
    private double fangyujiacheng;

    @SerializedName("生命")
    @ColumnInfo(name = "shengming")
    private double shengming;

    @SerializedName("生命加成")
    @ColumnInfo(name = "shengmingjiacheng")
    private double shengmingjiacheng;

    @SerializedName("速度")
    @ColumnInfo(name = "sudu")
    private double sudu;

    @SerializedName("效果命中")
    @ColumnInfo(name = "mingzhong")
    private double mingzhong;

    @SerializedName("效果抵抗")
    @ColumnInfo(name = "dikang")
    private double dikang;

    @SerializedName("暴击")
    @ColumnInfo(name = "baoji")
    private double baoji;

    @SerializedName("暴击伤害")
    @ColumnInfo(name = "baojishanghai")
    private double baojishanghai;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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


    public YuHunSource(@NonNull String id) {
        this.id = id;
    }
}

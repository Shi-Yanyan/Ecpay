package com.onetoall.yjt;

import java.io.Serializable;

public class Good implements Serializable
{
    private String fid;
    private String flowerName;
    private String flowerImage;
    private String oldPrice;
    private String nowPrice;
    private String classtify;

    public String getFid()
    {
        return fid;
    }

    public void setFid(String fid)
    {
        this.fid = fid;
    }

    public String getFlowerName()
    {
        return flowerName;
    }

    public void setFlowerName(String flowerName)
    {
        this.flowerName = flowerName;
    }

    public String getFlowerImage()
    {
        return flowerImage;
    }

    public void setFlowerImage(String flowerImage)
    {
        this.flowerImage = flowerImage;
    }

    public String getOldPrice()
    {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice)
    {
        this.oldPrice = oldPrice;
    }

    public String getNowPrice()
    {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice)
    {
        this.nowPrice = nowPrice;
    }

    public String getClasstify()
    {
        return classtify;
    }

    public void setClasstify(String classtify)
    {
        this.classtify = classtify;
    }


}

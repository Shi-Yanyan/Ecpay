package com.onetoall.yjt.utils;

import android.widget.ImageView;

/**
 * @author qinwei email:qinwei_it@163.com
 * @version 1.0
 * @created createTime: 2016-2-22 下午1:48:57
 */

public interface IImageDisplay {
    void displayImage(String uri, ImageView imageView);

    void displayImage(String uri, ImageView imageView, ImageDisplay.DisplayOptions options);

    void displayImage(int id, ImageView imageView);

    void clearMemoryCache();
}

package org.syanstue.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, Integer>
{
    private String dirpath;
    private String filepath;
    private Context context;
    private Button btn;
    private ImageView img;

    public DownloadTask(Context context, Button btn, ImageView img)
    {
        this.context = context;
        this.btn     = btn;
        this.img     = img;
    }

    @Override
    protected Integer doInBackground(String... strings)
    {
        String filename = strings[1];
        //将MainActivity中的第二个文件名赋给filename
//        dirpath = Environment.getExternalStorageDirectory() + "/download_picture/";
        dirpath = Environment.getRootDirectory() + "/download_picture/";

        //把外部环境+要开创的新的路径赋给dirpath
        File dir = new File(dirpath);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        filepath = dirpath + filename;
        File file = new File(filepath);
        if (!file.exists())
        {//判断文件是否存在
            try
            {
                file.createNewFile();//不存在创建新文件
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            return -1;
            //不存在则返回返回值（-1=文件已存在）
        }

        InputStream inputStream = null;//定义一个InputStream变量 ，输入流
        OutputStream outputStream = null;//定义一个InputStream变量 ，输出流
        /*下载图片时--输入流是网络，输出流是本地*/
        /*上传图片时--输出流是网络，输入流是本地*/
        try
        {
            URL url = new URL(strings[0]);//创建URL对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //用openConnection获取HttpURLConnection对象
            if (connection.getResponseCode() == 200)
            {
                //判断返回值是否为200（200为正常值，404为客户端错误，505为服务器错误）
                inputStream = connection.getInputStream();
            }
            else
            {
                return -2;//错误则返回返回值（-2=网络错误）
            }
            outputStream = new FileOutputStream(file);//输出流到本地
            int length = 0;//下载总量
            byte[] buffer = new byte[4 * 1024];//缓存区（4*1204=4k）
            while ((length = inputStream.read(buffer)) != -1)
            {
                //下载一直持续到最后一波正好结束再返回时发现没东西时结束（或者有空余）。
                outputStream.write(buffer, 0, length);
                //输入流写缓存区内容，从0到总长度
            }
            inputStream.close();//关闭输入流
            outputStream.close();//关闭输出流
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 1;
        //返回值设为1（1=下载完成）
    }

    @Override
    protected void onPostExecute(Integer integer)
    {
        super.onPostExecute(integer);
        switch (integer)
        {
            case 1:
                //(1=下载完成)
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = BitmapFactory.decodeFile(filepath);//将filepath（图片详细路径）从本地图库调用出来
                img.setImageBitmap(bitmap);//把值赋给控件
                //下载完成显示图片
                break;
            case -1:
                //(-1=文件已存在)
                Toast.makeText(context, "文件已存在", Toast.LENGTH_SHORT).show();
                Bitmap bm = BitmapFactory.decodeFile(filepath);
                img.setImageBitmap(bm);
                break;
            case -2:
                //（-2=网络错误）
                Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}




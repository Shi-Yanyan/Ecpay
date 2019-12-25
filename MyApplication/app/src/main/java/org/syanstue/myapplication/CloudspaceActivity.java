package org.syanstue.myapplication;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CloudspaceActivity extends AppCompatActivity
{
    private Button btn;
    private ImageView img;
    private String filename = "http://blog.mydrivers.com/Img/20120207/2012020709240832.jpg";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudspace);

        btn = findViewById(R.id.testButton);
        img = findViewById(R.id.testImageView);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                URL url  = new URL(filename);
                                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                                httpConnection.setReadTimeout(5000);
                                httpConnection.setRequestMethod("GET");
                                InputStream inputStream = httpConnection.getInputStream();
                                //将inputStream变成byte数组
                               final byte[] fileByte = SDUtil.read(inputStream);
                                //第一，文件的数据流转为的byte数组，2.文件夹名字 3.文件名字

                                CloudspaceActivity.this.runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        img.setImageBitmap(BitmapFactory.decodeByteArray(fileByte,0,fileByte.length));
                                        Toast.makeText(CloudspaceActivity.this, "下载成功", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();

            }
        });
    }
}

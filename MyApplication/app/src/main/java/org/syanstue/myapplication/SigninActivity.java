package org.syanstue.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.URLEncoder;

public class SigninActivity extends AppCompatActivity
{
    EditText nk;
    EditText sun;
    EditText spw0;
    EditText spw1;
    Button   btn;

    User user = new User();
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            if (msg.what==2)
            {//登陆成功
                Toast.makeText(SigninActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        nk   = findViewById(R.id.nickyName);
        sun  = findViewById(R.id.emailAddress);
        spw0 = findViewById(R.id.pw0);
        spw1 = findViewById(R.id.pw1);
        btn  = findViewById(R.id.logupSubmit);

        String reg = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

        final String nickyname = nk.getText().toString();
        final String username = sun.getText().toString();
        final String password = sun.getText().toString();



        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             new Thread
              (
                new Runnable()
                  {
                    @Override
                    public void run()
                    {
                        if ( username == "" || password =="")
                        {
                            handler.sendEmptyMessage(2);
                        }
                        else
                        {
                            try
                            {
                                if (signinPost(nickyname, username, password) == true)
                                {
                                    Intent intent = new Intent(SigninActivity.this, CloudspaceActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Looper.prepare();
                                    Toast.makeText(SigninActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                  }
              ).start();
            }
        });
    }
    public boolean signinPost(String nickyname , String username , String password) throws IOException
    {
        int tmp = 0;
        URL requestURL = new URL("http://114.232.147.16:9090/SigninServlet");
        HttpURLConnection httpURLConnection = (HttpURLConnection)requestURL.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpURLConnection.connect();

        String content = "nickyname" + URLEncoder.encode(nickyname,"UTF-8") + "&username" + URLEncoder.encode(username,"UTF-8") + "&password" + URLEncoder.encode(password,"UTF-8");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(content.getBytes("UTF-8"));
        outputStream.close();

        httpURLConnection.setConnectTimeout(800);
        httpURLConnection.setReadTimeout(800);

        tmp = httpURLConnection.getResponseCode();
        System.out.println("状态码"+tmp);
        httpURLConnection.disconnect();
        return tmp == 200;
    }
}

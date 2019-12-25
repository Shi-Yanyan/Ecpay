package org.syanstue.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{
    EditText un;
    EditText pw;
    Button btn;
    TextView lt;
    CheckBox al;
    CheckBox rm;
    int way_to_login = 0;
    SharedPreferences sharedPreferences;

    User user = new User();

    Intent intent = new Intent();

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            //登陆成功
            if (msg.what==1)
            {
                Toast.makeText(MainActivity.this, "登陆失败0", Toast.LENGTH_LONG).show();
            }
//            else if (msg.what==2)
//            {
//                Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
//            }
        }
    };



    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        un  = findViewById(R.id.email);
        pw  = findViewById(R.id.password);
        btn = findViewById(R.id.button);
        lt  = findViewById(R.id.logup_ent);
        al  = findViewById(R.id.auto_login);
        rm  = findViewById(R.id.remember_password);

        rm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked && un.getText().toString() != null && pw.getText().toString() != null)
                {
                    sharedPreferences =  remember_user_data(un.getText().toString(), un, pw, al, rm);
                    Toast.makeText(MainActivity.this,"记住密码成功！",Toast.LENGTH_LONG).show();
                }
                else if(!isChecked && un.getText().toString() != null && pw.getText().toString() != null)
                {
                    forget_user_data(sharedPreferences);
                    Toast.makeText(MainActivity.this,"忘记密码成功",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(un.getText().toString() != "" && pw.getText().toString() != "")
                    {
                        forget_user_data(sharedPreferences);
                    }
                }
            }
        });

        al.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

               if( al.isChecked() == true && (un.getText().toString() == " " || pw.getText().toString()==" " || rm.isChecked() == false))
                {
                    al.setChecked(false);
                    Toast.makeText(MainActivity.this,"自动登录时，用户名和密码不能为空,且用户必须选中记住密码！",Toast.LENGTH_LONG);
                }
                else if (al.isChecked() == false && un.getText().toString() != null && pw.getText().toString() != null && rm.isChecked() == true)
                {
                    al.setChecked(false);
                    Toast.makeText(MainActivity.this,"取消自动成功！",Toast.LENGTH_LONG).show();
                }
                else if(isChecked && un.getText().toString() != null && pw.getText().toString() != null && rm.isChecked() == true)
                {
                    Toast.makeText(MainActivity.this,"设置自动登录成功！",Toast.LENGTH_LONG).show();
                }

            }
        });

        lt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view2)
            {
                intent.setClass(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });



        switch(way_to_login)
        {
            case 0:
                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            new Thread(
                                    new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            String username = un.getText().toString();
                                            String password = pw.getText().toString();

                                            if ( username == " " || password == " ")
                                            {
                                                handler.sendEmptyMessage(1);
                                            }
                                            else
                                            {
                                                try
                                                {
                                                    if( loginPost(username , password ,2849)==true )
                                                    {

                                                        intent.setClass(MainActivity.this, CloudspaceActivity.class);
                                                        startActivity(intent);
                                                    }
                                                    else
                                                    {
                                                        Looper.prepare();
                                                        Toast.makeText(MainActivity.this,"登录失败1",Toast.LENGTH_LONG).show();
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
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case 1:

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            new Thread(
                                    new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            String username = un.getText().toString();
                                            String password = pw.getText().toString();

                                            if ( username == " " || password == " ")
                                            {
                                                handler.sendEmptyMessage(1);
                                            }
                                            else
                                            {
                                                try
                                                {
                                                    String username_tmp =  read_user_data(sharedPreferences,un.getText().toString(),username);
                                                    String password_tmp =  read_user_data(sharedPreferences,un.getText().toString(),password);
                                                    boolean remember_password_tmp = read_user_data(sharedPreferences,un.getText().toString(),remember_password);
                                                    boolean auto_login_tmp = read_user_data(sharedPreferences,un.getText().toString(),auto_login);



                                                    sharedPreferences = getSharedPreferences(un.getText().toString(),MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                                    if( loginPost(username , password ,2849)==true )
                                                    {

                                                        intent.setClass(MainActivity.this, CloudspaceActivity.class);
                                                        startActivity(intent);
                                                    }
                                                    else
                                                    {
                                                        Looper.prepare();
                                                        Toast.makeText(MainActivity.this,"登录失败1",Toast.LENGTH_LONG).show();
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
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }


    }
    public boolean loginPost( String username , String password , int id) throws IOException
    {
            URL requestURL = new URL("http:114.232.147.16:9090/LoginServlet");
            int tmp = 0;

            HttpURLConnection httpURLConnection = (HttpURLConnection)requestURL.openConnection();

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            httpURLConnection.connect();

//            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
//            dataOutputStream.writeBytes(content);
//            dataOutputStream.flush();
//            dataOutputStream.close();
            String content = "username="+ URLEncoder.encode(username,"UTF8") + "&password=" + URLEncoder.encode(password,"UTF-8") + "&id=" + URLEncoder.encode(String.valueOf(id),"UTF-8");


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(content.getBytes("UTF-8"));
            outputStream.close();

            httpURLConnection.setConnectTimeout(800);
            httpURLConnection.setReadTimeout(800);

            tmp = httpURLConnection.getResponseCode();
            System.out.println("返回状态" + tmp);
            System.out.println(content);
            httpURLConnection.disconnect();
            return tmp == 200;
    }
    public void auto_login(CheckBox checkBox)
    {
        boolean tmp = false;

        tmp = checkBox.isChecked();
        if( tmp = true && un.getText().toString() != "" || pw.getText().toString() != "")
        {

        }
        else
        {

        }
    }
    public SharedPreferences remember_user_data(String un ,EditText editText0 , EditText editText1 , CheckBox checkBox0,CheckBox checkBox1)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(un, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",editText0.getText().toString());
        editor.putString("password",editText1.getText().toString());
        editor.putBoolean("auto_login",al.isChecked());
        editor.putBoolean("remember_password",rm.isChecked());
        editor.commit();
        return sharedPreferences;
    }
    public void forget_user_data(SharedPreferences sharedPreferences)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public String read_user_data(SharedPreferences sharedPreferences , String un , String key)
    {
        SharedPreferences sharedPreferences1 = getSharedPreferences(un,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    public void regex(EditText editText0 , EditText editText1)
    {

    }
}

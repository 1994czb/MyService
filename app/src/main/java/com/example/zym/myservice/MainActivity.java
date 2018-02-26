package com.example.zym.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start_service;
    private Button stop_service;
    private Button bind_service;
    private Button unbind_service;
    private MyService.DownloadBinder downloadBinder;
    //匿名类，用于绑定和解绑服务
    private ServiceConnection connection = new ServiceConnection() {
        //服务成功绑定时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        //服务成功解绑时调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_service = (Button) findViewById(R.id.start_service);
        stop_service = (Button) findViewById(R.id.stop_service);
        bind_service = (Button) findViewById(R.id.bind_service);
        unbind_service = (Button) findViewById(R.id.unbind_service);
        start_service.setOnClickListener(this);
        stop_service.setOnClickListener(this);
        bind_service.setOnClickListener(this);
        unbind_service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                //开启服务
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                //停止服务
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                //绑定服务
                Intent binIntent = new Intent(this, MyService.class);
                //三个参数，第一个：Intent对象；第二个参数：ServiceConnection匿名类的对象
                //第三个参数：BIND_AUTO_CREATE表示：在活动与服务绑定成功后自动创建服务
                bindService(binIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                //解绑服务
                unbindService(connection);
                break;
        }
    }
}

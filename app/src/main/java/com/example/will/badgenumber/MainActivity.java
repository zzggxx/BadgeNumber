package com.example.will.badgenumber;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.will.badgenumber.manager.BadgeNumberManager;
import com.example.will.badgenumber.manager.BadgeNumberManagerXiaoMi;
import com.example.will.badgenumber.manager.MobileBrand;

public class MainActivity extends AppCompatActivity {

    private TextView ivMobileBrand;
    private Button btnSetBadge;
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BadgeNumberManager.from(this).setBadgeNumber(5);

        ivMobileBrand = (TextView) findViewById(R.id.iv_mobile_brand);
        btnSetBadge = (Button) findViewById(R.id.btn_set_badge);
        btnClear = (Button) findViewById(R.id.btn_clear);
        ivMobileBrand.setText("手机品牌：" + Build.MANUFACTURER);
        btnSetBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置应用在桌面上显示的角标
                if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
                    BadgeNumberManager.from(MainActivity.this).setBadgeNumber(10);
                    Toast.makeText(MainActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
                } else {
                    setXiaomiBadgeNumber();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置应用在桌面上显示的角标,小米机型只要用户点击了应用图标进入应用，会自动清除掉角标
                if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
                    BadgeNumberManager.from(MainActivity.this).setBadgeNumber(0);
                    Toast.makeText(MainActivity.this, "清除桌面角标成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setXiaomiBadgeNumber() {
        NotificationManager notificationManager = (NotificationManager) MainActivity.this.
                getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(MainActivity.this.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("推送标题")
                .setContentText("我是推送内容")
                .setTicker("ticker")
                .setAutoCancel(true)
                .build();
        BadgeNumberManagerXiaoMi.setBadgeNumber(notification, 10);
        notificationManager.notify(1000, notification);
        Toast.makeText(MainActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();

    }


}

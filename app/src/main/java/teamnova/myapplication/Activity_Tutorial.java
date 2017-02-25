package teamnova.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import teamnova.myapplication.woongbi.activity_main;

public class Activity_Tutorial extends AppCompatActivity {

    int MAX_PAGE = 3;
    Fragment cur_fragment = new Fragment();

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        startActivity(new Intent(this, Activity_Splash.class));

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

            int permissionResult = checkSelfPermission(Manifest.permission.CAMERA);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);

                }

                else {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);

                }

            }

        }

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));

        TextView button_login_facebook = (TextView) findViewById(R.id.button_login_facebook);
        button_login_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(Activity_Tutorial.this, "", "로그인 중입니다.", true);
                dialog.show();

                EndDialog endDialog = new EndDialog();
                endDialog.start();

            }
        });

        TextView button_login_google = (TextView) findViewById(R.id.button_login_google);
        button_login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(Activity_Tutorial.this, "", "로그인 중입니다.", true);
                dialog.show();

                EndDialog endDialog = new EndDialog();
                endDialog.start();

            }
        });

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+ viewPager.getAdapter().getItemPosition(viewPager.getCurrentItem()));

    }

    private class adapter extends FragmentPagerAdapter {
        public adapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if(position<0 || MAX_PAGE<=position)
                return null;
            switch (position){
                case 0:
                    cur_fragment = new Viewpager_Page1();
                    break;
                case 1:
                    cur_fragment = new Viewpager_Page2();
                    break;
                case 2:
                    cur_fragment = new Viewpager_Page3();
                    break;
            }
            return cur_fragment;
        }
        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }

    private Handler DialogHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            dialog.dismiss();
            startActivity(new Intent(getApplicationContext(), activity_main.class));
//            startActivity(intent);
        }
    };

    private class EndDialog extends Thread {
        public void run(){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DialogHandler.sendEmptyMessage(0);
        }
    }
}

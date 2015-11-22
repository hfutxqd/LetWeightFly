package cf.imxqd.LetWeightFly.Activities;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cf.imxqd.LetWeightFly.DataOp.DataBase;
import cf.imxqd.LetWeightFly.DataOp.UserInfoOp;
import cf.imxqd.LetWeightFly.Fragments.TodayTaskFragment;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Fragments.RecordFragment;
import cf.imxqd.LetWeightFly.Fragments.TipsFragment;
import cf.imxqd.LetWeightFly.Services.StepService;
import cf.imxqd.LetWeightFly.Utils.Tool;
import cf.imxqd.LetWeightFly.Views.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public DataBase database;
    public static File appdir;
    public View toolbar_shadow;
    public static File datadir;

    NavigationView navView;
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    FrameLayout container;
    TextView nav_weight_c;
    TextView nav_weight_r;
    TextView user_name;
    CircleImageView user_image;
    Fragment todayTaskFragment;
    Fragment recordFragment;
    Fragment tipsFragment;
    UserInfoOp userInfoOp;
    boolean configed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
        initServices();
    }

    @Override
    public void onResume()
    {
        initviewData();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        userInfoOp.setNull();
        toolbar.removeAllViews();
        toolbar.destroyDrawingCache();
        toolbar_shadow.destroyDrawingCache();
        user_image.destroyDrawingCache();
        user_name.destroyDrawingCache();
        nav_weight_c.destroyDrawingCache();
        nav_weight_r.destroyDrawingCache();
        navView.destroyDrawingCache();
        drawer.removeAllViews();
        drawer.destroyDrawingCache();
        container.removeAllViews();
        container.destroyDrawingCache();

        database = null;
        userInfoOp = null;
        toolbar = null;
        user_image = null;
        user_name = null;
        nav_weight_c = null;
        nav_weight_r = null;
        navView = null;
        drawer = null;
        container = null;
        todayTaskFragment = null;
        recordFragment = null;
        tipsFragment = null;
        System.gc();

    }

    private void initServices()
    {

        startService(new Intent(this, StepService.class));
    }

    private void initData()
    {
        database = new DataBase(getApplicationContext());
        userInfoOp = new UserInfoOp(getApplicationContext());
        appdir = new File(Environment.getExternalStorageDirectory() + getString(R.string.appdir));
        if (!appdir.exists()) {
            if(appdir.mkdir())
            {
                System.out.println(appdir + " created successfully.");
            }else{
                System.out.println(appdir + " create failed.");
            }
        }
        datadir = new File(Environment.getDataDirectory()+getString(R.string.datadir));
        if (!datadir.exists()) {
            if(datadir.mkdir())
            {
                System.out.println(datadir + " created successfully.");
            }else{
                System.out.println(datadir + " create failed.");
            }
        }
        if (!userInfoOp.hasUser())
        {
            configed = false;
            handleUserConfig();
        }else {
            configed = true;
        }
    }

    public void setShadow(int mode)
    {
        toolbar_shadow.setVisibility(mode);
    }

    private void initViews()
    {
        Tool.setNavBarColor(this,getResources().getColor(R.color.nav_background));
        navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(this);

        user_image = (CircleImageView) navView.getHeaderView(0).findViewById(R.id.nav_image);
        user_image.setOnClickListener(this);
        File tmp = new File(datadir+"/user.jpg");
        if (tmp.exists())
        {
            Drawable drawable = Drawable.createFromPath(tmp.toString());
            user_image.setImageDrawable(drawable);
        }else{
            user_image.setImageResource(R.drawable.user);
        }
        user_name = (TextView) navView.getHeaderView(0).findViewById(R.id.name);
        nav_weight_c = (TextView) navView.getHeaderView(0).findViewById(R.id.weight_current);
        nav_weight_r = (TextView) navView.getHeaderView(0).findViewById(R.id.weight_reduced);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        container = (FrameLayout) findViewById(R.id.container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_shadow = findViewById(R.id.toolbar_shadow);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);
        mTitle = getString(R.string.title_task);
        todayTaskFragment = new TodayTaskFragment();
        recordFragment = new RecordFragment();
        tipsFragment = new TipsFragment();
        changeCurrFragment(todayTaskFragment);
        setTitle(getString(R.string.title_task));
    }

    private void initviewData()
    {
        if(configed)
        {
            UserInfoOp.User user= userInfoOp.getUser();
            user_name.setText(user.name);
            nav_weight_c.setText(getString(R.string.weight_current)
                    +database.getCurrentWeight()+"kg");
            nav_weight_r.setText(getString(R.string.weight_reduced)
                    + (user.weight - database.getCurrentWeight()) + "kg");
        }
    }


    private void handleUserConfig()
    {
        Intent intent = new Intent();
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }

    }

    private void changeCurrFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                Intent intent = new Intent();
                intent.setClass(this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_in,R.anim.translate_out);
                break;
            case R.id.action_mylib:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawers();
        }else {
            exitBy2Click();
        }
    }

    private boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this,
                    getString(R.string.exit_by_2_click), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId())
        {
            case R.id.drawer_today_task:
                changeCurrFragment(todayTaskFragment);
                mTitle = getString(R.string.title_task);
                drawer.closeDrawers();
                break;
            case R.id.drawer_history:
                changeCurrFragment(recordFragment);
                mTitle = getString(R.string.title_history);
                drawer.closeDrawers();
                break;
            case R.id.drawer_tips:
                changeCurrFragment(tipsFragment);
                mTitle = getString(R.string.title_tips);
                drawer.closeDrawers();
                break;
            case R.id.drawer_settings:
                Intent intent = new Intent();
                intent.setClass(this,SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_finsh_in,R.anim.no_anim);
                break;
        }
        getSupportActionBar().setTitle(mTitle);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.nav_image:
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            try {
                Bitmap bitmap = Tool.getResizedBMP(uri, getApplicationContext());
                File file_user = new File(datadir+"/user.jpg");
                Tool.save2jpg(bitmap, file_user);
                bitmap.recycle();
                Drawable drawable = Drawable.createFromPath(file_user.toString());
                user_image.setImageDrawable(drawable);
            } catch (FileNotFoundException e) {
                Toast.makeText(this,"错误：文件不存在！",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this,"错误：文件读写异常！",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


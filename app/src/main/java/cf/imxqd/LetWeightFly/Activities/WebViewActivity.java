package cf.imxqd.LetWeightFly.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cf.imxqd.LetWeightFly.DataOp.DataBase;
import cf.imxqd.LetWeightFly.Fragments.WebFragment;
import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Share;
import cf.imxqd.LetWeightFly.Views.LoadingView;

public class WebViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    public LoadingView loadingView;
    WebFragment fragment;
    int Mode;
    String title;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadingView = (LoadingView) findViewById(R.id.loadingView);
        loadingView.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        url = getIntent().getStringExtra("Url");
        title = getIntent().getStringExtra("title");
        Mode = getIntent().getIntExtra("Mode",0);
        fragment = WebFragment.newInstance(url, Mode);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onDestroy() {
        toolbar.removeAllViews();
        toolbar = null;
        loadingView.removeAllViews();
        loadingView = null;
        fragment = null;
        System.gc();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Mode == WebFragment.MODE_SEARCH)
        {
            getMenuInflater().inflate(R.menu.menu_web_view_search, menu);
        }else if(Mode == WebFragment.MODE_TIPS)
        {
            getMenuInflater().inflate(R.menu.menu_web_view_tips, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }else if(id == R.id.action_share)
        {
            Share.shareUrl(this,title,url);
            return true;
        }else if(id == R.id.add_to_task)
        {
            // TODO: 2015/7/28  
            Toast.makeText(this, "添加到 功能尚未开放！", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.add_to_lib)
        {
            float calory = getIntent().getFloatExtra("calory",0);
            DataBase db = new DataBase(this);
            if(db.canAddToLib(title)) {
                if(calory < 0)
                {
                    db.addToSportLib(title,calory);
                }else if(calory > 0)
                {
                    db.addToFoodLib(title,calory);
                }
                Toast.makeText(this,"添加成功！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"你已经添加过了",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

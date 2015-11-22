package cf.imxqd.LetWeightFly.Activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cf.imxqd.LetWeightFly.Fragments.SettingsFragment;
import cf.imxqd.LetWeightFly.R;

/**
 * Created by Henry on 2015/6/23.
 *
 */
public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.nav_settings);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        goBack();
    }

    private void goBack()
    {
        finish();
        overridePendingTransition(R.anim.no_anim,R.anim.translate_finsh_out);
    }



}


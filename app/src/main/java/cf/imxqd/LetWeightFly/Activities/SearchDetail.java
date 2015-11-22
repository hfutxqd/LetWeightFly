package cf.imxqd.LetWeightFly.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cf.imxqd.LetWeightFly.Fragments.FoodFragment;
import cf.imxqd.LetWeightFly.R;

public class SearchDetail extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new FoodFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}

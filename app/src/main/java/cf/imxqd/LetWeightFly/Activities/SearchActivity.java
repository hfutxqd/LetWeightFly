package cf.imxqd.LetWeightFly.Activities;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import cf.imxqd.LetWeightFly.Adapter.SearchAdapter;
import cf.imxqd.LetWeightFly.Fragments.WebFragment;
import cf.imxqd.LetWeightFly.Model.Web.SearchRes;
import cf.imxqd.LetWeightFly.R;


public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
        ,AdapterView.OnItemClickListener{
    Toolbar toolbar;
    SearchView searchView;
    FrameLayout container;
    ListView listView;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    @Override
    protected void onDestroy() {
        adapter.recycle();
        System.gc();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.onActionViewExpanded();
    }

    private void initView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.search_bar);
        container = (FrameLayout) findViewById(R.id.container);
        listView = (ListView) findViewById(R.id.list);
        adapter = new SearchAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        searchView.setOnQueryTextListener(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.main_in, R.anim.search_out);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(!query.isEmpty())
        {
            adapter.search(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.isEmpty())
        {
            adapter.search(newText);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchRes item = (SearchRes) adapter.getItem(position);
        System.out.println(item.calory);
        Intent intent = new Intent();
        intent.putExtra("Url", item.Url);
        intent.putExtra("title", item.title);
        intent.putExtra("calory", item.calory);
        intent.putExtra("Mode", WebFragment.MODE_SEARCH);
        intent.setClass(this, WebViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }
}

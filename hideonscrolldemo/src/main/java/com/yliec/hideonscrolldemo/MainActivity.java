package com.yliec.hideonscrolldemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar = null;
    private RecyclerView recyclerView;
    private ImageButton ib;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        items = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            items.add("item" + i);
        }
    }

    private void initView() {
        initToolbar();
        ib = (ImageButton) findViewById(R.id.ib_add);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isVisible = true;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    if (!isVisible) {
                        show();
                    }
                } else {
                    if (dy > 20 && isVisible) {
                        hide();
                    } else if (dy < -20 && !isVisible) {
                        show();
                    }
                }
                Log.d("onScrolled", String.format("dx : %s, dy : %s, ibBottom : %s", dx, dy, ib.getBottom() - ib.getTop()));

            }

            private void show() {
                isVisible = true;
                toolbar.animate().translationY(0).setDuration(300).setInterpolator(new AccelerateInterpolator(1)).start();
                int trans = ib.getBottom() - ib.getTop();
                ib.animate().translationY(0).setDuration(300).setInterpolator(new AccelerateInterpolator(1)).start();
            }

            private void hide() {
                isVisible = false;
                toolbar.animate().translationY(-toolbar.getHeight()).setDuration(300).setInterpolator(new AccelerateInterpolator(1)).start();
                int trans = ib.getBottom() - ib.getTop();

                ib.animate().translationY(trans).setDuration(300).setInterpolator(new AccelerateInterpolator(1)).start();
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("测试");
        toolbar.setSubtitle("subTitle");
        toolbar.setNavigationIcon(R.drawable.abc_ic_menu_selectall_mtrl_alpha);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter {

        private final static int TYPE_ITEM = 1;
        private final static int TYPE_HEADER = 2;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
                return new ViewHolder(v);
            } else if (viewType == TYPE_HEADER) {
                View v = LayoutInflater.from(context).inflate(R.layout.layout_header, parent, false);
                return new HeaderViewHolder(v);
            }
            throw new RuntimeException("Unknow Type: " + viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != 0) {
                ((ViewHolder)holder).setData(items.get(position - 1));
            }
        }


        @Override
        public int getItemCount() {
            return getBasicCount() + 1;
        }

        private int getBasicCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        public final class ViewHolder extends RecyclerView.ViewHolder {
            private View itemView;
            private TextView tvTitle;
            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            }

            void setData(String data) {
                tvTitle.setText(data);
            }

        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}

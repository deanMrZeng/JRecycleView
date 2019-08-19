package com.zinc.jrecycleview.stick.header;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.zinc.jrecycleview.JRecycleView;
import com.zinc.jrecycleview.R;
import com.zinc.jrecycleview.adapter.JRefreshAndLoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author       : zinc
 * time         : 2019-07-26 14:09
 * desc         : 粘性头
 * version      : 1.0.0
 */
public class StickHeaderActivity extends AppCompatActivity {

    private static final float OFFSET = 600;
    private static final int PAGE_SIZE = 20;

    private final List<String> mData = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private JRecycleView mJRecycleView;
    private TextView mTextView;

    private JRefreshAndLoadMoreAdapter mAdapter;

    private float mRvOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);

        mJRecycleView = findViewById(R.id.j_recycle_view);
        mTextView = findViewById(R.id.tv_header);

        mTextView.setAlpha(0);
        mTextView.setText("Zinc");

        getInitData();

        RecyclerView.Adapter adapter = new StickHeaderAdapter(this, mData);
        this.mAdapter = new JRefreshAndLoadMoreAdapter(this, adapter);

        this.mAdapter.setIsOpenRefresh(false);

        this.mAdapter.setOnLoadMoreListener(() -> {
            Toast.makeText(StickHeaderActivity.this,
                    "触发加载更多",
                    Toast.LENGTH_SHORT)
                    .show();

            mHandler.postDelayed(() -> {
                if (mData.size() > 2 * PAGE_SIZE) {
                    mAdapter.setLoadError();
                } else {
                    int size = mData.size();
                    addData();
                    mAdapter.setLoadComplete();
                    mAdapter.notifyItemRangeInserted(size, PAGE_SIZE);
                }
            }, 2000);
        });

        mJRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                mRvOffset += dy;
                float percent = mRvOffset / OFFSET;

                if (percent >= 1) {
                    percent = 1;
                }

                Log.i("test", "onScrolled: [x: " + dx + ";" +
                        " y:" + dy + ";" +
                        " mRvOffset:" + mRvOffset + ";" +
                        " percent:" + percent + "]");

                mTextView.setAlpha(percent);
            }
        });

        mJRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mJRecycleView.setAdapter(mAdapter);

    }

    public void getInitData() {
        this.mData.clear();
        for (int i = 1; i <= PAGE_SIZE; ++i) {
            mData.add("zinc Power" + i);
        }
    }

    public void addData() {
        int size = mData.size();
        for (int i = 1; i <= PAGE_SIZE; ++i) {
            mData.add("zinc Power" + (i + size));
        }
    }

}

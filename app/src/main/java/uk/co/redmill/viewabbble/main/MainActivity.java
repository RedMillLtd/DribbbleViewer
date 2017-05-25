package uk.co.redmill.viewabbble.main;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import uk.co.redmill.viewabbble.DribbbleApp;
import uk.co.redmill.viewabbble.R;
import uk.co.redmill.viewabbble.api.models.Shots;
import uk.co.redmill.viewabbble.data.source.ShotsDataSource;
import uk.co.redmill.viewabbble.data.source.ShotsRepository;
import uk.co.redmill.viewabbble.utils.ImageSize;
import uk.co.redmill.viewabbble.utils.Injection;

public class MainActivity extends AppCompatActivity {

    private ShotsAdapter mAdapter;
    private RecyclerView lvShots;
    ShotsRepository mShotsRepository;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    populateList(1);
                    return true;
                case R.id.navigation_dashboard:
                    populateList(2);
                    return true;
                case R.id.navigation_notifications:
                    populateList(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvShots = (RecyclerView) findViewById(R.id.shotList);
        lvShots.setLayoutManager(new LinearLayoutManager(this));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mShotsRepository = Injection.provideTasksRepository(getApplicationContext());
        populateList(0);
    }

    private void populateList(int page) {
        mShotsRepository.getListShotsByPage(page, 0, new ShotsDataSource.LoadListShotsCallback() {
            @Override
            public void onListShotsLoaded(List<Shots> shotsList) {
                mAdapter = new ShotsAdapter(shotsList);
                lvShots.setAdapter(mAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }


    public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.ViewHolder> {
        private List<Shots> mDataset;
        private int lastPosition = -1;

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView photo;
            public TextView title;
            public TextView description;

            public ViewHolder(View itemView) {
                super(itemView);
                photo = (ImageView) itemView.findViewById(R.id.image);
                title = (TextView) itemView.findViewById(R.id.title);
                description = (TextView) itemView.findViewById(R.id.description);
            }
        }

        public ShotsAdapter(List<Shots> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ShotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shot_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Shots shotItem = mDataset.get(position);
            Context context = holder.photo.getContext();
            holder.title.setText(shotItem.getTitle());
            holder.description.setText(Html.fromHtml(shotItem.getDescription() == null ? "" : shotItem.getDescription()));
            Glide.with(context)
                    .load(shotItem.getImages().getNormal())
                    .into(holder.photo);
            setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(DribbbleApp.getmContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}

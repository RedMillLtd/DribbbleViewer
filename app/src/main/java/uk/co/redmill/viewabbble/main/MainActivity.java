package uk.co.redmill.viewabbble.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Objects;

import uk.co.redmill.viewabbble.DribbbleApp;
import uk.co.redmill.viewabbble.R;
import uk.co.redmill.viewabbble.api.models.Shots;
import uk.co.redmill.viewabbble.data.source.ShotsDataSource;
import uk.co.redmill.viewabbble.data.source.ShotsRepository;
import uk.co.redmill.viewabbble.utils.Injection;

public class MainActivity extends AppCompatActivity {

    private ShotsAdapter mAdapter;
    private RecyclerView lvShots;
    private int lastPosition;

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
        lvShots = findViewById(R.id.shotList);
        lvShots.setLayoutManager(new LinearLayoutManager(this));
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mShotsRepository = Injection.provideTasksRepository(getApplicationContext());
        populateList(1);
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


    class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.ViewHolder> {
        private List<Shots> mDataset;

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout textLayout;
            ImageView photo;
            TextView title;
            TextView description;

            ViewHolder(View itemView) {
                super(itemView);
                textLayout = itemView.findViewById(R.id.relTextLayout);
                photo = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
                description = itemView.findViewById(R.id.description);
            }
        }

        ShotsAdapter(List<Shots> myDataset) {
            mDataset = myDataset;
        }

        @NonNull
        @Override
        public ShotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                          int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shot_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Shots shotItem = mDataset.get(position);
            final Context context = holder.photo.getContext();
            holder.title.setText(shotItem.getTitle());
            holder.description.setText(Html.fromHtml(shotItem.getDescription() == null ? "" : shotItem.getDescription()));
            String url = Objects.requireNonNull(shotItem.getImages()).getNormal();

            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Palette p = createPaletteSync(resource);
                            Palette.Swatch vibrantSwatch = checkVibrantSwatch(p);
                            Palette.Swatch textSwatch = checkTextSwatch(p);

                            if (textSwatch != null) {
                                holder.title.setTextColor(textSwatch.getRgb());
                                holder.description.setTextColor(textSwatch.getRgb());
                            }
                            if (vibrantSwatch != null) {
                                holder.textLayout.setBackgroundColor(vibrantSwatch.getRgb());
                                holder.title.setBackgroundColor(vibrantSwatch.getRgb());
                            }
                            return false;
                        }
                    })
                    .into(holder.photo);

            setAnimation(holder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private Palette.Swatch checkVibrantSwatch(Palette p) {
        Palette.Swatch muted = p.getLightMutedSwatch();
        if (muted != null) {
            return muted;
        }
        return null;
    }

    private Palette.Swatch checkTextSwatch(Palette p) {
        Palette.Swatch darkVibrant = p.getDarkVibrantSwatch();
        if (darkVibrant != null) {
            return darkVibrant;
        }
        return null;
    }

    public Palette createPaletteSync(Bitmap bitmap) {
        return Palette.from(bitmap).generate();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(DribbbleApp.getmContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

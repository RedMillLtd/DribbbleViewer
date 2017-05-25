package uk.co.redmill.viewabbble.data.source.local;

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import uk.co.redmill.viewabbble.api.models.Shots;
import uk.co.redmill.viewabbble.data.source.ShotsDataSource;

public class ShotsLocalDataSource implements ShotsDataSource {

    private static ShotsLocalDataSource INSTANCE;

    private Context mContext;

    private ShotsLocalDataSource(Context context) {
        this.mContext = context;
    }

    @Override
    public void getListShots(int filterId, @NonNull LoadListShotsCallback callback) {
        callback.onDataNotAvailable();
    }

    @Override
    public void getListShotsByPage(int page, int filterId, @NonNull LoadListShotsCallback callback) {

    }

    @Override
    public void deleteAllShots() {

    }

    @Override
    public void refreshShots() {

    }

    @Override
    public void getShots(@NonNull int shotsId, @NonNull GetShotsCallback callback) {

    }

    @Override
    public void saveListShots(List<Shots> shotsList) {

    }

    public static ShotsDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ShotsLocalDataSource(context);
        }
        return INSTANCE;
    }
}

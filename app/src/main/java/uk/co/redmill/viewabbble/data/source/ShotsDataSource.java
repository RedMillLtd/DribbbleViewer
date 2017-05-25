package uk.co.redmill.viewabbble.data.source;

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.support.annotation.NonNull;

import java.util.List;

import uk.co.redmill.viewabbble.api.models.Shots;

public interface ShotsDataSource {

    interface LoadListShotsCallback {

        void onListShotsLoaded(List<Shots> shotsList);

        void onDataNotAvailable();

    }

    interface GetShotsCallback {
        void onShotsLoaded(Shots shots);

        void onDataNotAvailable();
    }

    void getListShots(int filterId, @NonNull LoadListShotsCallback callback);

    void getListShotsByPage(int page, int filterId, @NonNull LoadListShotsCallback callback);

    void deleteAllShots();

    void refreshShots();

    void getShots(@NonNull int shotsId, @NonNull GetShotsCallback callback);

    void saveListShots(List<Shots> shotsList);

}
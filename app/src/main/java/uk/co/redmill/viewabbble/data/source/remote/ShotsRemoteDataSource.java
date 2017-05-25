package uk.co.redmill.viewabbble.data.source.remote;

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.redmill.viewabbble.api.DribbbleService;
import uk.co.redmill.viewabbble.api.RetrofitClient;
import uk.co.redmill.viewabbble.api.models.Shots;
import uk.co.redmill.viewabbble.data.source.ShotsDataSource;

public class ShotsRemoteDataSource implements ShotsDataSource {

    public static final int FILTER_POPULAR = 0;

    public static final int FILTER_RECENT = 1;

    public static final int FILTER_DEBUTS = 2;

    DribbbleService mService;

    private static ShotsDataSource INSTANCE;

    private ShotsRemoteDataSource() {
        mService = RetrofitClient.getInstance().getDRService();
    }

    public static ShotsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShotsRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getListShots(int filterId, @NonNull final LoadListShotsCallback callback) {
        Map<String, String> map = new HashMap<>();
        switch (filterId) {
            case FILTER_POPULAR:
                map.put("sort", "popular");
                break;
            case FILTER_RECENT:
                map.put("sort", "recent");
                break;
            case FILTER_DEBUTS:
                map.put("list", "debuts");
                break;
        }
        mService.getShotsList(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Shots>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<Shots> value) {
                        callback.onListShotsLoaded(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getListShotsByPage(int page, int filterId, @NonNull final LoadListShotsCallback callback) {
        Map<String, String> map = new HashMap<>();
        switch (filterId) {
            case 0:
                map.put("sort", "popular");
                break;
            case 1:
                map.put("sort", "recent");
                break;
            case 2:
                map.put("list", "debuts");
                break;
        }
        map.put("page", String.valueOf(page));
        map.put("per_page", "12");
        mService.getShotsList(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Shots>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Shots> shotsList) {
                        callback.onListShotsLoaded(shotsList);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void deleteAllShots() {

    }

    @Override
    public void refreshShots() {

    }

    @Override
    public void getShots(@NonNull int shotsId, @NonNull final GetShotsCallback callback) {
        mService.getShots(shotsId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Shots>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(Shots shots) {
                        callback.onShotsLoaded(shots);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void saveListShots(List<Shots> shotsList) {

    }
}
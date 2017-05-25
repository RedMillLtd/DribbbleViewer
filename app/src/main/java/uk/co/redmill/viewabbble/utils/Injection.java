package uk.co.redmill.viewabbble.utils;

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;

import uk.co.redmill.viewabbble.data.source.ShotsRepository;
import uk.co.redmill.viewabbble.data.source.local.ShotsLocalDataSource;
import uk.co.redmill.viewabbble.data.source.remote.ShotsRemoteDataSource;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

public class Injection {
    public static ShotsRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return ShotsRepository.getInstance(ShotsLocalDataSource.getInstance(context), ShotsRemoteDataSource.getInstance());
    }

    public static String provideTokenValue() {
        return SharedPrefManager.getAccessToken();
    }

}

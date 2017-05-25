package uk.co.redmill.viewabbble.login

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.text.TextUtils
import android.util.Log
import com.sangupta.dribbble.api.model.Shot
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import org.reactivestreams.Subscriber
import uk.co.redmill.viewabbble.BuildConfig
import uk.co.redmill.viewabbble.api.RetrofitClient
import uk.co.redmill.viewabbble.api.models.DribbbleToken
import uk.co.redmill.viewabbble.api.models.Shots
import uk.co.redmill.viewabbble.main.MainContract
import uk.co.redmill.viewabbble.utils.SharedPrefManager

class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    override fun loadList() {

//        RetrofitClient
//                .getInstance()
//                .drService
//                .shotsList
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : SingleSubject<Shots>() {
//                    override fun onSuccess(user: Shots?) {
//                        mView.updateUserProfile(user)
//                    }
//
//                    override fun onError(error: Throwable?) {
//
//                    }
//                })
    }
//
//    val emitterUngated = RetrofitClient.getInstance()
//            .drService
//            .shotsList
//            .map(Single<Shots> { getShots() })
//            .subscribeOn(Schedulers.io())
//
//    fun getShots(): Observable<List<Shots>> {
//        return Observable.create {
//            subscriber ->
//            val shots = mutableListOf<Shots>()
//            subscriber.onNext(shots)
//            subscriber.onComplete()
//        }
//    }
}
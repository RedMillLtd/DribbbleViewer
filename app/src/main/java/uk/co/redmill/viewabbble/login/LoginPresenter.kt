package uk.co.redmill.viewabbble.login

/**
 * Created by dave @ RedMill Ltd on 5/22/17.
 */

import android.text.TextUtils
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.redmill.viewabbble.BuildConfig
import uk.co.redmill.viewabbble.api.RetrofitClient
import uk.co.redmill.viewabbble.api.models.DribbbleToken
import uk.co.redmill.viewabbble.utils.SharedPrefManager

class LoginPresenter(private val mView: LoginContract.View) : LoginContract.Presenter {

    override fun loadToken(code: String) {
        RetrofitClient.getInstance()
                .drService
                .getDrToken(BuildConfig.CLIENT_ID,
                        BuildConfig.CLIENT_SECRET,
                        code,
                        BuildConfig.REDIRECT_URI)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : SingleObserver<DribbbleToken> {
                    override fun onSubscribe(@NonNull d: Disposable) {}

                    override fun onSuccess(dribbbleToken: DribbbleToken) {
                        val access_token = dribbbleToken.access_token
                        if (TextUtils.isEmpty(access_token)) {
                            mView.loginFailure()
                            return
                        }
                        SharedPrefManager.putAccessToken(access_token)
                        SharedPrefManager.putTokenType(dribbbleToken.token_type)
                        SharedPrefManager.putScope(dribbbleToken.scope)
                        mView.loginSuccess()
                    }

                    override fun onError(error: Throwable) {
                        mView.loginFailure()
                    }
                })
    }
}
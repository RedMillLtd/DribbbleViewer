package uk.co.redmill.viewabbble.login;

/**
 * Created by dave on 5/22/17.
 */

public interface LoginContract {

    interface View{
        void loginSuccess();

        void loginFailure();
    }

    interface Presenter{

        void loadToken(String code);

    }
}
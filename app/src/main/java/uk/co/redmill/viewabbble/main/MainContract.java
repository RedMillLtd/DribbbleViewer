package uk.co.redmill.viewabbble.main;

import uk.co.redmill.viewabbble.api.models.Shots;

/**
 * Created by dave on 5/22/17.
 */

public interface MainContract {

    interface View{
        void updateList(Shots shots);
        void listFailure();
    }

    interface Presenter{

        void loadList();

    }
}
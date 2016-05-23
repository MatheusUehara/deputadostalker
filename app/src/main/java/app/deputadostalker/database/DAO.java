package app.deputadostalker.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Matheus Uehara on 22/05/2016.
 */
public abstract class DAO {

    private Context context = null;

    public static void getDataBaseHelper(Context ctx) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(ctx)
                .name("deputado-stalker.realm")
                .build();
        Realm.setDefaultConfiguration( realmConfiguration );
    }


    public void setContextUp(Context context) {
        setContext(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}

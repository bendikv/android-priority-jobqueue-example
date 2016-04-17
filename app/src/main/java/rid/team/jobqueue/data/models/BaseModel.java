package rid.team.jobqueue.data.models;

import android.database.sqlite.SQLiteDatabase;

import rid.team.jobqueue.App;
import rid.team.jobqueue.di.component.AppComponent;

public class BaseModel {

    protected final SQLiteDatabase mSQLiteDatabase;
    protected final AppComponent mComponent;

    public BaseModel(App app, SQLiteDatabase database) {
        mComponent = app.getAppComponent();
        mSQLiteDatabase = database;
    }
}

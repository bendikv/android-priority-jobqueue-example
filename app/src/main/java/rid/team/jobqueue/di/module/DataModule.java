package rid.team.jobqueue.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rid.team.jobqueue.App;
import rid.team.jobqueue.data.AppDatabase;
import rid.team.jobqueue.data.models.NewsModel;

/**
 * Created by bendik on 17.03.16.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    public NewsModel newsModel(Context context, SQLiteDatabase database) {
        return new NewsModel((App) context, database);
    }

    @Provides
    @Singleton
    public SQLiteDatabase database() {
        return FlowManager.getDatabase(AppDatabase.NAME).getWritableDatabase();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("news", Context.MODE_PRIVATE);
    }

}

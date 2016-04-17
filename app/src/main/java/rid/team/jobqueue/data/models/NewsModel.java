package rid.team.jobqueue.data.models;

import android.database.sqlite.SQLiteDatabase;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import rid.team.jobqueue.App;
import rid.team.jobqueue.data.validation.ValidationUtil;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.data.vo.News$Table;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsModel extends BaseModel{

    public NewsModel(App app, SQLiteDatabase database) {
        super(app, database);
    }

    public News load(long id) {
        return new Select().from(News.class)
                .where(Condition.column(News$Table.ID).eq(id)).querySingle();
    }

    public List<News> loadAll() {
        return new Select().from(News.class).queryList();
    }

    public synchronized void save(News news) {
        ValidationUtil.validate(news);
        saveValid(news);
    }

    public synchronized void saveAll(final List<News> newsList) {
        ValidationUtil.pruneInvalid(newsList);
        if (newsList.isEmpty()) {
            return;
        }
        TransactionManager.transact(mSQLiteDatabase, new Runnable() {
            @Override
            public void run() {
                for (News news : newsList) {
                    saveValid(news);
                }
            }
        });
    }

    private void saveValid(News news) {
        News existing = load(news.getId());
        if (existing == null) {
            news.save();
        } else {
            news.setId(existing.getId());
            if (news.getContent()==null) {
                news.setContent(existing.getContent());
            }
            news.update();
        }
    }

    public void delete(News news) {
        news.delete();
    }

    public void updateContent(long id, String content) {
        News existing = load(id);
        if (existing != null) {
            existing.setContent(content);
            existing.update();
        }
    }
}

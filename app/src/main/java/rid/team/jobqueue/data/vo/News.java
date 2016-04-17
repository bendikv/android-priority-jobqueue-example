package rid.team.jobqueue.data.vo;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import rid.team.jobqueue.data.AppDatabase;
import rid.team.jobqueue.data.validation.Validation;
import rid.team.jobqueue.data.validation.ValidationFailedException;

@Table(databaseName = AppDatabase.NAME)
public class News extends BaseModel implements Validation, Serializable {
    @Column
    @PrimaryKey(autoincrement = false)
    @Expose
    long id;

    @Column
    @Expose
    String name;

    @Column
    @Expose
    String text;

    @Column
    @Expose
    PublicationDate publicationDate;

    @Column
    String content;

    @Column
    @Expose
    long bankInfoTypeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public long getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(long bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }

    public void validate() {
            if (id <= 0) {
                throw new ValidationFailedException("invalid news id");
            }
            if (publicationDate == null || publicationDate.getMilliseconds() < 0) {
                throw new ValidationFailedException("invalid publication date");
            }
            if (TextUtils.isEmpty(name)) {
                throw new ValidationFailedException("invalid name");
            }
            if (TextUtils.isEmpty(text)) {
                throw new ValidationFailedException("invalid text");
            }
    }
}

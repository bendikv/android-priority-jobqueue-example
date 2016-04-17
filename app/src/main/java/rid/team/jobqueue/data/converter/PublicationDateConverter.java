package rid.team.jobqueue.data.converter;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import rid.team.jobqueue.data.vo.PublicationDate;

/**
 * Created by bendik on 17.03.16.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class PublicationDateConverter extends TypeConverter<Long, PublicationDate> {

    @Override
    public Long getDBValue(PublicationDate model) {
        return model == null ? null : model.getMilliseconds();
    }

    @Override
    public PublicationDate getModelValue(Long data) {
        PublicationDate date = new PublicationDate(data);
        return date;
    }
}
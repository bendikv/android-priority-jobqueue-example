package rid.team.jobqueue.api;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import rid.team.jobqueue.jobqueue.NetworkException;

/**
 * Created by bendik on 17.03.16.
 */
public class RetrofitErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError error) {
        return new NetworkException(error.getResponse().getStatus());
    }
}

package rid.team.jobqueue.jobqueue;

import android.support.annotation.IntDef;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.path.android.jobqueue.RetryConstraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rid.team.jobqueue.di.component.AppComponent;
import timber.log.Timber;

abstract public class BaseJob extends Job {
    private static final int RETRY_LIMIT = 3;

    public static final int UI_HIGH = 10;
    public static final int BACKGROUND = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UI_HIGH, BACKGROUND})
    public @interface Priority {

    }

    private NetworkException exception;

    public BaseJob(Params params) {
        super(params);
    }

    protected boolean shouldRetry(Throwable throwable) {
        if (throwable instanceof NetworkException) {
            exception = (NetworkException) throwable;
            return exception.shouldRetry();
        }
        return true;
    }

    public NetworkException getNetworkException() {
        return exception;
    }

    @Override
    public void onAdded() {
        Timber.d("%s onAdded()", getClass().getName());
    }

    @Override
    protected void onCancel() {
        Timber.e("onCancel %s", getNetworkException());
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                int maxRunCount) {
        if (shouldRetry(throwable)) {
            return RetryConstraint.createExponentialBackoff(runCount, 1000);
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected int getRetryLimit() {
        return RETRY_LIMIT;
    }

    public abstract void inject(AppComponent appComponent);
}

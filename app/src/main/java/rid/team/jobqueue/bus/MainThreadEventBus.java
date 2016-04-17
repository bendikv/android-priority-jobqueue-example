package rid.team.jobqueue.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Extension of the normal Otto bus that makes sure events are always posted on the main thread to avoid threading
 * exceptions.
 */
public class MainThreadEventBus extends Bus {

    private final Handler mainThread = new Handler(Looper.getMainLooper());

    /** Creates a new Bus named "default" that enforces actions on the main thread. */
    public MainThreadEventBus() {
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });
        }
    }
}

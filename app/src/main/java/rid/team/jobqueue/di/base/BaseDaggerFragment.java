package rid.team.jobqueue.di.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import rid.team.jobqueue.App;
import rid.team.jobqueue.di.component.DaggerFragmentComponent;
import rid.team.jobqueue.di.component.FragmentComponent;

/**
 * Created by bendik on 17.03.16.
 */
public abstract class BaseDaggerFragment extends Fragment {
    private FragmentComponent mComponent;

    @Override
    public void onAttach(Context context) {
        buildComponent();
        onInject();
        super.onAttach(context);
    }

    private void buildComponent() {
        mComponent = DaggerFragmentComponent.builder()
                .appComponent(getApp().getAppComponent()).build();
    }

    protected App getApp() {
        return (App) ((BaseDaggerActivity) getActivity()).getApplicationContext();
    }


    protected abstract void onInject();

    public FragmentComponent getFragmentComponent() {
        return mComponent;
    }
}

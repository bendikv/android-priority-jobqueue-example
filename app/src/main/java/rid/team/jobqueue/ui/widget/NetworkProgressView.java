package rid.team.jobqueue.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rid.team.jobqueue.R;

/**
 * Created by bendik on 17.03.16.
 */
public class NetworkProgressView extends FrameLayout {
    @Bind(R.id.progress_layout) View progressLayout;
    @Bind(R.id.problem_layout) View problemLayout;
    @Bind(R.id.empty_data_layout) View emptyDataView;
    @Bind(R.id.problem_message) TextView mTextViewProblemMessage;
    private OnClickListener mOnRetryClickListener;

    {
        inflate(getContext(), R.layout.layout_network_progress, this);
        ButterKnife.bind(this);
        setLayoutParams(generateDefaultLayoutParams());
    }

    public NetworkProgressView(Context context) {
        super(context);
    }

    public NetworkProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetworkProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void retry(){
        problemLayout.setVisibility(View.GONE);
        emptyDataView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener){
        mOnRetryClickListener = onRetryClickListener;
    }

    public void onError() {
        onError(null);
    }

    public void onError(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTextViewProblemMessage.setText(message);
        }
        problemLayout.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
        emptyDataView.setVisibility(View.GONE);
    }

    public void onEmptyData(){
        setVisibility(View.VISIBLE);
        problemLayout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
        emptyDataView.setVisibility(View.VISIBLE);
    }

    public void onSuccess() {
        setVisibility(View.GONE);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    @OnClick(R.id.button_retry)
    public void onRetryButtonClicked(View v){
        if (mOnRetryClickListener != null) {
            mOnRetryClickListener.onClick(v);
        }
    }
}

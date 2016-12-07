package bupi.companyregistraralpha21.custom;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import bupi.companyregistraralpha21.R;

public class LastExpandableListView extends ExpandableListView {
    public LastExpandableListView(Context context) {
        super(context);
        setGroupIndicator(null);
        setDividerHeight(1);
        setDivider(ContextCompat.getDrawable(context, R.drawable.dv_lvl_1));
        setChildDivider(ContextCompat.getDrawable(context, R.drawable.dv_lvl_2));
    } // Constructor

    public void setViewHeight(int viewHeight) {
        _viewHeight = viewHeight;
        Log.d(LOG_TAG, "viewHeight set: " + _viewHeight);
    } // setViewHeight

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,               heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), _viewHeight);
        Log.d(LOG_TAG, "onMeasure " + this +
                ": width: " + decodeMeasureSpec(widthMeasureSpec) +
                "; height: " + decodeMeasureSpec(heightMeasureSpec) +
                "; measuredHeight: " + getMeasuredHeight() +
                "; measuredWidth: " + getMeasuredWidth());
    } // onMeasure

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    } // onLayout

    private String decodeMeasureSpec(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        String modeString = "<> ";
        switch (mode) {
            case View.MeasureSpec.UNSPECIFIED:
                modeString = "UNSPECIFIED ";
                break;
            case View.MeasureSpec.EXACTLY:
                modeString = "EXACTLY ";
                break;
            case View.MeasureSpec.AT_MOST:
                modeString = "AT_MOST ";
                break;
        } // switch mode
        return modeString + Integer.toString(View.MeasureSpec.getSize(measureSpec));
    } // decodeMeasureSpec

    private static final String LOG_TAG = "DebugExpandableListView";
    private int _viewHeight;
} // class
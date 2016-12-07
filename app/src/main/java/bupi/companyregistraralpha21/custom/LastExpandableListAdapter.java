package bupi.companyregistraralpha21.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;

import java.util.List;
import java.util.Map;

public class LastExpandableListAdapter extends SimpleExpandableListAdapter {

    Context _context;

    public LastExpandableListAdapter(Context context,
                                     List<? extends Map<String, ?>> groupData,
                                     int groupLayout,
                                     String[] groupFrom,
                                     int[] groupTo,
                                     List<? extends List<? extends Map<String, ?>>> childData,
                                     int childLayout,
                                     String[] childFrom,
                                     int[] childTo) {
        super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo);
        _context = context;
    } // Constructor

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView != null) {
            convertView.setLayoutParams(
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
            );
            return convertView;
        }
        return super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return super.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

} // class
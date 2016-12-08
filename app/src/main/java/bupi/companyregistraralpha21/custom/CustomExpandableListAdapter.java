package bupi.companyregistraralpha21.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bupi.companyregistraralpha21.ActivityMain;
import bupi.companyregistraralpha21.FragmentCheckList;
import bupi.companyregistraralpha21.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    static final int MAX_NO_OF_CHILDREN_PER_GROUP = 1000;

    private Context _context;
    private String _data[][][][];
    private LayoutInflater _inflater;
    private ExpandableListView _topExpList;
    private LastExpandableListView _listViewCache[];
    private static final String KEY_COLOR_NAME = "colorName";
    private static final String KEY_SHADE_NAME = "shadeName";
    private static final String KEY_RGB = "rgb";

    public CustomExpandableListAdapter(Context context,
                                       ExpandableListView topExpList,
                                       String data[][][][]) {
        _context = context;
        _topExpList = topExpList;
// blue <- level1 group
//   darkblue <- level2 group
//      pureblue #0000FF <- level2 child
// _data is formatted as the following:
// {
//   { // belongs to a level1 group [group1][x][y]
//     { // belongs to level2 group [group1][group2_0]
//       { // belongs to child items in [group1][group2_0]
//         { "group1_name", "group2_0_name" }    // [group1][0][0]
//         { "color_name1", "rgb1" }        // [group1][group2][item1] ...
//         { "color_name2", "rgb2" }        // [group1][group2][item2] ...
//       }
//     { // belongs to level2 group [group1][group2_1]
//       { // belongs to child items in [group1][group2_1]
//         { "group1_name", "group2_1_name" }    // [group1][1][0]
//          ...
//       }
//     }
//     ...
//   }
//   ...
// }
        _data = data;
        _inflater = LayoutInflater.from(context);
        _listViewCache = new LastExpandableListView[_data.length];
    } // Constructor

    public Object getChild(int groupPosition, int childPosition) {
        return _data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) (groupPosition * MAX_NO_OF_CHILDREN_PER_GROUP + childPosition);
    }

    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LastExpandableListView v;

        if (_listViewCache[groupPosition] != null) {
            v = _listViewCache[groupPosition];
            v.setViewHeight(calculateViewHeight(groupPosition, v));
        } else {
            LastExpandableListView dev = new LastExpandableListView(_context);

            dev.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int gp, int cp, long id) {

                    ((ActivityMain) _context).replaceFragment(
                            FragmentCheckList.newInstance(groupPosition, gp, cp),
                            groupPosition + ActivityMain.delimiter + // lvl0Indx
                                    gp + ActivityMain.delimiter + // lvl1Indx
                                    cp + ActivityMain.delimiter // lvl2Indx
                    );
                    return true;
                }
            });

            dev.setViewHeight(calculateViewHeight(groupPosition, null));
            dev.setAdapter(
                    new LastExpandableListAdapter(
                            _context,
                            createGroupList(groupPosition),    // groupData describes the first-level entries
                            R.layout.li_lvl_1,    // Layout for the first-level entries
                            new String[]{KEY_COLOR_NAME},    // Key in the groupData maps to display
                            new int[]{R.id.group2_name},        // Data under "colorName" key goes into this TextView
                            createChildList(groupPosition),    // childData describes second-level entries
                            R.layout.li_lvl_2,    // Layout for second-level entries
                            new String[]{KEY_SHADE_NAME, KEY_RGB},    // Keys in childData maps to display
                            new int[]{R.id.txt_tag, R.id.txt_child}    // Data under the keys above go into these TextViews
                    )
            );
            dev.setOnGroupClickListener(new Level1GroupExpandListener(groupPosition));
            _listViewCache[groupPosition] = dev;
            v = dev;
        } // if no cache

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
        //return _data[groupPosition].length; // ********************************** WARNING!!!!! This affects view in unexpected ways.
    }

    // TODO: Following method is a workaround since the developer is too dumb to override onLayout() in LastExpandableListView
    public int childCount(int gp) {
        return _data[gp].length;
    }

    public Object getGroup(int groupPosition) {
        return _data[groupPosition][0][0][0];
    }

    public int getGroupCount() {
        return _data.length;
    }

    public long getGroupId(int groupPosition) {
        return (long) (groupPosition * MAX_NO_OF_CHILDREN_PER_GROUP);  // To be consistent with getChildId
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v; // null
        if (convertView != null) {
            v = convertView;
        } else {
            v = _inflater.inflate(R.layout.li_lvl_0, parent, false);
        } // if
        String gt = (String) getGroup(groupPosition);
        TextView colorGroup = (TextView) v.findViewById(R.id.group_name);
        if (gt != null) {
            colorGroup.setText(gt);
        } // if
        return v;
    } // getGroupView

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void onGroupCollapsed(int groupPosition) {
    }

    public void onGroupExpanded(int groupPosition) {
    }

    /**
     * Creates a level2 group list out of the _data array according to
     * the structure required by SimpleExpandableListAdapter. The resulting
     * List contains Maps. Each Map contains one entry with key "colorName" and
     * value of an entry in the _data array.
     *
     * @param level1 Index of the level1 group whose level2 subgroups are listed.
     */
    private List<HashMap<String, String>> createGroupList(int level1) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        for (int i = 0; i < _data[level1].length; ++i) {
            HashMap<String, String> m = new HashMap<>();
            m.put(KEY_COLOR_NAME, _data[level1][i][0][1]);
            result.add(m);
        } // for
        return result;
    } // createGroupList

    /**
     * Creates the child list out of the _data array according to the
     * structure required by SimpleExpandableListAdapter. The resulting List
     * contains one list for each group. Each such second-level group contains
     * Maps. Each such Map contains two keys: "shadeName" is the name of the
     * shade and "rgb" is the RGB value for the shade.
     *
     * @param level1 Index of the level1 group whose level2 subgroups are included in the child list.
     */
    private List createChildList(int level1) {
        ArrayList<ArrayList> result = new ArrayList<>();
        for (int i = 0; i < _data[level1].length; ++i) {
            // Second-level lists
            ArrayList<HashMap<String, String>> secList = new ArrayList<>();
            for (int n = 1; n < _data[level1][i].length; ++n) {
                HashMap<String, String> child = new HashMap<>();
                child.put(KEY_SHADE_NAME, _data[level1][i][n][0]);
                child.put(KEY_RGB, _data[level1][i][n][1]);
                secList.add(child);
            } // for n
            result.add(secList);
        } // for i
        return result;
    }

    // Calculates the row count for a lvl0Indx expandable list adapter. Each level2 group counts 1 row
    // (group row) plus any child row that belongs to the group
    private int calculateViewHeight(int lvl0Indx, ExpandableListView lvl1View) {
        final int padding = 2;
        int lvl1GroupCount = _data[lvl0Indx].length;
        int lvl1rowHeight = padding + _context.getResources().getDimensionPixelSize(R.dimen.lvl1_row_height);
        int lvl2rowHeight = padding + _context.getResources().getDimensionPixelSize(R.dimen.lvl2_row_height);
        int viewHeight = 0;
        for (int j = 0; j < lvl1GroupCount; j++) {
            viewHeight += lvl1rowHeight; // for the group row
            if ((lvl1View != null) && (lvl1View.isGroupExpanded(j))) { // then add the children too (minus the group descriptor)
                viewHeight += (_data[lvl0Indx][j].length - 1) * lvl2rowHeight;
            } // if
        } // for i
        return viewHeight;
    } // calculateViewHeight


    class Level1GroupExpandListener implements ExpandableListView.OnGroupClickListener {

        private int _lvl0GroupPosition;

        public Level1GroupExpandListener(int lvl0GroupPosition) {
            _lvl0GroupPosition = lvl0GroupPosition;
        } // Constructor

        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if (parent.isGroupExpanded(groupPosition)) {
                parent.collapseGroup(groupPosition);
            } else {
                parent.expandGroup(groupPosition);
            } // if
            if (parent instanceof LastExpandableListView) {
                LastExpandableListView dev = (LastExpandableListView) parent;
                dev.setViewHeight(calculateViewHeight(_lvl0GroupPosition, parent));
            } // if
            _topExpList.requestLayout();
            return true;
        } // onGroupClick

    } // class Level1GroupExpandListener
} // class

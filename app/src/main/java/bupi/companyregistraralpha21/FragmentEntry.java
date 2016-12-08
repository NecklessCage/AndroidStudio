package bupi.companyregistraralpha21;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import bupi.companyregistraralpha21.custom.CustomExpandableListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentEntry extends Fragment {

    public static final String tag = "entry";

    Button _btnExpandCollapse;
    ExpandableListView _expandableListView;
    boolean _isExpanded;

    public FragmentEntry() {
    }

    String[][][][] data() {
        String bullet = "";

        String[] steps = {
                getResources().getString(R.string.step1),
                getResources().getString(R.string.step2),
                getResources().getString(R.string.step3),
                getResources().getString(R.string.step4),
        };

        return new String[][][][]
                {
                        { // Step 1
                                {  // 1.1
                                        {steps[0], getResources().getString(R.string.step1_1)},
                                        {bullet, getResources().getString(R.string.step1_1_1)},
                                        {bullet, getResources().getString(R.string.step1_1_2)}
                                }
                        },
                        { // Step 2
                                {  // 2.1
                                        {steps[1], getResources().getString(R.string.step2_1)},
                                        {bullet, getResources().getString(R.string.step2_1_1)},
                                        {bullet, getResources().getString(R.string.step2_1_2)},
                                        {bullet, getResources().getString(R.string.step2_1_3)}
                                },
                                {  // 2.2
                                        {steps[1], getResources().getString(R.string.step2_2)},
                                        {bullet, getResources().getString(R.string.step2_2_1)}
                                }
                        },
                        { // Step 3
                                {  // 3.1
                                        {steps[2], getResources().getString(R.string.step3_1)},
                                        {bullet, getResources().getString(R.string.step3_1_1)}
                                },
                                {  // 3.2
                                        {steps[2], getResources().getString(R.string.step3_2)},
                                        {bullet, getResources().getString(R.string.step3_2_1)},
                                        {bullet, getResources().getString(R.string.step3_2_2)}
                                }
                        },
                        { // Step 4
                                {  // 4.1
                                        {steps[3], getResources().getString(R.string.step4_1)},
                                        {bullet, getResources().getString(R.string.step4_1_1)}
                                },
                                { // 4.2
                                        {steps[3], getResources().getString(R.string.step4_2)},
                                        {bullet, getResources().getString(R.string.step4_2_1)},
                                        {bullet, getResources().getString(R.string.step4_2_2)}
                                }
                        }
                };
    } // initData

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_entry, container, false);

        initiate(rootView);

        return rootView;
    } // onCreateView

    void initiate(View v) {
        _isExpanded = false;

        _btnExpandCollapse = (Button) v.findViewById(R.id.btn_expand_collapse);

        _btnExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Refactored for shorter code
                final int groupCount = _expandableListView.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < groupCount; j++) {
                    final int childCount = ((CustomExpandableListAdapter) _expandableListView.getExpandableListAdapter()).childCount(j);

                    if (_isExpanded) _expandableListView.collapseGroup(j);
                    else _expandableListView.expandGroup(j);

                    for (int k = 0; k < childCount; k++) {
                        final ExpandableListView lv = (ExpandableListView) _expandableListView.getExpandableListAdapter().getChildView(j, k, false, null, null);

                        if (_isExpanded) lv.collapseGroup(k);
                        else lv.expandGroup(k);
                    } // for k -> children
                } // for j -> groups
                _isExpanded = !_isExpanded;
                _btnExpandCollapse.setText(_isExpanded ? R.string.collapse_all : R.string.expand_all);

                /*
                // Following if-block could be shortened but this might be better. Maybe.
                if (_isExpanded) { // Collapse them all
                    for (int j = 0; j < _expandableListView.getExpandableListAdapter().getGroupCount(); j++) {
                        _expandableListView.collapseGroup(j);
                        final int childCount = ((CustomExpandableListAdapter)
                                _expandableListView.getExpandableListAdapter())
                                .childCount(j);
                        for (int k = 0; k < childCount; k++) {
                            ((ExpandableListView)
                                    _expandableListView
                                            .getExpandableListAdapter()
                                            .getChildView(j, k, false, null, null))
                                    .collapseGroup(k);
                        } // for k
                    } // for j
                    _isExpanded = false;
                    _btnExpandCollapse.setText(R.string.expand_all);
                    // -----------------------------------------------------------------------------------------------
                } else { // Expand them all
                    final int groupCount = _expandableListView.getExpandableListAdapter()
                            .getGroupCount();
                    for (int j = 0; j < groupCount; j++) {
                        _expandableListView.expandGroup(j);
                        final int childCount = ((CustomExpandableListAdapter)
                                _expandableListView.getExpandableListAdapter()).childCount(j);
                        for (int k = 0; k < childCount; k++) {
                            ((ExpandableListView)
                                    _expandableListView
                                            .getExpandableListAdapter()
                                            .getChildView(j, k, false, null, null))
                                    .expandGroup(k);
                        } // for k
                    } // for j
                    _isExpanded = true;
                    _btnExpandCollapse.setText(R.string.collapse_all);
                } // if expanded
                */
            } // onClick
        });

        // Populate expandable list view
        _expandableListView = (ExpandableListView) v.findViewById(R.id.main_list);
        _expandableListView.setAdapter(
                new CustomExpandableListAdapter(
                        v.getContext(),
                        _expandableListView,
                        data()
                )
        );
    } // initiate
} // class

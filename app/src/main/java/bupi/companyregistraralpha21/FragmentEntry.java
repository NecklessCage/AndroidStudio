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
    ExpandableListView _exExpandableListView;
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

        // Set button on click listener
        _btnExpandCollapse = (Button) v.findViewById(R.id.btn_expand_collapse);
        // TODO: button on-click listener

        _btnExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Following if-block could be shortened but this might be better. Maybe.
                if (_isExpanded) { // Collapse them all
                    for (int j = 0; j < _exExpandableListView.getExpandableListAdapter().getGroupCount(); j++) {
                        _exExpandableListView.collapseGroup(j);
                        for (int k = 0; k < _exExpandableListView.getExpandableListAdapter().getChildrenCount(j); k++) {
                            ((ExpandableListView)
                                    _exExpandableListView
                                            .getExpandableListAdapter()
                                            .getChildView(j, k, false, null, null))
                                    .collapseGroup(k);
                        } // for k
                    } // for j
                    _isExpanded = false;
                    _btnExpandCollapse.setText(R.string.expand_all);
                    // -----------------------------------------------------------------------------------------------
                } else { // Expand them all
                    for (int j = 0; j < _exExpandableListView.getExpandableListAdapter().getGroupCount(); j++) {
                        _exExpandableListView.expandGroup(j);
                        final int childCount = ((CustomExpandableListAdapter)
                                _exExpandableListView.getExpandableListAdapter())
                                .childCount(j);
                        for (int k = 0; k < childCount; k++) {
                            ((ExpandableListView)
                                    _exExpandableListView
                                            .getExpandableListAdapter()
                                            .getChildView(j, k, false, null, null))
                                    .expandGroup(k);
                        } // for k
                    } // for j
                    _isExpanded = true;
                    _btnExpandCollapse.setText(R.string.collapse_all);
                } // if expanded
            } // onClick
        });

        // Populate expandable list view
        _exExpandableListView = (ExpandableListView) v.findViewById(R.id.main_list);
        _exExpandableListView.setAdapter(
                new CustomExpandableListAdapter(
                        v.getContext(),
                        _exExpandableListView,
                        data()
                )
        );
    } // initiate
} // class

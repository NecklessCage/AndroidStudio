package bupi.companyregistraralpha21;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCheckList extends Fragment {

    View _rootView;

    public static FragmentCheckList newInstance(int lvl0Indx, int lvl1Indx, int lvl2Indx) {
        FragmentCheckList f = new FragmentCheckList();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("lvl0Indx", lvl0Indx);
        args.putInt("lvl1Indx", lvl1Indx);
        args.putInt("lvl2Indx", lvl2Indx);
        f.setArguments(args);
        return f;
    }

    public FragmentCheckList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.f_check_list, container, false);

        Bundle args = getArguments();
        final int lvl0Indx = args.getInt("lvl0Indx");
        final int lvl1Indx = args.getInt("lvl1Indx");
        final int lvl2Indx = args.getInt("lvl2Indx");

        // Set title
        TextView fragmentTitle = (TextView) _rootView.findViewById(R.id.txt_list_title);
        fragmentTitle.setText("Step ("
                + (lvl0Indx + 1) + "."
                + (lvl1Indx + 1) + "."
                + (lvl2Indx + 1)
                + ")");
        /*
        fragmentTitle.setText("????? ("
                + engToMm(lvl0Indx + 1)
                + engToMm(lvl1Indx + 1)
                + engToMm(lvl2Indx + 1)
                + ")");
                */

        ListView listView = (ListView) _rootView.findViewById(R.id.lst_check_list);
        listView.setDivider(ContextCompat.getDrawable(_rootView.getContext(), R.drawable.dv_leaf));
        listView.setAdapter(
                new ArrayAdapter<>(
                        _rootView.getContext(),
                        R.layout.li_lvl_0,
                        arrayData(lvl0Indx, lvl1Indx, lvl2Indx)
                )
        );
        return _rootView;
    } // onCreateView


    String[] arrayData(int lvl0Indx, int lvl1Indx, int lvl2Indx) {
        switch (lvl0Indx) {
            case 0: // 1
                switch (lvl1Indx) {
                    case 0: // 1.1 Submit
                        switch (lvl2Indx) {
                            case 0: // 1.1.1 Prep
                                return getResources().getStringArray(R.array.step1_1_1);
                            case 1: // 1.1.2 DICA
                                return getResources().getStringArray(R.array.step1_1_2);
                            default:
                                return new String[]{};
                        } // 2
                    default:
                        return new String[]{};
                } // 1
            case 1: // 2
                switch (lvl1Indx) {
                    case 0: // 2.1
                        switch (lvl2Indx) {
                            case 0: // 2.1.1
                                return getResources().getStringArray(R.array.step2_1_1);
                            case 1: // 2.1.2
                                return getResources().getStringArray(R.array.step2_1_2);
                            case 2: // 2.1.3
                                return getResources().getStringArray(R.array.step2_1_3);
                            default:
                                return new String[]{};
                        } // 2
                    case 1: // 2.2
                        switch (lvl2Indx) {
                            case 0: // 2.2.1
                                return getResources().getStringArray(R.array.step2_2_1);
                            default:
                                return new String[]{};
                        } // 2
                    default:
                        return new String[]{};
                } // 1
            case 2: // 3
                switch (lvl1Indx) {
                    case 0: // 3.1
                        switch (lvl2Indx) {
                            case 0: // 3.1.1
                                return getResources().getStringArray(R.array.step3_1_1);
                            default:
                                return new String[]{};
                        } // 2
                    case 1: // 3.2
                        switch (lvl2Indx) {
                            case 0: // 3.2.1
                                return getResources().getStringArray(R.array.step3_2_1);
                            case 1: // 3.2.2
                                return getResources().getStringArray(R.array.step3_2_2);
                            default:
                                return new String[]{};
                        } // 2
                    default:
                        return new String[]{};
                } // 1
            case 3:
                switch (lvl1Indx) {
                    case 0: // 4.1
                        switch (lvl2Indx) {
                            case 0: // 4.1.1
                                return getResources().getStringArray(R.array.step4_1_1);
                            default: // nothing here
                                return new String[]{};
                        } // 2
                    case 1: // 4.2
                        switch (lvl2Indx) {
                            case 0: // 4.2.1
                                return getResources().getStringArray(R.array.step4_2_1);
                            case 1: // 4.2.2
                                return getResources().getStringArray(R.array.step4_2_2);
                            default:
                                return new String[]{};
                        } // 2
                    default:
                        return new String[]{};
                } // 1
            default:
                return new String[]{};
        } // 0
    } // arrayData

    /**
     * @param num num <= 9 && n >= 0
     * @return Burmese number character corresponding to num
     */
    char engToMm(int num) {
        switch (num) {
            case 0:
                return '?';
            case 1:
                return '?';
            case 2:
                return '?';
            case 3:
                return '?';
            case 4:
                return '?';
            case 5:
                return '?';
            case 6:
                return '?';
            case 7:
                return '?';
            case 8:
                return '?';
            case 9:
                return '?';
            default:
                return '-';
        } // switch
    } // engToMm

} // class

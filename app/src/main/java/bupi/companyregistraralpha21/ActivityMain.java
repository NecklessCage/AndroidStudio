package bupi.companyregistraralpha21;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import bupi.companyregistraralpha21.custom.LastExpandableListView;

/**
 *
 */
public class ActivityMain extends Activity {

    public static final String delimiter = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        getFragmentManager()
                .beginTransaction()
                .add(
                        R.id.container_main,
                        new FragmentEntry(),
                        FragmentEntry.tag
                )
                .addToBackStack(null)
                .commit();
    }

    /**
     * onBackPressed() does 2 things:
     * 1) If the current fragment has no previous fragment, the phone goes to home screen.
     * 2) Otherwise, retrieve the state of the double-expandable list and show the appropriate view.
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) { // Goes to home screen @else
            // Get the fragment before backing
            FragmentCheckList fragmentCheckList = (FragmentCheckList) getActiveFragment();
            if (fragmentCheckList == null) {
                return;
            } // if null
            int[] states = getState(fragmentCheckList.getTag());

            // Perform default back
            super.onBackPressed();

            // Set the states of expandable list views
            ExpandableListView lvl0List = (ExpandableListView) findViewById(R.id.main_list);
            if (lvl0List == null) {
                return;
            } // if null
            lvl0List.expandGroup(states[0]);
            // Expand the lvl1 expandable list
            ((LastExpandableListView)
                    lvl0List
                            .getExpandableListAdapter()
                            .getChildView(states[0], states[1], false, null, null)
            ).expandGroup(states[1]);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } // if last fragment
    } // onBackPressed

    /**
     * @param fragment Fragment to be added (technically a replace).
     * @param tag      tag must hold a String of the format lvl0Indx + delimiter + lvl1Indx + delimiter + lvl2Indx
     *                 --to be retrieved on back button. TODO: Validation
     */
    public void replaceFragment(Fragment fragment, String tag) {
        rightInAnimation(fragment, tag, true);
    } // replaceFragment

    /**
     * @return Returns the currently active fragment.
     */
    Fragment getActiveFragment() {
        FragmentManager m = getFragmentManager();
        if (m.getBackStackEntryCount() == 0) {
            return null;
        } // if
        String tag = m.getBackStackEntryAt(m.getBackStackEntryCount() - 1).getName();
        return m.findFragmentByTag(tag);
    } // getActiveFragment

    /**
     * @param stateString String of the form [lvl0Indx][delimiter][lvl1Indx][delimiter][lvl2Indx].
     *                    [delimiter] is defined above.
     * @return {lvl0Indx, lvl1Indx, lvl2Indx}
     * <p/>
     * The index values were stored as a tag when fragment replacement transaction occurred. The
     * index values store the state of the FragmentEntry instance before it was replaced by a
     * FragmentChecklist instance.
     */
    int[] getState(String stateString) {
        String[] strings = stateString.split(delimiter);
        if (strings.length != 3) {
            return null;
        } else {
            int[] ans = new int[3];
            for (int j = 0; j < 3; j++) {
                ans[j] = Integer.parseInt(strings[j]);
            } // for
            return ans;
        } // if
    } // getState

    // Fragment replacement animations
    void rightInAnimation(Fragment fragment, String tag, boolean stack) {
        // TODO: Adjust animation parameters
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        transaction.replace(R.id.container_main, fragment, tag);
        if (stack) { // If needed to be added to back stack
            transaction.addToBackStack(fragment.getTag());
        } // End of if.
        transaction.commit();
    } // rightInAnimation

    void leftInAnimation(Fragment fragment, String tag, boolean stack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit,
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit);
        transaction.replace(R.id.container_main, fragment, tag);
        if (stack) { // If needed to be added to back stack.
            transaction.addToBackStack(fragment.getTag());
        } // End of if.
        transaction.commit();
    } // leftInAnimation
} // class

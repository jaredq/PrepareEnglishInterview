package info.jaredq.ppiv;

import android.support.v4.app.Fragment;

/**
 * Created by 1218 on 22/02/2015.
 */
public class MainFragmentHelper {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Create new instance.
     *
     * @param number
     * @return
     */
    public static Fragment newInstance(int number) {
        Fragment fragment = null;
        switch (number) {
            case 1:
                //questions_and_tips
                fragment = WelcomeFragment.newInstance(number);
                break;
            case 2:
                //questions_and_tips
                fragment = QuestionsAndTipsFragment.newInstance(number);
                break;
            case 3:
                // my answers
                fragment = MyAnswersFragment.newInstance(number);
                break;
            case 4:
                // profile
                fragment = ProfileFragment.newInstance(number);
                break;
            case 5:
                // settings
                fragment = SettingsFragment.newInstance(number);
                break;
            case 6:
                // help
                fragment = HelpFragment.newInstance(number);
                break;
        }
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, number);
//        fragment.setArguments(args);
        return fragment;
    }
}
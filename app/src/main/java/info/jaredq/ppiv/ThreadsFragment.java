package info.jaredq.ppiv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import info.jaredq.ppiv.dummy.DummyContent;
import info.jaredq.ppiv.models.Thread;
import info.jaredq.ppiv.models.ThreadHelper;

/**
 * A fragment representing a list of threads.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ThreadsFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     *
     */
    private static final String KEY_FORUM_ID = "FORUM_ID";

    /**
     *
     */
    private int mForumId;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * The thread list
     */
    List<Thread> mThreadList;

    /**
     * the get thread list AsyncTask
     */
    AsyncTask mGetThreadListAT;

    public static ThreadsFragment newInstance(int forumId) {
        ThreadsFragment fragment = new ThreadsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_FORUM_ID, forumId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThreadsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mForumId = getArguments().getInt(KEY_FORUM_ID);
        }

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threads, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);

        mGetThreadListAT = new GetThreadListAT().execute(mForumId);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onThreadClick(mThreadList.get(position).getTid());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private class GetThreadListAT extends AsyncTask<Integer, Integer, List<Thread>> {

        @Override
        protected List<Thread> doInBackground(Integer... params) {
            mThreadList = ThreadHelper.getThreadList(params[0]);

            return mThreadList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Thread> o) {
            super.onPostExecute(o);

            List<Thread> items = (List<Thread>) o;
            mAdapter = new ArrayAdapter<Thread>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);

            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        }
    }

}

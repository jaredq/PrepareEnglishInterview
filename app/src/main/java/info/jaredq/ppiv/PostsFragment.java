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
import info.jaredq.ppiv.models.Post;
import info.jaredq.ppiv.models.PostHelper;

/**
 * A fragment representing a list of posts.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PostsFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String KEY_THREAD_ID = "THREAD_ID";

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

    private int mThreadId;

    private AsyncTask mGetPostListAT;

    public static PostsFragment newInstance(int threadId) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_THREAD_ID, threadId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mThreadId = getArguments().getInt(KEY_THREAD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);

        mGetPostListAT = new GetPostListAT().execute(mThreadId);

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
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
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

    /**
     * The get post list async task class.
     */
    private class GetPostListAT extends AsyncTask<Integer, Integer, List<Post>> {

        @Override
        protected List<Post> doInBackground(Integer... params) {
            List<Post>  mPostList = PostHelper.getPostList(params[0]);

            return mPostList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Post> o) {
            super.onPostExecute(o);

            List<Post> items = (List<Post>) o;
            mAdapter = new ArrayAdapter<Post>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, items);

            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        }
    }
}

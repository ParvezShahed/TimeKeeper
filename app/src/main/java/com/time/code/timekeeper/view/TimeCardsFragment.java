package com.time.code.timekeeper.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.time.code.timekeeper.R;
import com.time.code.timekeeper.model.TimeCard;
import com.time.code.timekeeper.presenter.CardsPresenter;
import com.time.code.timekeeper.presenter.RestTimeCardsCallBacks;

import java.util.List;

/**
 * Created by shahed on 15/07/2016.
 */

public class TimeCardsFragment extends Fragment implements OnTimeCardClickListener {
    private RecyclerView mRecyclerView;
    private TimeCardsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mUsername;
    private Button mButtonPunchIn;
    private Button mButtonPunchOut;
    private ContentLoadingProgressBar mLoadingProgressBar;
    private CardsPresenter mCardsPresenter;
    private boolean mPunchedIn = false;

    public void setUsername(String username){
        this.mUsername = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_time_cards, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.time_cards_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mButtonPunchIn = (Button) view.findViewById(R.id.punchin);
        mButtonPunchOut = (Button) view.findViewById(R.id.punchout);
        mLoadingProgressBar = (ContentLoadingProgressBar) view.findViewById(R.id.time_cards_progress);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPunchIn();
        onPunchOut();
        mAdapter = new TimeCardsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mCardsPresenter = new CardsPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        //mCardsPresenter.deleteTimeCard(73);
        //mCardsPresenter.deleteTimeCard(74);
        //mCardsPresenter.deleteTimeCard(75);
        updateView();
    }

    /**
     * Fetch all cards from the cloud and update the recycler view
     */
    private void updateView(){
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mCardsPresenter.fetchCards(new RestTimeCardsCallBacks() {
            @Override
            public void onSuccess(List<TimeCard> timeCards) {
                mLoadingProgressBar.setVisibility(View.GONE);
                mAdapter.updateDataset(timeCards);
            }
            @Override
            public void onError() {
            }
        });
    }

    /**
     * Checks whether it is already punched in or not before sending punch in time
     */
    private void onPunchIn(){
        mButtonPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPunchedIn){
                    Toast.makeText(getActivity(),
                            "Please punch out first.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mPunchedIn = true;
                punchIn();
            }
        });
    }

    /**
     * Checks whether it is already punched out or not before sending punch out time
     */
    private void onPunchOut(){
        mButtonPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPunchedIn){
                    Toast.makeText(getActivity(),
                            "Punch in please.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mPunchedIn = false;
                final int id = getLastCreatedCardIdSP();
                insertTime(id);
            }
        });
    }

    /**
     * Read last punched in time card's id from shared preferences.
     * If there is no info, create a new card and insert time on it.
     * If a valid id is found, retrieves the same time card from the cloud and check the date is same as today.
     * If no card of current date create one otherwise insert time on the existing.
     */
    private void punchIn(){
        final int id = getLastCreatedCardIdSP();
        if(id < 0){
            insertCardAndTime();
            return;
        }
        mCardsPresenter.getTimeCard(new RestTimeCardsCallBacks() {
            @Override
            public void onSuccess(List<TimeCard> timeCards) {
                TimeCard timeCard = timeCards.get(0);
                if(timeCard == null){
                    insertCardAndTime();
                    return;
                }
                String date = timeCard.getmOccurrence();
                String currentDate = new CardsPresenter().getCurrentDate();
                int id = timeCard.getId();

                if(date.contains(currentDate)){
                    insertTime(id);
                }else {
                    insertCardAndTime();
                }
            }
            @Override
            public void onError() {
            }
        },id);

    }

    /**
     * Insert a card first and then inset time on it.
     * Save the card id in the shared preferences.
     */
    private void insertCardAndTime(){
        mCardsPresenter.insetCard(new RestTimeCardsCallBacks() {
            @Override
            public void onSuccess(List<TimeCard> timeCards) {
                if(timeCards == null){
                    return;
                }
                TimeCard timeCard = timeCards.get(0);
                saveLastlyCreatedCardSP(timeCard.getId());
                insertTime(timeCard.getId());
            }
            @Override
            public void onError() {
            }
        }, mUsername);
    }

    /**
     * Insert time on a time card
     * @param timeCardId the id of a time card
     */
    private void insertTime(int timeCardId){
        mCardsPresenter.insertTime(new RestTimeCardsCallBacks() {
            @Override
            public void onSuccess(List<TimeCard> timeCards) {
              updateView();
            }
            @Override
            public void onError() {
            }
        }, timeCardId);
    }

    /**
     * Save id of last created card
     * @param lastlyCreatedCardId is of last created card
     */
    private void saveLastlyCreatedCardSP(int lastlyCreatedCardId){
        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs", TimeKeeperActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("last_card_id", lastlyCreatedCardId);
        editor.commit();
    }

    /**
     * Retrieve the id of the last created card
     * @return id of last created card.
     */
    private int getLastCreatedCardIdSP(){
        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs", TimeKeeperActivity.MODE_PRIVATE);
        return sp.getInt("last_card_id", -1);
    }

    /**
     * Go to times view on clicking a time card item
     * @param timeCard a timecard object
     */
    @Override
    public void onClick(TimeCard timeCard) {
        TimesFragment timesFragment = new TimesFragment();
        timesFragment.setTimeCard(timeCard);
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, timesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
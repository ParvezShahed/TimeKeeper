package com.time.code.timekeeper.presenter;

import com.google.gson.Gson;
import com.time.code.timekeeper.model.TimeCard;
import com.time.code.timekeeper.model.TimeEntry;
import com.time.code.timekeeper.restful.TimeKeeperApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shahed on 16/07/2016.
 */
public class CardsPresenter {

    public CardsPresenter(){
    }

    /**
     * Insert a card to the time keeping cloud
     * @param callBacks notifies the caller in case of success and failure.
     * @param username Name of the user
     */
    public void insetCard(final RestTimeCardsCallBacks callBacks, String username){
        TimeCard timeCard = new TimeCard(username, getCurrentDate());
        TimeKeeperApi.getInstance()
                .createTimeCard(timeCard)
                .enqueue(new Callback<TimeCard>() {
                    @Override
                    public void onResponse(Call<TimeCard> call, Response<TimeCard> response) {
                        int statusCode = response.code();
                        TimeCard timeCard = response.body();
                        List<TimeCard> timeCards = new ArrayList<>();
                        timeCards.add(timeCard);
                        callBacks.onSuccess(timeCards);
                    }
                    @Override
                    public void onFailure(Call<TimeCard> call, Throwable t) {
                        callBacks.onError();
                    }
                });
    }

    /**
     * Inset time corresponding to a card
     * @param callBacks notifies the caller in case of success and failure.
     * @param timeCardId integer id of the time card
     */
    public void insertTime(final RestTimeCardsCallBacks callBacks, int timeCardId){
        TimeKeeperApi.getInstance()
                .createTimeEntry(new TimeEntry(getCurrentTime(), timeCardId))
                .enqueue(new Callback<TimeEntry>() {
                    @Override
                    public void onResponse(Call<TimeEntry> call, Response<TimeEntry> response) {
                        callBacks.onSuccess(null);
                    }
                    @Override
                    public void onFailure(Call<TimeEntry> call, Throwable t) {
                        callBacks.onError();
                    }
                });
    }

    /**
     * Delete a single time card
     * @param id time card id
     */
    public void deleteTimeCard(int id){
        TimeKeeperApi.getInstance().deleteTimeCard(id).enqueue(new Callback<TimeCard>() {
            @Override
            public void onResponse(Call<TimeCard> call, Response<TimeCard> response) {
            }
            @Override
            public void onFailure(Call<TimeCard> call, Throwable t) {
            }
        });
    }

    /**
     *Fetches all the cards from cloud
     * @param callBacks notify the caller on success or failure
     */
    public void fetchCards(final RestTimeCardsCallBacks callBacks){
        TimeKeeperApi.getInstance()
                .getAllTimeCards()
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        int statusCode = response.code();
                        Object timeCard = response.body();
                        String jsonSting = ((Map<String, String>)timeCard).get("timecards");
                        ArrayList<TimeCard> timeCards = new ArrayList<>();
                        JSONArray jsonarray;
                        try {
                            jsonarray = new JSONArray(jsonSting);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                Gson gson = new Gson();
                                TimeCard tc = gson.fromJson(jsonobject.toString(), TimeCard.class);
                                JSONArray timeArray = jsonobject.getJSONArray("time_entries");
                                ArrayList<TimeEntry> timeEntries = new ArrayList<>();
                                for (int j = 0; j < timeArray.length(); j++){
                                    JSONObject timeObject = timeArray.getJSONObject(j);
                                    TimeEntry timeEntry = gson.fromJson(timeObject.toString(), TimeEntry.class);
                                    timeEntries.add(timeEntry);
                                }
                                tc.setTimeEntries(timeEntries);
                                timeCards.add(tc);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callBacks.onSuccess(timeCards);
                    }
                    @Override
                public void onFailure(Call<Object> call, Throwable t) {
                callBacks.onError();
            }
        });
    }

    /**
     * Retrieve a single time card
     * @param callBacks notifies the caller about success and failure
     * @param id gets a time card corresponding to an id
     */
    public void getTimeCard(final RestTimeCardsCallBacks callBacks, int id){
        TimeKeeperApi.getInstance()
                .getTimeCard(id)
                .enqueue(new Callback<TimeCard>() {
                    @Override
                    public void onResponse(Call<TimeCard> call, Response<TimeCard> response) {
                        TimeCard timeCard = response.body();
                        ArrayList<TimeCard> timeCards = new ArrayList<>();
                        timeCards.add(timeCard);
                        callBacks.onSuccess(timeCards);
                    }
                    @Override
                    public void onFailure(Call<TimeCard> call, Throwable t) {
                        callBacks.onError();
                    }
                });
    }

    /**
     * Retrieves current date in "yyyy-MM-dd" format
     * @return current date string
     */
    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    /**
     * Gets current time
     * @return Return current time in "h:mm a" format
     */
    public String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("h:mm a");
        return df.format(c.getTime());
    }

}

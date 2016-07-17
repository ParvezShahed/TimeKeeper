package com.time.code.timekeeper.restful;

import com.time.code.timekeeper.model.TimeCard;
import com.time.code.timekeeper.model.TimeEntry;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shahed on 14/07/2016.
 */
public class TimeKeeperApi {
    public static final String BASE_URL = "https://timekeeping-api.herokuapp.com/api/v1/";
    private TimekeeperApiInterface mApiService;

    /**
     * Gets the instance of the api
     * @return returns api instance
     */
    public static TimeKeeperApi getInstance(){
        return TimeKeeperApiHolder.instance;
    }

    /**
     * Constructor
     */
    public TimeKeeperApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(TimekeeperApiInterface.class);
    }

    /**
     * Api call to get all time cards
     * @return returns call obj
     */
    public Call<Object> getAllTimeCards(){
        return mApiService.getAllTimeCards();
    }

    /**
     * Creates a time card
     * @param timeCard time card instance
     * @return call obj
     */
    public Call<TimeCard> createTimeCard(TimeCard timeCard){
        return mApiService.createTimeCard(timeCard);
    }

    /**
     * Delete a time card
     * @param id id of time card
     * @return call obj
     */
    public Call<TimeCard> deleteTimeCard(int id){
        return mApiService.deleteTimeCard(id);
    }

    /**
     * Gets a time card corresponding to an id.
     * @param id time card id
     * @return call obj
     */
    public Call<TimeCard> getTimeCard(int id){
        return mApiService.getTimeCard(id);
    }

    /**
     *
     * @param timeEntry creates a time entry
     * @return call obj
     */
    public Call<TimeEntry> createTimeEntry(TimeEntry timeEntry) {
        return mApiService.createTimeEntry(timeEntry);
    }

    private static class TimeKeeperApiHolder {
        public static final TimeKeeperApi instance = new TimeKeeperApi();
    }

}

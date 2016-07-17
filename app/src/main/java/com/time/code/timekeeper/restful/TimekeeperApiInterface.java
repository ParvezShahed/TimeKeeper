package com.time.code.timekeeper.restful;

import com.time.code.timekeeper.model.TimeCard;
import com.time.code.timekeeper.model.TimeEntry;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shahed on 14/07/2016.
 */

public interface TimekeeperApiInterface {
    @GET("timecards")
    Call<Object> getAllTimeCards();

    @POST("timecards")
    Call<TimeCard> createTimeCard(@Body TimeCard timeCard);

    @PUT("timecards/{id}")
    Call<TimeCard> updateTimeCard(@Path("id") int id, @Body TimeCard timeCard);

    @GET("timecards/{id}")
    Call<TimeCard> getTimeCard(@Path("id") int id);

    @DELETE("timecards/{id}")
    Call<TimeCard> deleteTimeCard(@Path("id") int id);

    @GET("timecards")
    Call<TimeCard> getTodaysTimeCard(@Query("occurrence") String occurrence);

    @GET("time_entries")
    Call<Object> getAllTimeEntries();

    @POST("time_entries")
    Call<TimeEntry> createTimeEntry(@Body TimeEntry timeEntry);

    @PUT("time_entries/{id}")
    Call<TimeEntry> updateTimeEntry(@Path("id") int id, @Body TimeEntry timeEntry);

    @DELETE("time_entries/{id}")
    Call<TimeEntry> deleteTimeEntry(@Path("id") int id);
}

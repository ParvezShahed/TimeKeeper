package com.time.code.timekeeper.presenter;

import com.time.code.timekeeper.model.TimeCard;

import java.util.List;

/**
 * Created by shahed on 16/07/2016.
 */
public interface RestTimeCardsCallBacks {
    void onSuccess(List<TimeCard> timeCards);
    void onError();
}

package com.layalty.app.listener;

/**
 * Created by 19569 on 6/19/2017.
 */

public interface CommunicationListener {

    abstract void onTastFinished(String response, String requestKey);
}

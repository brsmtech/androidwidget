package com.bstech.widapp;

/**
 * Created by brayskiy on 3/6/18.
 */

public class TestApi {
    public void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {}
    }
}

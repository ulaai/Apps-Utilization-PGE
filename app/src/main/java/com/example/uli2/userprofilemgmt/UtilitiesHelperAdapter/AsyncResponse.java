package com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter;

import android.content.Context;

/**
 * Created by uli on 31/07/17.
 */

public interface AsyncResponse {
    void processFinish(String output);

    Context getDelegateContext();
}

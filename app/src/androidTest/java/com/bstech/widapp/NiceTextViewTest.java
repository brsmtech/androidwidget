package com.bstech.widapp;

import android.os.Handler;
import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.bstech.widapp.activity.NiceTextViewActivity;
import com.bstech.widlib.view.NiceTextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by brayskiy on 3/6/18.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NiceTextViewTest {
    private TestApi api = new TestApi();

    @Rule
    public ActivityTestRule<NiceTextViewActivity> activity =
            new ActivityTestRule<>(NiceTextViewActivity.class);

    @Before
    public void initializeTests() {}

    @After
    public void finalizeTests() {
        api.sleep(10000);
    }

    @Test
    public void allTests() {
        api.sleep(2000);

        NiceTextView view1 = activity.getActivity().findViewById(R.id.nice_text_view1);
        NiceTextView view2 = activity.getActivity().findViewById(R.id.nice_text_view2);
        NiceTextView view3 = activity.getActivity().findViewById(R.id.nice_text_view3);

        activity.getActivity().runOnUiThread(() -> view1.setText(""));

        {
            final StringBuilder outStr = new StringBuilder();
            for (char ch = 'A'; ch < 'Z'; ++ch) {
                outStr.append(ch);
                activity.getActivity().runOnUiThread(() -> view1.setText(outStr.toString()));
                activity.getActivity().runOnUiThread(() -> view2.setText(outStr.toString()));
                api.sleep(500);
            }
        }

        {
            final StringBuilder outStr = new StringBuilder();
            for (char ch = 'a'; ch < 'z'; ++ch) {
                outStr.append(ch);
                activity.getActivity().runOnUiThread(() -> view1.setText(outStr.toString()));
                activity.getActivity().runOnUiThread(() -> view2.setText(outStr.toString()));
                api.sleep(500);
            }
        }

        {
            final StringBuilder outStr = new StringBuilder();
            for (char ch = 'a'; ch < 'z'; ++ch) {
                outStr.append(ch);
                activity.getActivity().runOnUiThread(() -> view3.setText(outStr.toString()));
                api.sleep(300);
                activity.getActivity().runOnUiThread(() -> view3.setText(""));
                api.sleep(300);
                activity.getActivity().runOnUiThread(() -> view3.setText(null));
                api.sleep(300);
            }
        }

        activity.getActivity().runOnUiThread(() -> view2.setText(null));
        activity.getActivity().runOnUiThread(() -> view1.setText("All Done"));

        api.sleep(2000);
    }
}

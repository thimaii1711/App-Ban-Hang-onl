package com.example.appbanhangonl.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.fragment.SpeakerFragment;
import com.example.appbanhangonl.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meeting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final String meetingId = getIntent().getStringExtra("meetingId");
        String token = getIntent().getStringExtra("token");
        String mode = getIntent().getStringExtra("mode");
        String localParticipantName = "Admin";
        boolean streamEnable = mode.equals("CONFERENCE");
        postDataToMeeting(meetingId, token);

        // khởi tạo VideoSDK
        VideoSDK.initialize(getApplicationContext());

        // Cấu hình VideoSDK với Token
        VideoSDK.config(token);

        // Khởi tạo cuộc họp VideoSDK
        meeting = VideoSDK.initMeeting(MeetingActivity.this, meetingId, localParticipantName, streamEnable, streamEnable, null, mode, false, null);

        // join Meeting
        meeting.join();

        // if mode is CONFERENCE than replace mainLayout with SpeakerFragment otherwise with ViewerFragment
        meeting.addEventListener(new MeetingEventListener() {
            @Override
            public void onMeetingJoined() {
                if (meeting != null) {
                    if (mode.equals("CONFERENCE")) {
                        //pin the local partcipant
                        meeting.getLocalParticipant().pin("SHARE_AND_CAM");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main, new SpeakerFragment(), "MainFragment")
                                .commit();
                    }
                }
            }
        });
    }

    private void postDataToMeeting(String meetingId, String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL + "insertmeeting.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("meetingId", meetingId);
                params.put("token", token);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public Meeting getMeeting() {
        return meeting;
    }
}
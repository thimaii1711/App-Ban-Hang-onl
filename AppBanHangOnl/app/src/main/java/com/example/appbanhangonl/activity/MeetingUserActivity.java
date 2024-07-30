package com.example.appbanhangonl.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.fragment.ViewerFragment;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingUserActivity extends AppCompatActivity {
    private Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meeting_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getMeetingIdFromServer();
    }

    public Meeting getMeeting() {
        return meeting;
    }

    private void getMeetingIdFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utils.BASE_URL + "getmeeting.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("succes")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            String meetingId = jsonArray.getJSONObject(0).getString("meetingId");
                            String token = jsonArray.getJSONObject(0).getString("token");
                            String localParticipantName = Utils.user_current.getUsername();
                            String mode = "VIEWER";
                            boolean streamEnable = mode.equals("CONFERENCE");

                            VideoSDK.initialize(getApplicationContext());
                            VideoSDK.config(token);

                            meeting = VideoSDK.initMeeting(MeetingUserActivity.this, meetingId, localParticipantName, streamEnable, streamEnable, null, mode, false, null);

                            meeting.join();

                            meeting.addEventListener(new MeetingEventListener() {
                                @Override
                                public void onMeetingJoined() {
                                    if (meeting != null) {
                                        if (mode.equals("VIEWER")) {
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.main, new ViewerFragment(), "viewerFragment")
                                                    .commit();
                                        }
                                    }
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastHelper.showCustomToast(getApplicationContext(), "Lỗi kết nối đến server !!!");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
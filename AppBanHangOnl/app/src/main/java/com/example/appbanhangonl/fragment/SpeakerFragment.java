package com.example.appbanhangonl.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.activity.JoinActivity;
import com.example.appbanhangonl.activity.MeetingActivity;
import com.example.appbanhangonl.adapter.SpeakerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.lib.JsonUtils;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class SpeakerFragment extends Fragment {
    private static Activity mActivity;
    private static Context mContext;
    private static Meeting meeting;
    private boolean micEnabled = true;
    private boolean webcamEnabled = true;
    private boolean hlsEnabled = false;
    private final MeetingEventListener meetingEventListener = new MeetingEventListener() {
        @Override
        public void onMeetingLeft() {
            //unpin local participant
            meeting.getLocalParticipant().unpin("SHARE_AND_CAM");
            if (isAdded()) {
//                Intent intents = new Intent(mContext, JoinActivity.class);
//                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intents);
                mActivity.finish();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onHlsStateChanged(JSONObject HlsState) {
            if (HlsState.has("status")) {
                try {
//                    tvHlsState.setText("Trạng thái : " + HlsState.getString("status"));
                    if (HlsState.getString("status").equals("HLS_STARTED")) {
                        tvHlsState.setText("Trạng thái : " + "Đang phát");
                        hlsEnabled = true;
                        btnHls.setText("Dừng phát");
                    }
                    if (HlsState.getString("status").equals("HLS_STOPPED")) {
                        tvHlsState.setText("Trạng thái : " + "Đang dừng");
                        hlsEnabled = false;
                        btnHls.setText("Phát");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Button btnMic, btnWebcam, btnHls, btnLeave;
    private TextView tvMeetingId, tvHlsState;

    public SpeakerFragment() {

    }

    public static SpeakerFragment newInstance(String param1, String param2) {
        SpeakerFragment fragment = new SpeakerFragment();

        return fragment;
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mActivity = null;
        if (meeting != null) {
            meeting.removeAllListeners();
            meeting = null;
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity) {
            mActivity = (Activity) context;
            // nhận đối tượng cuộc họp từ Meeting Activity
            meeting = ((MeetingActivity) mActivity).getMeeting();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker, container, false);
        btnMic = view.findViewById(R.id.btnMic);
        btnWebcam = view.findViewById(R.id.btnWebcam);
        btnHls = view.findViewById(R.id.btnHLS);
        btnLeave = view.findViewById(R.id.btnLeave);

        tvMeetingId = view.findViewById(R.id.tvMeetingId);
        tvHlsState = view.findViewById(R.id.tvHlsState);

        if (meeting != null) {
            tvMeetingId.setText("Mã phòng : " + meeting.getMeetingId());
            setActionListeners();
            meeting.addEventListener(meetingEventListener);
            final RecyclerView rvParticipants = view.findViewById(R.id.rvParticipants);
            rvParticipants.setLayoutManager(new GridLayoutManager(mContext, 2));
            rvParticipants.setAdapter(new SpeakerAdapter(meeting));
        }
        return view;
    }

    private void setActionListeners() {
        btnMic.setOnClickListener(v -> {
            if (micEnabled) {
                meeting.muteMic();
                Toast.makeText(mContext, "Mic đang tắt", Toast.LENGTH_SHORT).show();
                btnMic.setText("Bật mic");
            } else {
                meeting.unmuteMic();
                Toast.makeText(mContext, "Mic đang bật", Toast.LENGTH_SHORT).show();
                btnMic.setText("Tắt mic");
            }
            micEnabled = !micEnabled;
        });

        btnWebcam.setOnClickListener(v -> {
            if (webcamEnabled) {
                meeting.disableWebcam();
                Toast.makeText(mContext, "Webcam đang tắt", Toast.LENGTH_SHORT).show();
                btnWebcam.setText("Bật webcam");
            } else {
                meeting.enableWebcam();
                Toast.makeText(mContext, "Webcam đang bật", Toast.LENGTH_SHORT).show();
                btnWebcam.setText("Tắt webcam");
            }
            webcamEnabled = !webcamEnabled;
        });

        btnLeave.setOnClickListener(v -> meeting.leave());

        btnHls.setOnClickListener(v -> {
            if (!hlsEnabled) {
                JSONObject config = new JSONObject();
                JSONObject layout = new JSONObject();
                JsonUtils.jsonPut(layout, "type", "SPOTLIGHT");
                JsonUtils.jsonPut(layout, "priority", "PIN");
                JsonUtils.jsonPut(layout, "gridSize", 4);
                JsonUtils.jsonPut(config, "layout", layout);
                JsonUtils.jsonPut(config, "orientation", "portrait");
                JsonUtils.jsonPut(config, "theme", "DARK");
                JsonUtils.jsonPut(config, "quality", "high");
                meeting.startHls(config);
            } else {
                meeting.stopHls();
            }
        });
    }
}
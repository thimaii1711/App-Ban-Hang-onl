package com.example.appbanhangonl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonl.R;

import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.Participant;
import live.videosdk.rtc.android.Stream;
import live.videosdk.rtc.android.VideoView;
import live.videosdk.rtc.android.listeners.MeetingEventListener;
import live.videosdk.rtc.android.listeners.ParticipantEventListener;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.PeerViewHolder> {
    private final Meeting meeting;
    private List<Participant> participantList = new ArrayList<>();

    public SpeakerAdapter(Meeting meeting) {
        this.meeting = meeting;

        updateParticipantList();

        // thêm Meeting Event listener tđể thu hút người tham gia tham gia/rời khỏi sự kiện trong cuộc họp
        meeting.addEventListener(new MeetingEventListener() {
            @Override
            public void onParticipantJoined(Participant participant) {
                // kiểm tra xem người tham gia có tham gia với tư cách là Host/Speaker hay không
                if (participant.getMode().equals("CONFERENCE")) {
                    // ghim người tham gia
                    participant.pin("SHARE_AND_CAM");
                    // thêm người tham gia vào Danh sách người tham gia
                    participantList.add(participant);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onParticipantLeft(Participant participant) {
                int pos = -1;
                for (int i = 0; i < participantList.size(); i++) {
                    if (participantList.get(i).getId().equals(participant.getId())) {
                        pos = i;
                        break;
                    }
                }
                if (participantList.contains(participant)) {
                    // bỏ ghim người tham gia đã rời khỏi cuộc họp
                    participant.unpin("SHARE_AND_CAM");
                    // xóa người tham gia khỏi danh sách
                    participantList.remove(participant);
                }
                if (pos >= 0) {
                    notifyItemRemoved(pos);
                }
            }
        });
    }

    private void updateParticipantList() {
        participantList = new ArrayList<>();

        // thêm người tham gia địa phương(Bạn) vào danh sách
        participantList.add(meeting.getLocalParticipant());

        // thêm người tham gia với tư cách là Host/Speaker
        Iterator<Participant> participants = meeting.getParticipants().values().iterator();
        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            final Participant participant = participants.next();
            if (participant.getMode().equals("CONFERENCE")) {
                // ghim người tham gia
                participant.pin("SHARE_AND_CAM");
                // thêm người tham gia vào Danh sách người tham gia
                participantList.add(participant);
            }
        }
    }

    @NonNull
    @Override
    public PeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remote_peer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeerViewHolder holder, int position) {
        Participant participant = participantList.get(position);

        holder.tvName.setText(participant.getDisplayName());

        // thêm luồng video ban đầu cho người tham gia vào 'VideoView'
        for (Map.Entry<String, Stream> entry : participant.getStreams().entrySet()) {
            Stream stream = entry.getValue();
            if (stream.getKind().equalsIgnoreCase("video")) {
                holder.participantView.setVisibility(View.VISIBLE);
                VideoTrack videoTrack = (VideoTrack) stream.getTrack();
                holder.participantView.addTrack(videoTrack);
                break;
            }
        }

        // thêm Listener vào người tham gia sẽ cập nhật bắt đầu hoặc dừng luồng video của người tham gia đó
        participant.addEventListener(new ParticipantEventListener() {
            @Override
            public void onStreamEnabled(Stream stream) {
                if (stream.getKind().equalsIgnoreCase("video")) {
                    holder.participantView.setVisibility(View.VISIBLE);
                    VideoTrack videoTrack = (VideoTrack) stream.getTrack();
                    holder.participantView.addTrack(videoTrack);
                }
            }

            @Override
            public void onStreamDisabled(Stream stream) {
                if (stream.getKind().equalsIgnoreCase("video")) {
                    holder.participantView.removeTrack();
                    holder.participantView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    static class PeerViewHolder extends RecyclerView.ViewHolder {
        // 'VideoView' để hiển thị luồng video
        // import live.videosdk.rtc.android.VideoView;
        public VideoView participantView;
        public TextView tvName;
        public View itemView;

        PeerViewHolder(@NonNull View view) {
            super(view);
            itemView = view;
            tvName = view.findViewById(R.id.tvName);
            participantView = view.findViewById(R.id.participantView);
        }
    }
}

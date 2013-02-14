package org.sugr.gearshift;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A fragment representing a single Torrent detail screen.
 * This fragment is either contained in a {@link TorrentListActivity}
 * in two-pane mode (on tablets) or a {@link TorrentDetailActivity}
 * on handsets.
 */
/* TODO: Use this class to hold the view pager. Place the options menu and loader here.
 * Create a new TorrentDetailPagerFragment class for the individual pages */
public class TorrentDetailPageFragment extends Fragment {
    private Torrent mTorrent;
    private List<String> mPriorityValues;

    private View.OnClickListener mExpanderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View image;
            View content;
            switch(v.getId()) {
                case R.id.torrent_detail_overview_expander:
                    image = v.findViewById(R.id.torrent_detail_overview_expander_image);
                    content = getView().findViewById(R.id.torrent_detail_overview_content);
                    break;
                case R.id.torrent_detail_limits_expander:
                    image = v.findViewById(R.id.torrent_detail_limits_expander_image);
                    content = getView().findViewById(R.id.torrent_detail_limits_content);
                    break;
                case R.id.torrent_detail_advanced_expander:
                    image = v.findViewById(R.id.torrent_detail_advanced_expander_image);
                    content = getView().findViewById(R.id.torrent_detail_advanced_content);
                    break;
                default:
                    return;
            }

            if (content.getVisibility() == View.GONE) {
                content.setVisibility(View.VISIBLE);
                image.setBackgroundResource(R.drawable.ic_section_collapse);
            } else {
                content.setVisibility(View.GONE);
                image.setBackgroundResource(R.drawable.ic_section_expand);
            }

        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TorrentDetailPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(TorrentDetailFragment.ARG_PAGE_POSITION)) {
            int position = getArguments().getInt(TorrentDetailFragment.ARG_PAGE_POSITION);
            ArrayList<Torrent> torrents = ((TransmissionSessionInterface) getActivity()).getTorrents();

            if (position < torrents.size())
                mTorrent = torrents.get(position);
        }

        mPriorityValues = Arrays.asList(getResources().getStringArray(R.array.torrent_priority_values));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_torrent_detail_page, container, false);

        if (mTorrent == null) return root;

        ((TextView) root.findViewById(R.id.torrent_detail_title)).setText(mTorrent.getName());


        root.findViewById(R.id.torrent_detail_overview_expander).setOnClickListener(mExpanderListener);
        root.findViewById(R.id.torrent_detail_limits_expander).setOnClickListener(mExpanderListener);
        root.findViewById(R.id.torrent_detail_advanced_expander).setOnClickListener(mExpanderListener);

        long now = new Timestamp(new Date().getTime()).getTime() / 1000;
        if (mTorrent.getMetadataPercentComplete() == 1) {
            ((TextView) root.findViewById(R.id.torrent_have)).setText(
                String.format(
                    getString(R.string.torrent_have_format),
                    Torrent.readableFileSize(mTorrent.getHaveValid() > 0
                            ? mTorrent.getHaveValid() : mTorrent.getSizeWhenDone() - mTorrent.getLeftUntilDone()),
                    Torrent.readableFileSize(mTorrent.getSizeWhenDone()),
                    Torrent.readablePercent(100 * (
                        mTorrent.getSizeWhenDone() > 0
                            ? (mTorrent.getSizeWhenDone() - mTorrent.getLeftUntilDone()) / mTorrent.getSizeWhenDone()
                            : 1
                    ))
                ));
            ((TextView) root.findViewById(R.id.torrent_downloaded)).setText(
                Torrent.readableFileSize(mTorrent.getDownloadedEver())
            );
            ((TextView) root.findViewById(R.id.torrent_uploaded)).setText(
                Torrent.readableFileSize(mTorrent.getUploadedEver())
            );
            int state = R.string.none;
            switch(mTorrent.getStatus()) {
                case Torrent.Status.STOPPED:
                    state = R.string.status_stopped;
                    break;
                case Torrent.Status.CHECK_WAITING:
                    state = R.string.status_check_waiting;
                    break;
                case Torrent.Status.CHECKING:
                    state = R.string.status_checking;
                    break;
                case Torrent.Status.DOWNLOAD_WAITING:
                    state = R.string.status_download_waiting;
                    break;
                case Torrent.Status.DOWNLOADING:
                    state = mTorrent.getMetadataPercentComplete() < 0
                        ? R.string.status_downloading_metadata
                        : R.string.status_downloading;
                    break;
                case Torrent.Status.SEED_WAITING:
                    state = R.string.status_seed_waiting;
                    break;
                case Torrent.Status.SEEDING:
                    state = R.string.status_seeding;
                    break;
            }
            ((TextView) root.findViewById(R.id.torrent_state)).setText(state);
            ((TextView) root.findViewById(R.id.torrent_running_time)).setText(
                mTorrent.getStatus() == Torrent.Status.STOPPED
                    ? getString(R.string.status_stopped)
                    : Torrent.readableRemainingTime(now - mTorrent.getStartDate(), getActivity())
            );
            ((TextView) root.findViewById(R.id.torrent_remaining_time)).setText(
                mTorrent.getEta() < 0
                    ? getString(R.string.unknown)
                    : Torrent.readableRemainingTime(mTorrent.getEta(), getActivity())
            );
            long lastActive = now - mTorrent.getActivityDate();
            ((TextView) root.findViewById(R.id.torrent_last_activity)).setText(
                lastActive < 0
                    ? getString(R.string.unknown)
                    : lastActive < 5
                        ? getString(R.string.torrent_active_now)
                        : Torrent.readableRemainingTime(lastActive, getActivity())
            );
        } else {
            ((TextView) root.findViewById(R.id.torrent_have)).setText(R.string.none);
        }

        /* TODO: actually use whatever torrent priority is set */
        ((Spinner) root.findViewById(R.id.torrent_priority)).setSelection(mPriorityValues.indexOf("normal"));

        CheckBox check = (CheckBox) root.findViewById(R.id.torrent_limit_download_check);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                root.findViewById(R.id.torrent_limit_download).setEnabled(isChecked);
            }
        });
        root.findViewById(R.id.torrent_limit_download).setEnabled(check.isChecked());

        check = (CheckBox) root.findViewById(R.id.torrent_limit_upload_check);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                root.findViewById(R.id.torrent_limit_upload).setEnabled(isChecked);
            }
        });
        root.findViewById(R.id.torrent_limit_upload).setEnabled(check.isChecked());

        ((Spinner) root.findViewById(R.id.torrent_seed_ratio_mode)).setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String[] values = getResources().getStringArray(R.array.torrent_seed_ratio_mode_values);

                        root.findViewById(R.id.torrent_seed_ratio_limit).setEnabled(values[pos].equals("user"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        /* TODO: use the torrent global limits override */
        check = (CheckBox) root.findViewById(R.id.torrent_global_limits);
        check.setChecked(true);


        return root;
    }
}

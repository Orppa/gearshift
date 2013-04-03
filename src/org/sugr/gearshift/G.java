package org.sugr.gearshift;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;

class G {
    public static final String PREF_UPDATE_ACTIVE = "update_active_torrents";
    public static final String PREF_FULL_UPDATE = "full_update";
    public static final String PREF_UPDATE_INTERVAL = "update_interval";
    public static final String PREF_START_PAUSED = "start_paused";
    public static final String PREF_DELETE_LOCAL_TORRENT = "delete_local_torrent_file";

    public static final String PREF_PROFILES = "profiles";
    public static final String PREF_CURRENT_PROFILE = "default_profile";

    public static final String PREF_NAME = "profile_name";
    public static final String PREF_HOST = "profile_host";
    public static final String PREF_PORT = "profile_port";
    public static final String PREF_PATH = "profile_path";
    public static final String PREF_USER = "profile_username";
    public static final String PREF_PASS = "profile_password";
    public static final String PREF_SSL = "profile_use_ssl";
    public static final String PREF_TIMEOUT = "profile_timeout";
    public static final String PREF_RETRIES = "profile_retries";
    public static final String PREF_DIRECTORIES = "profile_directories";
    public static final String PREF_PREFIX = "profile_";

    public static final String PREF_LIST_SORT_BY = "torrents_sort_by";
    public static final String PREF_LIST_SORT_ORDER = "torrents_sort_order";
    public static final String PREF_LIST_FILTER = "torrents_filter";
    public static final String PREF_LIST_DIRECTORY = "torrents_directory";
    public static final String PREF_LIST_SEARCH = "torrents_search";

    public static final String PREF_FILTER_DOWNLOADING = "filter_downloading";
    public static final String PREF_FILTER_SEEDING = "filter_seeding";
    public static final String PREF_FILTER_PAUSED = "filter_paused";
    public static final String PREF_FILTER_COMPLETE = "filter_complete";
    public static final String PREF_FILTER_INCOMPLETE = "filter_incomplete";
    public static final String PREF_FILTER_ACTIVE = "filter_active";
    public static final String PREF_FILTER_CHECKING = "filter_checking";
    public static final String PREF_FILTER_DIRECTORIES = "filter_directories";

    public static final String PREF_SORT_NAME = "sort_name";
    public static final String PREF_SORT_SIZE = "sort_size";
    public static final String PREF_SORT_STATUS = "sort_status";
    public static final String PREF_SORT_ACTIVITY = "sort_activity";
    public static final String PREF_SORT_AGE = "sort_age";
    public static final String PREF_SORT_PROGRESS = "sort_progress";
    public static final String PREF_SORT_RATIO = "sort_ratio";
    public static final String PREF_SORT_LOCATION = "sort_ratio";
    public static final String PREF_SORT_PEERS = "sort_peers";
    public static final String PREF_SORT_RATE_DOWNLOAD = "sort_rate_download";
    public static final String PREF_SORT_RATE_UPLOAD = "sort_rate_upload";
    public static final String PREF_SORT_QUEUE = "sort_queue";

    public static final String ARG_JSON_TORRENTS = "json_torrents";
    public static final String ARG_JSON_SESSION = "json_session";
    public static final String ARG_PROFILE = "profile";
    public static final String ARG_PROFILE_ID = "profile_id";

    public static final String DETAIL_FRAGMENT_TAG = "detail_fragment";
    public static final String ARG_PAGE_POSITION = "page_position";

    public static final int PROFILES_LOADER_ID = 1;
    public static final int SESSION_LOADER_ID = 2;

    private static final boolean DEBUG = false;
    private static final String LogTag = "GearShift";

    public static enum FilterBy {
        ALL, DOWNLOADING, SEEDING, PAUSED, COMPLETE, INCOMPLETE,
        ACTIVE, CHECKING
    };

    public static enum SortBy {
        NAME, SIZE, STATUS, ACTIVITY, AGE, PROGRESS, RATIO, LOCATION,
        PEERS, RATE_DOWNLOAD, RATE_UPLOAD, QUEUE
    };

    public static enum SortOrder {
        ASCENDING, DESCENDING
    };

    public static Comparator<String> SIMPLE_STRING_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String lhs, String rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            } else if (lhs == null) {
                return -1;
            } else if (rhs == null) {
                return 1;
            } else {
                return lhs.compareToIgnoreCase(rhs);
            }
        }

    };

    public static void logE(String message, Object[] args, Exception e) {
        Log.e(LogTag, String.format(message, args), e);
    }

    public static void logE(String message, Exception e) {
        Log.e(LogTag, message, e);
    }

    public static void logD(String message, Object[] args) {
        if (!DEBUG) return;

        Log.d(LogTag, String.format(message, args));
    }

    public static void logD(String message) {
        if (!DEBUG) return;

        Log.d(LogTag, message);
    }

    public static void logDTrace() {
        if (!DEBUG) return;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Throwable t = new Throwable();

        t.printStackTrace(pw);
        Log.d(LogTag, sw.toString());
    }


    public static String readableFileSize(long size) {
        if(size <= 0) return "0 B";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String readablePercent(float percent) {
        if (percent < 10.0) {
            return new DecimalFormat("#.##").format(percent);
        } else if (percent < 100.0) {
            return new DecimalFormat("#.#").format(percent);
        } else {
            return new DecimalFormat("#").format(percent);
        }
    }

    public static String readableRemainingTime(long eta, Context context) {
        if (eta < 0) {
            return context.getString(R.string.traffic_remaining_time_unknown);
        }
        int days = (int) Math.floor(eta / 86400);
        int hours = (int) Math.floor((eta % 86400) / 3600);
        int minutes = (int) Math.floor((eta % 3600) / 60);
        int seconds = (int) Math.floor(eta % 60);
        String d = Integer.toString(days) + ' ' + context.getString(days > 1 ? R.string.time_days : R.string.time_day);
        String h = Integer.toString(hours) + ' ' + context.getString(hours > 1 ? R.string.time_hours : R.string.time_hour);
        String m = Integer.toString(minutes) + ' ' + context.getString(minutes > 1 ? R.string.time_minutes : R.string.time_minute);
        String s = Integer.toString(seconds) + ' ' + context.getString(seconds > 1 ? R.string.time_seconds : R.string.time_second);

        if (days > 0) {
            if (days >= 4 || hours == 0)
                return d;
            return d + ", " + h;
        }

        if (hours > 0) {
            if (hours >= 4 || minutes == 0)
                return h;
            return h + ", " + m;
        }

        if (minutes > 0) {
            if (minutes >= 4 || seconds == 0)
                return m;
            return m + ", " + s;
        }

        return s;
    }
}
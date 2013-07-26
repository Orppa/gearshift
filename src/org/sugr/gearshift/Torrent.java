package org.sugr.gearshift;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

import org.sugr.gearshift.TransmissionSessionManager.Exclude;

public class Torrent implements Parcelable {
    @Exclude public static final class SetterFields {
        public static final String DOWNLOAD_LIMIT = "downloadLimit";
        public static final String DOWNLOAD_LIMITED = "downloadLimited";
        public static final String PEER_LIMIT = "peer-limit";
        public static final String QUEUE_POSITION = "queuePosition";
        public static final String SEED_RATIO_LIMIT = "seedRatioLimit";
        public static final String SEED_RATIO_MODE = "seedRatioMode";
        public static final String SESSION_LIMITS = "honorsSessionLimits";
        public static final String TORRENT_PRIORITY = "bandwidthPriority";
        public static final String UPLOAD_LIMIT = "uploadLimit";
        public static final String UPLOAD_LIMITED = "uploadLimited";

        public static final String FILES_WANTED = "files-wanted";
        public static final String FILES_UNWANTED = "files-unwanted";
        public static final String FILES_HIGH = "priority-high";
        public static final String FILES_NORMAL = "priority-normal";
        public static final String FILES_LOW = "priority-low";

    }

    @Exclude public static final class AddFields {
        public static final String URI = "filename";
        public static final String META = "metainfo";
        public static final String LOCATION = "download-dir";
        public static final String PAUSED = "paused";
    }

    @SerializedName("id") private int mId;
    @SerializedName("status") private int mStatus = Status.STOPPED;

    @SerializedName("name") private String mName = "";

    @SerializedName("error") private int mError;
    @SerializedName("errorString") private String mErrorString;

    @SerializedName("metadataPercentComplete") private float mMetadataPercentComplete = 0;
    /* User selected */
    @SerializedName("percentDone") private float mPercentDone = 0;

    @SerializedName("eta") private long mEta;

    @SerializedName("isFinished") private boolean mFinished = false;
    @SerializedName("isStalled") private boolean mStalled = true;

    @SerializedName("peersConnected") private int mPeersConnected = 0;
    @SerializedName("peersGettingFromUs") private int mPeersGettingFromUs = 0;
    @SerializedName("peersSendingToUs") private int mPeersSendingToUs = 0;

    /* In bytes */
    @SerializedName("leftUntilDone") private long mLeftUntilDone;
    /* 0 .. leftUntilDone */
    @SerializedName("desiredAvailable")  private long mDesiredAvailable;

    @SerializedName("totalSize") private long mTotalSize;
    @SerializedName("sizeWhenDone") private long mSizeWhenDone;

    @SerializedName("rateDownload") private long mRateDownload;
    @SerializedName("rateUpload") private long mRateUpload;

    @SerializedName(SetterFields.QUEUE_POSITION) private int mQueuePosition;

    @SerializedName("recheckProgress") private float mRecheckProgress;

    @SerializedName(SetterFields.SEED_RATIO_MODE) private int mSeedRatioMode;
    @SerializedName(SetterFields.SEED_RATIO_LIMIT) private float mSeedRatioLimit;

    @SerializedName("uploadedEver") private long mUploadedEver;
    @SerializedName("uploadRatio") private float mUploadRatio;

    @SerializedName("addedDate") private long mAddedDate = 0;
    @SerializedName("doneDate") private long mDoneDate;
    @SerializedName("startDate") private long mStartDate;
    @SerializedName("activityDate") private long mActivityDate;

    @SerializedName("corruptEver") private long mCorruptEver;

    @SerializedName("downloadDir") private String mDownloadDir;
    @SerializedName("downloadedEver") private long mDownloadedEver;

    @SerializedName("haveUnchecked") private long mHaveUnchecked;
    @SerializedName("haveValid") private long mHaveValid;

    @SerializedName("trackers") private Tracker[] mTrackers;
    @SerializedName("trackerStats") private TrackerStats[] mTrackerStats;

    @SerializedName("comment") private String mComment;
    @SerializedName("creator") private String mCreator;
    @SerializedName("dateCreated") private long mDateCreated;
    @SerializedName("files") private File[] mFiles;
    @SerializedName("hashString") private String mHashString;
    @SerializedName("isPrivate") private boolean mPrivate;
    @SerializedName("pieceCount") private int mPieceCount;
    @SerializedName("pieceSize") private long mPieceSize;

    @SerializedName(SetterFields.TORRENT_PRIORITY) private int mTorrentPriority;
    @SerializedName(SetterFields.DOWNLOAD_LIMIT) private long mDownloadLimit;
    @SerializedName(SetterFields.DOWNLOAD_LIMITED) private boolean mDownloadLimited;
    @SerializedName("fileStats") private FileStats[] mFileStats;
    @SerializedName(SetterFields.SESSION_LIMITS) private boolean mHonorsSessionLimits;
    @SerializedName(SetterFields.UPLOAD_LIMIT) private long mUploadLimit;
    @SerializedName(SetterFields.UPLOAD_LIMITED) private boolean mUploadLimited;
    @SerializedName("webseedsSendingToUs") private int mWebseedsSendingToUs;
    @SerializedName("peers") private Peer[] mPeers;
    @SerializedName(SetterFields.PEER_LIMIT) private int mPeerLimit;

    @Exclude private Spanned mTrafficText;
    @Exclude private Spanned mStatusText;
    @Exclude private TransmissionSession mSession;

    // https://github.com/killemov/Shift/blob/master/shift.js#L864
    @Exclude public static class Status {
        public final static int ALL = -1;
        public final static int STOPPED = 0;
        public final static int CHECK_WAITING = 1;
        public final static int CHECKING = 2;
        public final static int DOWNLOAD_WAITING = 3;
        public final static int DOWNLOADING = 4;
        public final static int SEED_WAITING = 5;
        public final static int SEEDING = 6;
    };

    @Exclude private static final int NEW_STATUS_RPC_VERSION = 14;
    @Exclude public static class OldStatus {
        public final static int CHECK_WAITING = 1;
        public final static int CHECKING = 2;
        public final static int DOWNLOADING = 4;
        public final static int SEEDING = 8;
        public final static int STOPPED = 16;
    };

    // http://packages.python.org/transmissionrpc/reference/transmissionrpc.html
    @Exclude public static class SeedRatioMode {
        public final static int GLOBAL_LIMIT = 0;
        public final static int TORRENT_LIMIT = 1;
        public final static int NO_LIMIT = 2;
    }

    @Exclude public static class Error {
        public static final int OK = 0;
        public static final int TRACKER_WARNING = 1;
        public static final int TRACKER_ERROR = 2;
        public static final int LOCAL_ERROR = 3;
    }

    @Exclude public static class Priority {
        public static final int LOW = -1;
        public static final int NORMAL = 0;
        public static final int HIGH = 1;
    }

    @Exclude public static class Fields {
        /*
         * commonly used fields which only need to be loaded once, either on
         * startup or when a magnet finishes downloading its metadata finishes
         * downloading its metadata
         * */
        public static final String[] METADATA = { "addedDate", "name", "totalSize" };

        // commonly used fields which need to be periodically refreshed
        public static final String[] STATS = {
            "id", "error", "errorString", "eta", "isFinished", "isStalled",
            "leftUntilDone", "metadataPercentComplete", "peersConnected",
            "peersGettingFromUs", "peersSendingToUs", "percentDone",
            SetterFields.QUEUE_POSITION, "rateDownload", "rateUpload",
            "recheckProgress", SetterFields.SEED_RATIO_MODE, SetterFields.SEED_RATIO_LIMIT,
            "sizeWhenDone", "status", "trackers", "uploadedEver",
            "uploadRatio", "downloadDir"
        };

        // fields used by the inspector which only need to be loaded once
        public static final String[] INFO_EXTRA = {
            "comment", "creator", "dateCreated", "files", "hashString",
            "isPrivate", "pieceCount", "pieceSize"
        };

        // fields used in the inspector which need to be periodically refreshed
        public static final String[] STATS_EXTRA = {
            "activityDate", SetterFields.TORRENT_PRIORITY, "corruptEver",
            "desiredAvailable", "downloadedEver", SetterFields.DOWNLOAD_LIMIT,
            SetterFields.DOWNLOAD_LIMITED, "fileStats", "haveUnchecked",
            "haveValid", SetterFields.SESSION_LIMITS, SetterFields.PEER_LIMIT, "peers",
            "startDate", "trackerStats", SetterFields.UPLOAD_LIMIT,
            SetterFields.UPLOAD_LIMITED, "webseedsSendingToUs"
        };
    };

    @Exclude private static final int SEED = 0x21;

    public static class Tracker {
        @SerializedName("id") private int mId;
        @SerializedName("announce") private String mAnnounce;
        @SerializedName("scrape") private String mScrape;
        @SerializedName("tier") private int mTier;

        public int getId() {
            return mId;
        }

        public String getAnnounce() {
            return mAnnounce;
        }

        public String getScrape() {
            return mScrape;
        }

        public int getTier() {
            return mTier;
        }

        public void setId(int id) {
            mId = id;
        }

        public void setAnnounce(String announce) {
            mAnnounce = announce;
        }

        public void setScrape(String scrape) {
            mScrape = scrape;
        }

        public void setTier(int tier) {
            mTier = tier;
        }
    }

    public static class TrackerStats {
        @SerializedName("id") private int mId;

        @SerializedName("hasAnnounced") private boolean mAnnounced;
        @SerializedName("lastAnnounceTime") private long mLastAnnouceTime;
        @SerializedName("lastAnnounceSucceeded") private boolean mLastAnnouceSucceeded;
        @SerializedName("lastAnnouncePeerCount") private int mLastAnnoucePeerCount;
        @SerializedName("lastAnnounceResult") private String mLastAnnouceResult;

        @SerializedName("hasScraped") private boolean mScraped;
        @SerializedName("lastScrapeTime") private long mLastScrapeTime;
        @SerializedName("lastScrapeSucceeded") private boolean mLastScrapeSucceeded;
        @SerializedName("lastScrapeResult") private String mLastScrapeResult;

        @SerializedName("seederCount") private int mSeederCount;
        @SerializedName("leecherCount") private int mLeecherCount;

        public int getId() {
            return mId;
        }

        public boolean hasAnnounced() {
            return mAnnounced;
        }

        public long getLastAnnouceTime() {
            return mLastAnnouceTime;
        }

        public boolean hasLastAnnouceSucceeded() {
            return mLastAnnouceSucceeded;
        }

        public int getLastAnnoucePeerCount() {
            return mLastAnnoucePeerCount;
        }

        public String getLastAnnouceResult() {
            return mLastAnnouceResult;
        }

        public boolean hasScraped() {
            return mScraped;
        }

        public long getLastScrapeTime() {
            return mLastScrapeTime;
        }

        public boolean hasLastScrapeSucceeded() {
            return mLastScrapeSucceeded;
        }

        public String getLastScrapeResult() {
            return mLastScrapeResult;
        }

        public int getSeederCount() {
            return mSeederCount;
        }

        public int getLeecherCount() {
            return mLeecherCount;
        }

        public void setId(int mId) {
            mId = mId;
        }

        public void setAnnounced(boolean mAnnounced) {
            mAnnounced = mAnnounced;
        }

        public void setLastAnnouceTime(long mLastAnnouceTime) {
            mLastAnnouceTime = mLastAnnouceTime;
        }

        public void setLastAnnouceSucceeded(boolean mLastAnnouceSucceeded) {
            mLastAnnouceSucceeded = mLastAnnouceSucceeded;
        }

        public void setLastAnnoucePeerCount(int mLastAnnoucePeerCount) {
            mLastAnnoucePeerCount = mLastAnnoucePeerCount;
        }

        public void setLastAnnouceResult(String mLastAnnouceResult) {
            mLastAnnouceResult = mLastAnnouceResult;
        }

        public void setScraped(boolean mScraped) {
            mScraped = mScraped;
        }

        public void setLastScrapeTime(long mLastScrapeTime) {
            mLastScrapeTime = mLastScrapeTime;
        }

        public void setLastScrapeSucceeded(boolean mLastScrapeSucceeded) {
            mLastScrapeSucceeded = mLastScrapeSucceeded;
        }

        public void setLastScrapeResult(String mLastScrapeResult) {
            mLastScrapeResult = mLastScrapeResult;
        }

        public void setSeederCount(int mSeederCount) {
            mSeederCount = mSeederCount;
        }

        public void setLeecherCount(int mLeecherCount) {
            mLeecherCount = mLeecherCount;
        }
    }

    public static class File {
        @SerializedName("bytesCompleted") private long mBytesCompleted;
        @SerializedName("length") private long mLength;
        @SerializedName("name") private String mName;

        public long getBytesCompleted() {
            return mBytesCompleted;
        }

        public long getLength() {
            return mLength;
        }

        public String getName() {
            return mName;
        }

        public void setBytesCompleted(long bytes) {
            mBytesCompleted = bytes;
        }

        public void setLength(long length) {
            mLength = length;
        }

        public void setName(String name) {
            mName = name;
        }
    }

    public static class FileStats {
        @SerializedName("bytesCompleted") private long mBytesCompleted;
        @SerializedName("wanted") private boolean mWanted;
        @SerializedName("priority") private int mPriority = Priority.NORMAL;

        public long getBytesCompleted() {
            return mBytesCompleted;
        }

        public int getPriority() {
            return mPriority;
        }

        public boolean isWanted() {
            return mWanted;
        }

        public void setBytesCompleted(long bytes) {
            mBytesCompleted = bytes;
        }

        public void setPriority(int priority) {
            mPriority = priority;
        }

        public void setWanted(boolean wanted) {
            mWanted = wanted;
        }
    }

    public static class Peer {
        @SerializedName("address") private String mAddress;
        @SerializedName("clientName") private String mClientName;
        @SerializedName("clientIsChoked") private boolean mClientChoked;
        @SerializedName("clientIsInterested") private boolean mClientInterested;
        @SerializedName("isDownloadingFrom") private boolean mDownloadingFrom;
        @SerializedName("isEncrypted") private boolean mEncrypted;
        @SerializedName("isIncoming") private boolean mIncoming;
        @SerializedName("isUploadingTo") private boolean mUploadingTo;
        @SerializedName("peerIsChoked") private boolean mPeerChoked;
        @SerializedName("peerIsInterested") private boolean mPeerInterested;
        @SerializedName("port") private int mPort;
        @SerializedName("progress") private float mProgress;
        @SerializedName("rateToClient") private long mRateToClient;
        @SerializedName("rateToPeer") private long mRateToPeer;

        public String getAddress() {
            return mAddress;
        }
        public String getClientName() {
            return mClientName;
        }
        public boolean isClientChoked() {
            return mClientChoked;
        }
        public boolean isClientInterested() {
            return mClientInterested;
        }
        public boolean isDownloadingFrom() {
            return mDownloadingFrom;
        }
        public boolean isEncrypted() {
            return mEncrypted;
        }
        public boolean isIncoming() {
            return mIncoming;
        }
        public boolean isUploadingTo() {
            return mUploadingTo;
        }
        public boolean isPeerChoked() {
            return mPeerChoked;
        }
        public boolean isPeerInterested() {
            return mPeerInterested;
        }
        public int getPort() {
            return mPort;
        }
        public float getProgress() {
            return mProgress;
        }
        public long getRateToClient() {
            return mRateToClient;
        }
        public long getRateToPeer() {
            return mRateToPeer;
        }
        public void setAddress(String address) {
            mAddress = address;
        }
        public void setClientName(String clientName) {
            mClientName = clientName;
        }
        public void setClientChoked(boolean clientChoked) {
            mClientChoked = clientChoked;
        }
        public void setClientInterested(boolean clientInterested) {
            mClientInterested = clientInterested;
        }
        public void setDownloadingFrom(boolean downloadingFrom) {
            mDownloadingFrom = downloadingFrom;
        }
        public void setEncrypted(boolean encrypted) {
            mEncrypted = encrypted;
        }
        public void setIncoming(boolean incoming) {
            mIncoming = incoming;
        }
        public void setUploadingTo(boolean uploadingTo) {
            mUploadingTo = uploadingTo;
        }
        public void setPeerChoked(boolean peerChoked) {
            mPeerChoked = peerChoked;
        }
        public void setPeerInterested(boolean peerInterested) {
            mPeerInterested = peerInterested;
        }
        public void setPort(int port) {
            mPort = port;
        }
        public void setProgress(float progress) {
            mProgress = progress;
        }
        public void setRateToClient(long rateToClient) {
            mRateToClient = rateToClient;
        }
        public void setRateToPeer(long rateToPeer) {
            mRateToPeer = rateToPeer;
        }
    }

    public Torrent(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public int getStatus() {
        if (mSession != null && mSession.getRPCVersion() < NEW_STATUS_RPC_VERSION) {
            switch(mStatus) {
                case Torrent.OldStatus.CHECK_WAITING:
                    return Torrent.Status.CHECK_WAITING;
                case Torrent.OldStatus.CHECKING:
                    return Torrent.Status.CHECKING;
                case Torrent.OldStatus.DOWNLOADING:
                    return Torrent.Status.DOWNLOADING;
                case Torrent.OldStatus.SEEDING:
                    return Torrent.Status.SEEDING;
                case Torrent.OldStatus.STOPPED:
                    return Torrent.Status.STOPPED;
                default:
                    return mStatus;
            }
        }
        return mStatus;
    }

    public int getStatus(boolean skipConversion) {
        if (skipConversion) {
            return mStatus;
        } else {
            return getStatus();
        }
    }

    public String getName() {
        return mName;
    }

    public int getError() {
        return mError;
    }

    public String getErrorString() {
        return mErrorString;
    }

    public float getMetadataPercentComplete() {
        return mMetadataPercentComplete;
    }

    public float getPercentDone() {
        return mPercentDone;
    }

    public long getEta() {
        return mEta;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public boolean isStalled() {
        return mStalled;
    }

    public int getPeersConnected() {
        return mPeersConnected;
    }

    public int getPeersGettingFromUs() {
        return mPeersGettingFromUs;
    }

    public int getPeersSendingToUs() {
        return mPeersSendingToUs;
    }

    public long getLeftUntilDone() {
        return mLeftUntilDone;
    }

    public long getDesiredAvailable() {
        return mDesiredAvailable;
    }

    public long getTotalSize() {
        return mTotalSize;
    }

    public long getSizeWhenDone() {
        return mSizeWhenDone;
    }

    public long getRateDownload() {
        return mRateDownload;
    }

    public long getRateUpload() {
        return mRateUpload;
    }

    public int getQueuePosition() {
        return mQueuePosition;
    }

    public float getRecheckProgress() {
        return mRecheckProgress;
    }

    public int getSeedRatioMode() {
        return mSeedRatioMode;
    }

    public float getSeedRatioLimit() {
        return mSeedRatioLimit;
    }

    public long getUploadedEver() {
        return mUploadedEver;
    }

    public float getUploadRatio() {
        return mUploadRatio;
    }

    public long getAddedDate() {
        return mAddedDate;
    }

    public long getDoneDate() {
        return mDoneDate;
    }

    public long getStartDate() {
        return mStartDate;
    }

    public long getActivityDate() {
        return mActivityDate;
    }

    public long getCorruptEver() {
        return mCorruptEver;
    }

    public String getDownloadDir() {
        return mDownloadDir;
    }

    public long getDownloadedEver() {
        return mDownloadedEver;
    }

    public long getHaveUnchecked() {
        return mHaveUnchecked;
    }

    public long getHaveValid() {
        return mHaveValid;
    }

    public Tracker[] getTrackers() {
        return mTrackers;
    }

    public TrackerStats[] getTrackerStats() {
        return mTrackerStats;
    }

    public int getTorrentPriority() {
        return mTorrentPriority;
    }

    public String getComment() {
        return mComment;
    }

    public String getCreator() {
        return mCreator;
    }

    public long getDateCreated() {
        return mDateCreated;
    }

    public long getDownloadLimit() {
        return mDownloadLimit;
    }

    public boolean isDownloadLimited() {
        return mDownloadLimited;
    }

    public File[] getFiles() {
        return mFiles;
    }

    public String getHashString() {
        return mHashString;
    }

    public boolean areSessionLimitsHonored() {
        return mHonorsSessionLimits;
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public int getPieceCount() {
        return mPieceCount;
    }

    public long getPieceSize() {
        return mPieceSize;
    }

    public long getUploadLimit() {
        return mUploadLimit;
    }

    public boolean isUploadLimited() {
        return mUploadLimited;
    }

    public FileStats[] getFileStats() {
        return mFileStats;
    }

    public int getWebseedsSendingToUs() {
        return mWebseedsSendingToUs;
    }

    public Peer[] getPeers() {
        return mPeers;
    }

    public int getPeerLimit() {
        return mPeerLimit;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setError(int error) {
        mError = error;
    }

    public void setErrorString(String errorString) {
        mErrorString = errorString;
    }

    public void setMetadataPercentComplete(float metadataPercentComplete) {
        mMetadataPercentComplete = metadataPercentComplete;
    }

    public void setPercentDone(float percentDone) {
        mPercentDone = percentDone;
    }

    public void setEta(long eta) {
        mEta = eta;
    }

    public void setFinished(boolean finished) {
        mFinished = finished;
    }

    public void setStalled(boolean stalled) {
        mStalled = stalled;
    }

    public void setPeersConnected(int peersConnected) {
        mPeersConnected = peersConnected;
    }

    public void setPeersGettingFromUs(int peersGettingFromUs) {
        mPeersGettingFromUs = peersGettingFromUs;
    }

    public void setPeersSendingToUs(int peersSendingToUs) {
        mPeersSendingToUs = peersSendingToUs;
    }

    public void setLeftUntilDone(long leftUntilDone) {
        mLeftUntilDone = leftUntilDone;
    }

    public void setDesiredAvailable(long desiredAvailable) {
        mDesiredAvailable = desiredAvailable;
    }

    public void setTotalSize(long totalSize) {
        mTotalSize = totalSize;
    }

    public void setSizeWhenDone(long sizeWhenDone) {
        mSizeWhenDone = sizeWhenDone;
    }

    public void setRateDownload(long rateDownload) {
        mRateDownload = rateDownload;
    }

    public void setRateUpload(long rateUpload) {
        mRateUpload = rateUpload;
    }

    public void setQueuePosition(int queuePosition) {
        mQueuePosition = queuePosition;
    }

    public void setRecheckProgress(float recheckProgress) {
        mRecheckProgress = recheckProgress;
    }

    public void setSeedRatioMode(int seedRatioMode) {
        mSeedRatioMode = seedRatioMode;
    }

    public void setSeedRatioLimit(float seedRatioLimit) {
        mSeedRatioLimit = seedRatioLimit;
    }

    public void setUploadedEver(long uploadedEver) {
        mUploadedEver = uploadedEver;
    }

    public void setUploadRatio(float uploadRatio) {
        mUploadRatio = uploadRatio;
    }

    public void setAddedDate(long addedDate) {
        mAddedDate = addedDate;
    }

    public void setDoneDate(long doneDate) {
        mDoneDate = doneDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public void setActivityDate(long activityDate) {
        mActivityDate = activityDate;
    }

    public void setCorruptEver(long corruptEver) {
        mCorruptEver = corruptEver;
    }

    public void setDownloadDir(String downloadDir) {
        mDownloadDir = downloadDir;
    }

    public void setDownloadedEver(long downloadedEver) {
        mDownloadedEver = downloadedEver;
    }

    public void setHaveUnchecked(long haveUnchecked) {
        mHaveUnchecked = haveUnchecked;
    }

    public void setHaveValid(long haveValid) {
        mHaveValid = haveValid;
    }

    public void setTrackers(Tracker[] trackers) {
        mTrackers = trackers;
    }

    public void setTrackerStats(TrackerStats[] stats) {
        mTrackerStats = stats;
    }

    public void setTorrentPriority(int priority) {
        mTorrentPriority = priority;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public void setCreator(String creator) {
        mCreator = creator;
    }

    public void setDateCreated(long dateCreated) {
        mDateCreated = dateCreated;
    }

    public void setDownloadLimit(long limit) {
        mDownloadLimit = limit;
    }

    public void setDownloadLimited(boolean limited) {
        mDownloadLimited = limited;
    }

    public void setFiles(File[] files) {
        mFiles = files;
    }

    public void setHashString(String hashString) {
        mHashString = hashString;
    }

    public void setHonorsSessionLimits(boolean limits) {
        mHonorsSessionLimits = limits;
    }

    public void setPrivate(boolean priv) {
        mPrivate = priv;
    }

    public void setPieceCount(int pieceCount) {
        mPieceCount = pieceCount;
    }

    public void setPieceSize(long pieceSize) {
        mPieceSize = pieceSize;
    }

    public void setUploadLimit(long limit) {
        mUploadLimit = limit;
    }

    public void setUploadLimited(boolean limited) {
        mUploadLimited = limited;
    }

    public void setFileStats(FileStats[] fileStats) {
        mFileStats = fileStats;
    }

    public void setWebseedsSendingToUs(int webseedsSendingToUs) {
        mWebseedsSendingToUs = webseedsSendingToUs;
    }

    public void setPeers(Peer[] peers) {
        mPeers = peers;
    }

    public void setPeerLimit(int peers) {
        mPeerLimit = peers;
    }

    public boolean isPaused() {
        if (getStatus() != Status.STOPPED) {
            return false;
        }

        return mUploadRatio < getActiveSeedRatioLimit();
    }

    public boolean isSeeding() {
        return getStatus() == Status.SEEDING;
    }

    public boolean isActive() {
        switch(getStatus()) {
            case Status.STOPPED:
            case Status.CHECK_WAITING:
            case Status.DOWNLOAD_WAITING:
            case Status.SEED_WAITING:
                return false;
            default:
                return true;
        }
    }

    public void setTrafficText(Context context) {
        float seedLimit = getActiveSeedRatioLimit();
        switch(getStatus()) {
            case Torrent.Status.DOWNLOAD_WAITING:
            case Torrent.Status.DOWNLOADING:
                mTrafficText = Html.fromHtml(String.format(
                                context.getString(R.string.traffic_downloading_format),
                    G.readableFileSize(mSizeWhenDone - mLeftUntilDone),
                    G.readableFileSize(mSizeWhenDone),
                    String.format(context.getString(R.string.traffic_downloading_percentage_format),
                           G.readablePercent(mPercentDone * 100)),
                    mEta < 0
                        ? context.getString(R.string.traffic_remaining_time_unknown)
                        : String.format(context.getString(R.string.traffic_remaining_time_format),
                           G.readableRemainingTime(mEta, context))
                ));
                break;
            case Torrent.Status.SEED_WAITING:
            case Torrent.Status.SEEDING:
                mTrafficText = Html.fromHtml(String.format(
                                context.getString(R.string.traffic_seeding_format), new Object[] {
                    G.readableFileSize(mSizeWhenDone),
                    G.readableFileSize(mUploadedEver),
                    String.format(context.getString(R.string.traffic_seeding_ratio_format),
                           G.readablePercent(mUploadRatio),
                           seedLimit <= 0 ? "" : String.format(
                               context.getString(R.string.traffic_seeding_ratio_goal_format),
                               G.readablePercent(seedLimit))
                    ),
                    seedLimit <= 0
                        ? ""
                        : mEta < 0
                            ? context.getString(R.string.traffic_remaining_time_unknown)
                            : String.format(context.getString(R.string.traffic_remaining_time_format),
                               G.readableRemainingTime(mEta, context)),
                }));
                break;
            case Torrent.Status.CHECK_WAITING:
                break;
            case Torrent.Status.CHECKING:
                break;
            case Torrent.Status.STOPPED:
                if (mPercentDone < 1) {
                    mTrafficText = Html.fromHtml(String.format(
                                    context.getString(R.string.traffic_downloading_format),
                        G.readableFileSize(mSizeWhenDone - mLeftUntilDone),
                        G.readableFileSize(mSizeWhenDone),
                        String.format(context.getString(R.string.traffic_downloading_percentage_format),
                               G.readablePercent(mPercentDone * 100)),
                        "<br/>" + String.format(
                                        context.getString(R.string.traffic_seeding_format),
                            G.readableFileSize(mSizeWhenDone),
                            G.readableFileSize(mUploadedEver),
                            String.format(context.getString(R.string.traffic_seeding_ratio_format),
                                   mUploadRatio < 0 ? 0 : G.readablePercent(mUploadRatio),
                                   seedLimit <= 0 ? "" : String.format(
                                       context.getString(R.string.traffic_seeding_ratio_goal_format),
                                       G.readablePercent(seedLimit))
                            ),
                            ""
                        )
                    ));
                } else {
                    mTrafficText = Html.fromHtml(String.format(
                                    context.getString(R.string.traffic_seeding_format),
                        G.readableFileSize(mSizeWhenDone),
                        G.readableFileSize(mUploadedEver),
                        String.format(context.getString(R.string.traffic_seeding_ratio_format),
                               G.readablePercent(mUploadRatio),
                               seedLimit <= 0 ? "" : String.format(
                                   context.getString(R.string.traffic_seeding_ratio_goal_format),
                                   G.readablePercent(seedLimit))
                        ),
                        ""
                    ));
                }

                break;
            default:
                break;
        }
    }

    public Spanned getTrafficText() {
        return mTrafficText;
    }

    public void setStatusText(Context context) {
        String statusFormat = context.getString(R.string.status_format);
        String formattedStatus, statusType,
               statusMoreFormat, statusSpeedFormat, statusSpeed;
        int peers;

        switch(getStatus()) {
            case Torrent.Status.DOWNLOAD_WAITING:
            case Torrent.Status.DOWNLOADING:
                statusType = context.getString(getStatus() == Torrent.Status.DOWNLOADING
                        ? mMetadataPercentComplete < 1
                            ? R.string.status_state_downloading_metadata
                            : R.string.status_state_downloading
                        : R.string.status_state_download_waiting);
                statusMoreFormat = context.getString(R.string.status_more_downloading_format);
                statusSpeedFormat = context.getString(R.string.status_more_downloading_speed_format);

                if (mStalled) {
                    statusSpeed = context.getString(R.string.status_more_idle);
                } else {
                    statusSpeed = String.format(statusSpeedFormat,
                        G.readableFileSize(mRateDownload),
                        G.readableFileSize(mRateUpload)
                    );
                }

                peers = mPeersSendingToUs;

                formattedStatus = String.format(statusFormat, statusType,
                        String.format(statusMoreFormat,
                            peers, mPeersConnected, statusSpeed
                        )
                    );
                break;
            case Torrent.Status.SEED_WAITING:
            case Torrent.Status.SEEDING:
                statusType = context.getString(getStatus() == Torrent.Status.SEEDING
                        ? R.string.status_state_seeding : R.string.status_state_seed_waiting);
                statusMoreFormat = context.getString(R.string.status_more_seeding_format);
                statusSpeedFormat = context.getString(R.string.status_more_seeding_speed_format);

                if (mStalled) {
                    statusSpeed = context.getString(R.string.status_more_idle);
                } else {
                    statusSpeed = String.format(statusSpeedFormat,
                        G.readableFileSize(mRateUpload)
                    );
                }
                peers = mPeersGettingFromUs;

                formattedStatus = String.format(statusFormat, statusType,
                        String.format(statusMoreFormat,
                            peers, mPeersConnected, statusSpeed
                        )
                    );
                break;
            case Torrent.Status.CHECK_WAITING:
                statusType = context.getString(R.string.status_state_check_waiting);

                formattedStatus = String.format(statusFormat,
                    statusType,
                    "-" + context.getString(R.string.status_more_idle)
                );
                break;
            case Torrent.Status.CHECKING:
                formattedStatus = String.format(
                    context.getString(R.string.status_state_checking),
                    G.readablePercent(mRecheckProgress * 100));

                break;
            case Torrent.Status.STOPPED:
                formattedStatus = context.getString(
                    isPaused()
                        ? R.string.status_state_paused
                        : R.string.status_state_finished
                );

                break;
            default:
                formattedStatus = "Error";

                break;
        }
        mStatusText = Html.fromHtml(formattedStatus);
    }

    public Spanned getStatusText() {
        return mStatusText;
    }

    public void setTransmissionSession(TransmissionSession session) {
        mSession = session;
    }

    public float getActiveSeedRatioLimit() {
        switch(mSeedRatioMode) {
            case Torrent.SeedRatioMode.GLOBAL_LIMIT:
                if (mSession == null || !mSession.isSeedRatioLimitEnabled())
                    return -1;

                return mSession.getSeedRatioLimit();
            case Torrent.SeedRatioMode.TORRENT_LIMIT:
                return mSeedRatioLimit;
            default:
                return -1;
        }
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (!(o instanceof Torrent)) return false;

        return hashCode() == ((Torrent) o).hashCode();
    }

    @Override
    public int hashCode() {
        int result = SEED;

        result = SEED * result + mId;
        result = SEED * result + (mHashString == null
                ? 0 : mHashString.hashCode());

        return result;
    }

    public void updateFrom(Torrent source, String[] fields) {
        if (fields == null) return;

        for (String field : fields) {
            if (field.equals("addedDate")) {
                setAddedDate(source.getAddedDate());
            } else if (field.equals("name")) {
                setName(source.getName());
            } else if (field.equals("totalSize")) {
                setTotalSize(source.getTotalSize());
            } else if (field.equals("error")) {
                setError(source.getError());
            } else if (field.equals("errorString")) {
                setErrorString(source.getErrorString());
            } else if (field.equals("eta")) {
                setEta(source.getEta());
            } else if (field.equals("isFinished")) {
                setFinished(source.isFinished());
            } else if (field.equals("isStalled")) {
                setStalled(source.isStalled());
            } else if (field.equals("leftUntilDone")) {
                setLeftUntilDone(source.getLeftUntilDone());
            } else if (field.equals("metadataPercentComplete")) {
                setMetadataPercentComplete(source.getMetadataPercentComplete());
            } else if (field.equals("peersConnected")) {
                setPeersConnected(source.getPeersConnected());
            } else if (field.equals("peersGettingFromUs")) {
                setPeersGettingFromUs(source.getPeersGettingFromUs());
            } else if (field.equals("peersSendingToUs")) {
                setPeersSendingToUs(source.getPeersSendingToUs());
            } else if (field.equals("percentDone")) {
                setPercentDone(source.getPercentDone());
            } else if (field.equals(SetterFields.QUEUE_POSITION)) {
                setQueuePosition(source.getQueuePosition());
            } else if (field.equals("rateDownload")) {
                setRateDownload(source.getRateDownload());
            } else if (field.equals("rateUpload")) {
                setRateUpload(source.getRateUpload());
            } else if (field.equals("recheckProgress")) {
                setRecheckProgress(source.getRecheckProgress());
            } else if (field.equals(SetterFields.SEED_RATIO_MODE)) {
                setSeedRatioMode(source.getSeedRatioMode());
            } else if (field.equals(SetterFields.SEED_RATIO_LIMIT)) {
                setSeedRatioLimit(source.getSeedRatioLimit());
            } else if (field.equals("sizeWhenDone")) {
                setSizeWhenDone(source.getSizeWhenDone());
            } else if (field.equals("status")) {
                setStatus(source.getStatus(true));
            } else if (field.equals("trackers")) {
                setTrackers(source.getTrackers());
            } else if (field.equals("uploadedEver")) {
                setUploadedEver(source.getUploadedEver());
            } else if (field.equals("uploadRatio")) {
                setUploadRatio(source.getUploadRatio());
            } else if (field.equals(SetterFields.TORRENT_PRIORITY)) {
                setTorrentPriority(source.getTorrentPriority());
            } else if (field.equals("comment")) {
                setComment(source.getComment());
            } else if (field.equals("creator")) {
                setCreator(source.getCreator());
            } else if (field.equals("dateCreated")) {
                setDateCreated(source.getDateCreated());
            } else if (field.equals(SetterFields.DOWNLOAD_LIMIT)) {
                setDownloadLimit(source.getDownloadLimit());
            } else if (field.equals(SetterFields.DOWNLOAD_LIMITED)) {
                setDownloadLimited(source.isDownloadLimited());
            } else if (field.equals("files")) {
                setFiles(source.getFiles());
            } else if (field.equals("hashString")) {
                setHashString(source.getHashString());
            } else if (field.equals(SetterFields.SESSION_LIMITS)) {
                setHonorsSessionLimits(source.areSessionLimitsHonored());
            } else if (field.equals("isPrivate")) {
                setPrivate(source.isPrivate());
            } else if (field.equals("pieceCount")) {
                setPieceCount(source.getPieceCount());
            } else if (field.equals("pieceSize")) {
                setPieceSize(source.getPieceSize());
            } else if (field.equals(SetterFields.UPLOAD_LIMIT)) {
                setUploadLimit(source.getUploadLimit());
            } else if (field.equals(SetterFields.UPLOAD_LIMITED)) {
                setUploadLimited(source.isUploadLimited());
            } else if (field.equals("activityDate")) {
                setActivityDate(source.getActivityDate());
            } else if (field.equals("corruptEver")) {
                setCorruptEver(source.getCorruptEver());
            } else if (field.equals("desiredAvailable")) {
                setDesiredAvailable(source.getDesiredAvailable());
            } else if (field.equals("downloadDir")) {
                setDownloadDir(source.getDownloadDir());
            } else if (field.equals("downloadedEver")) {
                setDownloadedEver(source.getDownloadedEver());
            } else if (field.equals("fileStats")) {
                setFileStats(source.getFileStats());
            } else if (field.equals("haveUnchecked")) {
                setHaveUnchecked(source.getHaveUnchecked());
            } else if (field.equals("haveValid")) {
                setHaveValid(source.getHaveValid());
            } else if (field.equals("peers")) {
                setPeers(source.getPeers());
            } else if (field.equals(SetterFields.PEER_LIMIT)) {
                setPeerLimit(source.getPeerLimit());
            } else if (field.equals("startDate")) {
                setStartDate(source.getStartDate());
            } else if (field.equals("webseedsSendingToUs")) {
                setWebseedsSendingToUs(source.getWebseedsSendingToUs());
            } else if (field.equals("trackerStats")) {
                setTrackerStats(source.getTrackerStats());
            }
        }
    }

    @Override
    public String toString() {
        return mId + ": " + mName;
    }

    /* Just enough to regenerate the torrent list */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel in, int flags) {
        in.writeInt(mId);
        in.writeString(mName);
        in.writeFloat(mMetadataPercentComplete);
        in.writeFloat(mPercentDone);
        in.writeInt(mSeedRatioMode);
        in.writeFloat(mSeedRatioLimit);
        in.writeFloat(mUploadRatio);
        in.writeString(mTrafficText == null ? "" : Html.toHtml(mTrafficText));
        in.writeString(mStatusText == null ? "" : Html.toHtml(mStatusText));
        in.writeInt(getStatus());
        in.writeInt(mError);
        in.writeString(mErrorString);
        in.writeByte((byte) (mStalled ? 1 : 0));
        in.writeByte((byte) (mFinished ? 1 : 0));
        in.writeString(mDownloadDir);

        in.writeLong(mSizeWhenDone);
        in.writeLong(mLeftUntilDone);
        in.writeLong(mUploadedEver);
        in.writeLong(mEta);
        in.writeLong(mTotalSize);
    }

    private Torrent(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mMetadataPercentComplete = in.readFloat();
        mPercentDone = in.readFloat();
        mSeedRatioMode = in.readInt();
        mSeedRatioLimit = in.readFloat();
        mUploadRatio = in.readFloat();
        mTrafficText = Html.fromHtml(in.readString());
        mStatusText = Html.fromHtml(in.readString());
        mStatus = in.readInt();
        mError = in.readInt();
        mErrorString = in.readString();
        mStalled = in.readByte() == 1;
        mFinished = in.readByte() == 1;
        mDownloadDir = in.readString();

        mSizeWhenDone = in.readLong();
        mLeftUntilDone = in.readLong();
        mUploadedEver = in.readLong();
        mEta = in.readLong();
        mTotalSize = in.readLong();
    }

    public static final Parcelable.Creator<Torrent> CREATOR
            = new Parcelable.Creator<Torrent>() {
        @Override
        public Torrent createFromParcel(Parcel in) {
            return new Torrent(in);
        }

        @Override
        public Torrent[] newArray(int size) {
            return new Torrent[size];
        }
    };

}

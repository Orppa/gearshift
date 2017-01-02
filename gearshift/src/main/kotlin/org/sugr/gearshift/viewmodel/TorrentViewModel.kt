package org.sugr.gearshift.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Html
import android.text.Spanned
import android.text.SpannedString
import org.sugr.gearshift.Logger
import org.sugr.gearshift.model.Torrent

class TorrentViewModel(log: Logger, ctx: Context, prefs: SharedPreferences) {
    val isDirectory = ObservableBoolean(false)
    val isChecked = ObservableBoolean(false)
    val downloadProgress = ObservableInt(0)
    val uploadProgress = ObservableInt(0)
    val name = ObservableField("")
    val traffic = ObservableField<Spanned>(SpannedString(""))
    val status = ObservableField<Spanned>(SpannedString(""))
    val error = ObservableField("")
    val hasError = ObservableBoolean(false)

    interface Consumer {

    }

    fun updateTorrent(torrent: Torrent) {
        name.set(torrent.name)
        traffic.set(Html.fromHtml(torrent.trafficText))
        status.set(Html.fromHtml(torrent.statusText))

        downloadProgress.set((torrent.downloadProgress * 100).toInt())
        uploadProgress.set((torrent.uploadProgress * 100).toInt())

        hasError.set(torrent.errorType != Torrent.ErrorType.OK)
        error.set(torrent.error)

    }

    fun destroy() {
    }
}
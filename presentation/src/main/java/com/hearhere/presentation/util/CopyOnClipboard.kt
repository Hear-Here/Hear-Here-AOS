package com.hearhere.presentation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun Context.CopyOnClipboard(text: String) {
    val clipboard: ClipboardManager =
        getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("hear-here", text)
    clipboard.setPrimaryClip(clip)
}

package com.app.eventfinder.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.app.eventfinder.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * SingleTon Class for handling Permission and showing Informative Dialogs to users
 * @author Philip Jan B
 */
object PermissionManager {

    /**
     * Check for permissions. Returns true if granted or device is below API 23
     * @param act - [Context]
     * @param permissions - list of permissions to be checked
     */
    fun checkPermission(act: Activity, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            permissions.forEach {
                if (ContextCompat.checkSelfPermission(act, it) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        } else {
            return true
        }
    }

    /**
     * Shows Educational UI Dialog before prompting user for Permission Request
     * @param action - lambda invoked when positive button is clicked
     * @param ctx - [Context]
     * @param message - [String] message used to display on [Dialog]
     */
    fun showPermissionEducationalUi(ctx: Context, message: String, action: () -> Unit) {
        MaterialAlertDialogBuilder(ctx)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { x, y ->
                action()
                x.dismiss()
            }
            .setNegativeButton(R.string.cancel) { x, y ->
                x.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    /**
     * Shows Dialog informing user can manually change the permission on the device app settings
     * @param action - invoked when positive button is clicked
     * @param ctx - [Context]
     */
    fun showDeniedPermissionDialog(ctx: Context, action: () -> Unit) {
        MaterialAlertDialogBuilder(ctx)
            .setMessage(R.string.permission_denied_warning)
            .setPositiveButton(R.string.settings) { x: DialogInterface, y ->
                action.invoke()
                x.dismiss()
            }
            .setNegativeButton(R.string.cancel) { x, y ->
                x.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}
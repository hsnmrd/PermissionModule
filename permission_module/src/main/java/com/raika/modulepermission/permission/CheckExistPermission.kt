package com.raika.modulepermission.permission

import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.raika.toast.toasting

private fun Activity.getGrantedPermissions(appPackage: String, listener: (List<String>) -> Unit) {
    val granted: MutableList<String> = ArrayList()
    try {
        val pi = packageManager.getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS)
        for (i in pi.requestedPermissions.indices) {
            if (pi.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED != 0) {
                granted.add(pi.requestedPermissions[i])
            }
        }
    } catch (e: Exception) {
        toasting(e.toString())
    }
    listener(granted)
}

fun Activity.isPermissionExist(targetPermission: String): Boolean {
    var isExist = false
    getGrantedPermissions(packageName) {
        it.onEach { permission ->
            if (permission == targetPermission) {
                isExist = true
            }
        }
    }
    return isExist
}

package com.raika.modulepermission.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import com.raika.alertmodule.dialog.ModuleBottomAlert
//import com.raika.alertmodule.dialog.ModuleBottomAlert
import com.raika.attr.ResAttribute
import com.raika.modulepermission.R
import com.sembozdemir.permissionskt.askPermissions
import kotlinx.android.synthetic.main.root_dialog_deny_request.view.*

class AskPermission(var context: Context, vararg permissions: String) {

    companion object {
        var PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        var PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        var PERMISSION_CAMERA = Manifest.permission.CAMERA
        var PERMISSION_CALL = Manifest.permission.CALL_PHONE
        var PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
        var PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
        var PERMISSION_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS
        var PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
        var PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS
    }

    private var permissionList = permissions
    fun request(granted: () -> Unit) {
        (context as Activity).askPermissions(*permissionList) {
            onGranted { granted() }
            onShowRationale { permissionRequest ->
                ModuleBottomAlert(context, R.layout.root_dialog_deny_request)
                    .setCancelable(true)
                    .onViewCreate { adModel ->
                        val title =  "بدون اعمال دسترسی ${getProcessName(permissionList.getOrNull(0))} ، عملیات غیر قابل انجام است"
                        adModel.view.tv_alert_dialog_rf_task_title.text = title
                        adModel.view.btn_root_dialog_deny_request.text = "اعمال دسترسی"
                        adModel.view.btn_root_dialog_deny_request.setTextColor(ResAttribute(context, R.attr.permission_module_green).getColor())
                        adModel.view.btn_root_dialog_deny_request.setOnClickListener {
                            adModel.dialog.dismiss()
                            permissionRequest.retry()
                        }
                    }
                    .show()
            }

        }
    }


    fun getProcessName(permission: String?): String {
        return when (permission) {
            PERMISSION_READ_STORAGE -> "خواندن حافظه"
            PERMISSION_WRITE_STORAGE -> "نوشتن بر روی حافظه"
            PERMISSION_CAMERA -> "دوربین"
            PERMISSION_CALL -> "تماس"
            PERMISSION_RECORD_AUDIO -> "ضبط صدا"
            PERMISSION_READ_PHONE_STATE -> "مدیریت تماس "
            PERMISSION_PROCESS_OUTGOING_CALLS -> "مدیریت تماس خروجی"
            PERMISSION_READ_CALL_LOG -> "خواندن اطلاعات تماس"
            PERMISSION_READ_CONTACTS -> "خواندن مخاطبین"
            else -> ""
        }
    }

}
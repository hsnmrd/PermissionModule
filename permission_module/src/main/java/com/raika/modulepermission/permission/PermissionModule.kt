package com.raika.modulepermission.permission

import android.app.Activity
import android.content.Context
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.raika.alertmodule.dialog.ModuleBottomAlert
import com.raika.attr.PermissionModuleResAttribute
import com.raika.modulepermission.R
import com.sembozdemir.permissionskt.askPermissions

class PermissionModule(var context: Context, vararg permissions: String) {

    private var permissionList = permissions

    fun ask(granted: () -> Unit) {
        (context as Activity).askPermissions(*permissionList) {
            onGranted { granted() }
            onShowRationale { permissionRequest ->
                ModuleBottomAlert(context, R.layout.permission_module_root_dialog_ask_permission, 0f)
                    .setCancelable(true)
                    .onViewCreate { adModel ->
                        val title = "بدون اعمال دسترسی ${getProcessName(permissionList.getOrNull(0))} ، عملیات غیر قابل انجام است"

                        val tvTitle = adModel.view.findViewById<MaterialTextView>(R.id.permission_module_tv_alert_dialog_ask_permission)
                        val btnAccept = adModel.view.findViewById<MaterialButton>(R.id.permission_module_btn_root_dialog_ask_permission)

                        tvTitle.text = title
                        btnAccept.text = "اعمال دسترسی"
                        btnAccept.setTextColor(PermissionModuleResAttribute(context, R.attr.permission_module_green).getColor())
                        btnAccept.setOnClickListener {
                            adModel.dialog.dismiss()
                            permissionRequest.retry()
                        }
                    }
                    .show()
            }

        }
    }


    private fun getProcessName(permission: String?): String {
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
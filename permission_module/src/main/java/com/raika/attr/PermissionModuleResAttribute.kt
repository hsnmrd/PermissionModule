package com.raika.attr

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.raika.modulepermission.R

class PermissionModuleResAttribute(private val context: Context, private val attr: Int) {

    fun getColor(): Int {
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(attrs)
        val color = typedArray.getColor(0, ContextCompat.getColor(context, R.color.black))
        typedArray.recycle()
        return color
    }
    
    fun getColorStateList() : ColorStateList {
        return ColorStateList.valueOf(getColor())
    }
    
    fun getDrawable(): Drawable? {
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

}
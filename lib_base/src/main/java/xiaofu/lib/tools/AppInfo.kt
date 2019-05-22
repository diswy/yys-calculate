package xiaofu.lib.tools

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * 获取应用版本信息
 * Created by @author xiaofu on 2019/4/2.
 */
fun getPackageInfo(context: Context): PackageInfo? {
    return try {
        val pm = context.packageManager
        pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
    } catch (e: Exception) {
        null
    }
}

fun getVersionName(context: Context): String {
    return getPackageInfo(context)?.versionName ?: "版本获取失败"
}

fun getVersionCode(context: Context): Int {
    return getPackageInfo(context)?.versionCode ?: -1
}

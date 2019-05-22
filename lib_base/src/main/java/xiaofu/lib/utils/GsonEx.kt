package xiaofu.lib.utils

import com.google.gson.Gson

/**
 * Gson解析扩展
 * Created by @author xiaofu on 2019/3/25.
 */

inline fun <reified T> Gson.fromJsonArray(json: String, clazz: Class<T>): List<T> {
    val listType = ParameterizedTypeImpl(List::class.java, arrayOf<Class<*>>(clazz))
    return this.fromJson(json, listType)
}
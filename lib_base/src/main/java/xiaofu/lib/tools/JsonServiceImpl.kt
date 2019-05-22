package xiaofu.lib.tools

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * ARouter使用withObject传值时使用
 * 注意，需要有空构造函数，所以如果使用kotlin data class，则不采用此方法传值
 */
@Route(path = "/parseJson/json")
class JsonServiceImpl : SerializationService {

    override fun init(context: Context?) {
    }

    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return Gson().fromJson(input, clazz)
    }

    override fun object2Json(instance: Any?): String {
        return Gson().toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return Gson().fromJson(input, clazz)
    }
}
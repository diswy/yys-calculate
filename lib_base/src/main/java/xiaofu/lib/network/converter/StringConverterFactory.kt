package xiaofu.lib.network.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * Created by @author xiaofu on 2018/12/8.
 */
class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return if (type == String::class.java) StringConverter.INSTANCE else null
    }

    companion object {
        fun create(): StringConverterFactory {
            return StringConverterFactory()
        }
    }

}
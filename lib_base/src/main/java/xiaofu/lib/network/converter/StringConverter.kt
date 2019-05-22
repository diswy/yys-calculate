package xiaofu.lib.network.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 *
 * Created by @author xiaofu on 2018/12/8.
 */
class StringConverter : Converter<ResponseBody, String> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): String? {
        return value.string()
    }

    companion object {
        val INSTANCE = StringConverter()
    }
}
package xiaofu.lib.network.gateway

import android.util.Base64
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.collections.HashMap

/**
 * 描述
 * Created by gorden on 2017/11/8.
 */
class GatewayInterceptor(private val appKey: String, private val appSecret: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = buildReq(chain.request())
        return chain.proceed(request)
    }

    private fun buildReq(request: Request): Request {
        /*
         * 将pathParams中的value替换掉path中的动态参数
         * 比如 path=/v2/getUserInfo/[userId]，pathParams 字典中包含 key:userId , value:10000003
         * 替换后path会变成/v2/getUserInfo/10000003
         */
        val headers = HashMap<String, String>()

        val current = Date()

        headers[ApiConstant.CLOUD_API_HTTP_HEADER_ACCEPT] = "application/json"
        //设置请求头中的UserAgent
        headers[ApiConstant.CLOUD_API_HTTP_HEADER_USER_AGENT] =
            ApiConstant.CLOUD_API_USER_AGENT
        //设置请求头中的时间戳，以timeIntervalSince1970的形式
        headers[ApiConstant.CLOUD_API_X_CA_TIMESTAMP] = current.time.toString()
        //请求放重放Nonce,15分钟内保持唯一,建议使用UUID
        headers[ApiConstant.CLOUD_API_X_CA_NONCE] = UUID.randomUUID().toString()
        //设置请求头中的主机地址
        headers[ApiConstant.CLOUD_API_HTTP_HEADER_HOST] = request.url().host()
        //设置请求头中的Api绑定的的AppKey
        headers[ApiConstant.CLOUD_API_X_CA_KEY] = appKey
        //设置签名版本号
        headers[ApiConstant.CLOUD_API_X_CA_VERSION] =
            ApiConstant.CLOUD_API_CA_VERSION_VALUE
        //设置请求数据类型
        headers[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_TYPE] =
            ApiConstant.CLOUD_API_CONTENT_TYPE_FORM

        /*
         *  如果类型为byte数组的body不为空
         *  将body中的内容MD5算法加密后再采用BASE64方法Encode成字符串，放入HTTP头中
         *  做内容校验，避免内容在网络中被篡改
         */
        try {
            val requestBody = request.body()
            if (requestBody != null && requestBody.contentLength() > 0) {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                headers[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_MD5] = this.base64AndMD5(buffer.readByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var formParam: MutableMap<String, String>? = null
        if (request.method() == "POST" && request.body() is FormBody) {
            formParam = HashMap()
            val body: FormBody = request.body() as FormBody
            for (i in 0 until body.size()) {
                formParam[body.name(i)] = body.value(i)
            }
        }
        headers[ApiConstant.CLOUD_API_X_CA_SIGNATURE] =
            SignUtil.sign(request.method(), headers, appSecret, request.url(), formParam)

        /*
         *  凑齐所有HTTP头之后，将头中的数据全部放入Request对象中
         *  Http头编码方式：先将字符串进行UTF-8编码，然后使用Iso-8859-1解码生成字符串
         */
        for (key in headers.keys) {
            val value = headers[key]
            if (null != value && value.isNotEmpty()) {
                val temp = value.toByteArray(ApiConstant.CLOUD_API_ENCODING)
                headers[key] = String(temp, ApiConstant.CLOUD_API_HEADER_ENCODING)
            }
        }
        return request.newBuilder().headers(Headers.of(headers)).build()
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     */
    private fun base64AndMD5(bytes: ByteArray?): String {
        if (bytes == null) {
            throw IllegalArgumentException("bytes can not be null")
        }
        try {
            val md = MessageDigest.getInstance("MD5")
            md.reset()
            md.update(bytes)
            val encodeBytes = Base64.encode(md.digest(), Base64.DEFAULT)
            val encodeBytes2 = ByteArray(24)
            System.arraycopy(encodeBytes, 0, encodeBytes2, 0, 24)
            return String(encodeBytes2, ApiConstant.CLOUD_API_ENCODING)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException("unknown algorithm MD5")
        }

    }
}
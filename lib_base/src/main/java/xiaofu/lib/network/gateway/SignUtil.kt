package xiaofu.lib.network.gateway

import android.util.Base64
import okhttp3.HttpUrl
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * 描述
 * Created by gorden on 2017/11/8.
 */
object SignUtil {
    /**
     * 签名方法
     * 本方法将Request中的httpMethod、headers、path、queryParam、formParam合成一个字符串用hmacSha256算法双向加密进行签名
     */
    fun sign(method: String, headersParams: MutableMap<String, String>, secret: String, url: HttpUrl, formParam: Map<String, String>?): String {
        try {
            val hmacSha256 = Mac.getInstance("hmacSha256")
            val keyBytes = secret.toByteArray(ApiConstant.CLOUD_API_ENCODING)
            hmacSha256.init(SecretKeySpec(keyBytes, "HmacSHA256"))

            val signStr = buildStrToSign(method, headersParams, url, formParam)
            //对字符串进行hmacSha256加密，然后再进行BASE64编码
            val signResult = hmacSha256.doFinal(signStr.toByteArray(ApiConstant.CLOUD_API_ENCODING))
            val base64Bytes = Base64.encode(signResult, Base64.DEFAULT)
            return String(base64Bytes, ApiConstant.CLOUD_API_ENCODING)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun buildStrToSign(method: String, headerParams: MutableMap<String, String>, url: HttpUrl, formParam: Map<String, String>?): String {
        val sb = StringBuilder(method)
        sb.append(ApiConstant.CLOUD_API_LF)

        //如果有@"Accept"头，这个头需要参与签名
        if (headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_ACCEPT] != null) {
            sb.append(headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_ACCEPT])
        }
        sb.append(ApiConstant.CLOUD_API_LF)
        //如果有@"Content-MD5"头，这个头需要参与签名
        if (headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_MD5] != null) {
            sb.append(headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_MD5])
        }
        sb.append(ApiConstant.CLOUD_API_LF)

        //如果有@"Content-Type"头，这个头需要参与签名
        if (headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_TYPE] != null) {
            sb.append(headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_CONTENT_TYPE])
        }
        sb.append(ApiConstant.CLOUD_API_LF)
        //签名优先读取HTTP_CA_HEADER_DATE，因为通过浏览器过来的请求不允许自定义Date（会被浏览器认为是篡改攻击）
        if (headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_DATE] != null) {
            sb.append(headerParams[ApiConstant.CLOUD_API_HTTP_HEADER_DATE])
        }
        sb.append(ApiConstant.CLOUD_API_LF)
        //将headers合成一个字符串
        sb.append(buildHeaders(headerParams))

        //将path、queryParam、formParam合成一个字符串
        sb.append(buildResource(url, formParam))
        return sb.toString()
    }

    /**
     * 将headers合成一个字符串
     * 需要注意的是，HTTP头需要按照字母排序加入签名字符串
     * 同时所有加入签名的头的列表，需要用逗号分隔形成一个字符串，加入一个新HTTP头@"X-Ca-Signature-Headers"
     */
    private fun buildHeaders(headers: MutableMap<String, String>): String {
        //使用TreeMap,默认按照字母排序
        val headersToSign = TreeMap<String, String>()

        val signHeadersStringBuilder = StringBuilder()

        var flag = 0
        for ((key, value) in headers) {
            if (key.startsWith(ApiConstant.CLOUD_API_CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
                if (flag != 0) {
                    signHeadersStringBuilder.append(",")
                }
                flag++
                signHeadersStringBuilder.append(key)
                headersToSign[key] = value
            }
        }

        //同时所有加入签名的头的列表，需要用逗号分隔形成一个字符串，加入一个新HTTP头@"X-Ca-Signature-Headers"
        headers[ApiConstant.CLOUD_API_X_CA_SIGNATURE_HEADERS] = signHeadersStringBuilder.toString()

        val sb = StringBuilder()
        for ((key, value) in headersToSign) {
            sb.append(key).append(':').append(value).append(ApiConstant.CLOUD_API_LF)
        }
        return sb.toString()
    }

    private fun buildResource(url: HttpUrl, formParam: Map<String, String>?): String {
        val formParamMap = HashMap<String, String>()
        if (url.queryParameterNames().size > 0) {
            url.queryParameterNames().forEachIndexed { index, s ->
                formParamMap[s] = url.queryParameterValue(index)
            }
        }
        formParam?.let {
            formParamMap.putAll(it)
        }
        val sb = StringBuilder()
        sb.append(url.uri().path)

        if (formParamMap.size > 0) {
            sb.append('?')

            //参数Key按字典排序
            val sortMap = TreeMap<String, String>()
            sortMap.putAll(formParamMap)
            for ((flag, e) in sortMap.entries.withIndex()) {
                if (flag != 0) {
                    sb.append('&')
                }
                val key = e.key
                val value = e.value
                if (value.isEmpty()) {
                    sb.append(key)
                } else {
                    sb.append(key).append("=").append(value)
                }
            }
        }
        return sb.toString()
    }
}
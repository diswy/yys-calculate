package xiaofu.lib.network.gateway

import java.nio.charset.Charset

/**
 * 描述
 * Created by gorden on 2017/11/8.
 */
object ApiConstant {
    //签名Header
    const val CLOUD_API_X_CA_SIGNATURE = "X-Ca-Signature"
    //编码UTF-8
    val CLOUD_API_ENCODING = Charset.forName("UTF-8")!!
    //Header头的编码
    val CLOUD_API_HEADER_ENCODING = Charset.forName("ISO-8859-1")!!
    //换行符
    const val CLOUD_API_LF = "\n"
    //UserAgent
    const val CLOUD_API_USER_AGENT = "schoolexandroid"
    //请求时间戳
    const val CLOUD_API_X_CA_TIMESTAMP = "X-Ca-Timestamp"
    //参与签名的系统Header前缀,只有指定前缀的Header才会参与到签名中
    const val CLOUD_API_CA_HEADER_TO_SIGN_PREFIX_SYSTEM = "X-Ca-"
    //所有参与签名的Header
    const val CLOUD_API_X_CA_SIGNATURE_HEADERS = "X-Ca-Signature-Headers"
    //请求放重放Nonce,15分钟内保持唯一,建议使用UUID
    const val CLOUD_API_X_CA_NONCE = "X-Ca-Nonce"
    //APP KEY
    const val CLOUD_API_X_CA_KEY = "X-Ca-Key"
    //签名版本号
    const val CLOUD_API_X_CA_VERSION = "CA_VERSION"
    //请求Header Accept
    const val CLOUD_API_HTTP_HEADER_ACCEPT = "Accept"
    //请求Body内容MD5 Header
    const val CLOUD_API_HTTP_HEADER_CONTENT_MD5 = "Content-MD5"
    //请求Header Content-Type
    const val CLOUD_API_HTTP_HEADER_CONTENT_TYPE = "Content-Type"
    //请求Header UserAgent
    const val CLOUD_API_HTTP_HEADER_USER_AGENT = "User-Agent"
    //请求Header Date
    const val CLOUD_API_HTTP_HEADER_DATE = "Date"
    //请求Header Host
    const val CLOUD_API_HTTP_HEADER_HOST = "Host"
    //签名版本号
    const val CLOUD_API_CA_VERSION_VALUE = "1"
    //表单类型Content-Type
    const val CLOUD_API_CONTENT_TYPE_FORM = "application/x-www-form-urlencoded"
    // 流类型Content-Type
    const val CLOUD_API_CONTENT_TYPE_STREAM = "application/octet-stream; charset=UTF-8"
    //JSON类型Content-Type
    const val CLOUD_API_CONTENT_TYPE_JSON = "application/json; charset=UTF-8"
    //XML类型Content-Type
    const val CLOUD_API_CONTENT_TYPE_XML = "application/xml; charset=UTF-8"
    //文本类型Content-Type
    const val CLOUD_API_CONTENT_TYPE_TEXT = "application/text; charset=UTF-8"
}
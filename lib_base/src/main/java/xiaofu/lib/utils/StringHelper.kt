package xiaofu.lib.utils

import java.net.URLEncoder
import java.util.regex.Pattern

/**
 *
 * Created by @author xiaofu on 2019/4/2.
 */

fun String.unicode(): String {
    return try {
        URLEncoder.encode(this, "utf-8")
    } catch (e: Exception) {
        this
    }
}

/**
 * 如果匹配正则，截掉正则部分
 */
fun String.trimByRegex(regex: String): String {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)

    return if (matcher.find()) {// 匹配成功,截取字符串
        this.replace(matcher.group(), "")
    } else {// 匹配失败，返回原来的值
        this
    }
}

/**
 * 截取多个正则
 */
fun String.trimByRegex(vararg regex: String): String {
    var finalString = this
    regex.forEach {
        finalString = finalString.trimByRegex(it)
    }
    return finalString
}

/**
 * 正则表达式截取<img标签中内容>
 */
fun String.getImgTagSrcContent(): String {
    val imgRegex = "<img[\\s]*[\\S]*src=\"[\\S]*\"[\\s]*/>"// IMG标签正则
    val imgRegexStart = "<img[\\s]*[\\S]*src=\""// IMG标签引号前部分
    val imgRegexEnd = "\"[\\s]*/>"// IMG标签引号后部分

    val pattern = Pattern.compile(imgRegex)
    val matcher = pattern.matcher(this)

    return if (matcher.find()) {
        matcher.group().trimByRegex(imgRegexStart, imgRegexEnd)
    } else {
        ""
    }
}

/**
 * 更新除了img标签的其他内容
 */
fun String.updateExcludeImgTag(newStr: String): String {
    val imgFormat = "%s<img src=\"%s\" />"
    val img = this.getImgTagSrcContent()
    return imgFormat.format(newStr, img)
}

fun String.updateImgTagSrc(newSrc: String): String {
    val imgRegex = "<img[\\s]*[\\S]*src=\"[\\S]*\"[\\s]*/>"
    val answerString = this.trimByRegex(imgRegex)
    val imgFormat = "%s<img src=\"%s\" />"
    return imgFormat.format(answerString, newSrc)
}
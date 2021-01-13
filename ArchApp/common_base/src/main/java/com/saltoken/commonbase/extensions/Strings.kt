package com.saltoken.commonbase.extensions

import android.text.TextUtils
import org.komputing.khash.sha256.extensions.sha256
import org.komputing.khex.extensions.toNoPrefixHexString
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.internal.Intrinsics


fun String.sha256() = toByteArray().sha256().toNoPrefixHexString()

fun String.hmacSha256(secret: String): String {
    val hmacSHA256 = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
    hmacSHA256.init(secretKey)
    return hmacSHA256.doFinal(toByteArray()).toNoPrefixHexString()
}

/**
 *
 * Checks if the CharSequence contains only Unicode digits.
 * A decimal point is not a Unicode digit and returns false.
 *
 *
 * `null` will return `false`.
 * An empty CharSequence (length()=0) will return `false`.
 *
 *
 * Note that the method does not allow for a leading sign, either positive or negative.
 * Also, if a String passes the numeric test, it may still generate a NumberFormatException
 * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
 * for int or long respectively.
 *
 * <pre>
 * StringUtils.isNumeric(null)   = false
 * StringUtils.isNumeric("")     = false
 * StringUtils.isNumeric("  ")   = false
 * StringUtils.isNumeric("123")  = true
 * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
 * StringUtils.isNumeric("12 3") = false
 * StringUtils.isNumeric("ab2c") = false
 * StringUtils.isNumeric("12-3") = false
 * StringUtils.isNumeric("12.3") = false
 * StringUtils.isNumeric("-123") = false
 * StringUtils.isNumeric("+123") = false
</pre> *
 *
 * @param cs  the CharSequence to check, may be null
 * @return `true` if only contains digits, and is non-null
 * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
 * @since 3.0 Changed "" to return false and not true
 */
fun CharSequence.isNumeric(): Boolean {
    if (isEmpty()) {
        return false
    }
    val sz = length
    for (i in 0 until sz) {
        if (!Character.isDigit(get(i))) {
            return false
        }
    }
    return true
}

fun String.isEmail(): Boolean {
    return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
        .matcher(this).matches()
}


fun String.isPhoneNumberValid(areaCode: String = "86"): Boolean {
    if (areaCode.isEmpty() || this.length < 5) {
        return false
    }
    return if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
        Pattern.compile("^1[3|4|5|7|8|9]\\d{9}$").matcher(this).matches()
    } else {
        Pattern.compile("^[0-9]*$").matcher(this).matches()
    }
}

fun isPhoneFormat(str: String, str2: String): Boolean {
    Intrinsics.checkParameterIsNotNull(str, "areaCode")
    Intrinsics.checkParameterIsNotNull(str2, "phoneNumber")
    val charSequence: CharSequence = str2
    return if (!TextUtils.isEmpty(charSequence) && str2.length >= 7) {
        Pattern.compile("^[0-9]*$").matcher(charSequence).matches()
    } else false
}

fun String.isAllChinese(): Boolean {
    return if (isEmpty()) {
        false
    } else Regex("[\\\\u4e00-\\\\u9fa5]+").matches(this)
}

fun String.isDoubleOrFloat(): Boolean {
    val pattern: Pattern = Pattern.compile("^[-\\+]?[.\\d]*$")
    return pattern.matcher(this).matches()
}

fun String.toFistUpperCase(): String {
    if (isEmpty()) {
        return this
    }
    return this.substring(0, 1).toUpperCase(Locale.getDefault()) + this.substring(1)
}
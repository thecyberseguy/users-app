package thecyberseguy.usersapp.utils

import androidx.annotation.StringRes
import thecyberseguy.usersapp.R
import thecyberseguy.usersapp.constants.ErrorCode
import java.util.regex.Pattern

object Tools {

    @StringRes
    fun getErrorResId(code: Int): Int {
        val map = mapOf(
            Pair(ErrorCode.NO_INTERNET_CONNECTION, R.string.error_no_internet)
        ).withDefault {
            R.string.error_internal_server
        }

        return map.getValue(code)
    }

    fun getHtmlPattern(): Pattern = Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>")

}
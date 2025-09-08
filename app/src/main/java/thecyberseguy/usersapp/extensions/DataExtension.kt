package thecyberseguy.usersapp.extensions

import org.json.JSONException
import org.json.JSONObject
import thecyberseguy.usersapp.utils.Tools
import java.net.URLEncoder

fun String.toUrlWithQuery(params: List<Pair<String, Any?>>?): String {
    if (params == null) return this

    var query = ""

    for (paramPair in params) {
        if (paramPair.second == null) continue
        query += if (query.isEmpty()) {
            "?" + paramPair.first + "=" + URLEncoder.encode(paramPair.second.toString(), "UTF-8")
        } else {
            "&" + paramPair.first + "=" + URLEncoder.encode(paramPair.second.toString(), "UTF-8")
        }
    }

    return this + query
}

fun String.isHasHtmlTags() = Tools.getHtmlPattern().matcher(this).find()

fun String?.getJsonResponse(key: String): String? {
    if (this.isNullOrBlank()) return null

    return try {
        JSONObject(this).getString(key)
    } catch (e: JSONException) {
        e.printStackTrace()
        null
    }
}
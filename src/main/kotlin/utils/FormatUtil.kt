package utils

import java.text.DecimalFormat

class FormatUtil {
    val decimalFormat = DecimalFormat("#,###")

    fun decimalFormat(value: Int): String {
        return decimalFormat.format(value)
    }

    fun getFormattedDate(localDateTime: String): String {
        val date = localDateTime.split("T")[0]
        val time = localDateTime.split("T")[1].substring(0, 8)
        return "$date $time"
    }
}
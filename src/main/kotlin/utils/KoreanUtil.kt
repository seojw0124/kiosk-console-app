package utils

class KoreanUtil {
    fun getCompleteWordByJongsung(word: String): String {
        val lastName = word[word.length - 1]

        // 한글의 제일 처음과 끝의 범위 밖일 경우는 오류
        if (lastName.code < 0xAC00 || lastName.code > 0xD7A3) {
            return word
        }
        val selectedValue = (lastName.code - 0xAC00) % 28

        return if (selectedValue > 0) "${word}을" else "${word}를"
    }
}
package com.bstech.widlib.util

import java.util.regex.Pattern

/**
 * Created by brayskiy on 2/22/18.
 */

object Validation {

    private const val EMAIL_PATTERN = "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+"
    private const val URL_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"

    enum class Action {
        UNKNOWN, EMAIL, PASSWORD, CODE, URL
    }

    enum class Result {
        OK,
        ERROR,

        EMAIL_EMPTY,
        EMAIL_WRONG_FORMAT,

        PASSWORD_NULL,
        PASSWORD_WRONG_LENGTH
    }

    fun validate(text: String, action: Action): Result {
        return when (action) {
            Validation.Action.EMAIL -> isValidEmail(text)
            Validation.Action.PASSWORD -> isValidPassword(text)
            Validation.Action.URL -> isValidURL(text)
            Validation.Action.CODE -> isValidCode(text)
            else -> isValidText(text)
        }
    }

    fun isValidEmail(email: String?): Result {
        if (email == null || email.isEmpty()) {
            return Result.EMAIL_EMPTY
        } else {
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email.trim { it <= ' ' })
            if (!matcher.matches()) {
                return Result.EMAIL_WRONG_FORMAT
            }
        }
        return Result.OK
    }

    fun isValidURL(url: String?): Result {
        if (url == null || url.isEmpty()) return Result.ERROR
        val pattern = Pattern.compile(URL_PATTERN)
        val matcher = pattern.matcher(url)
        return if (matcher.matches()) Result.OK else Result.ERROR
    }

    fun isValidNumber(code: String): Result {
        return try {
            val codeInt = Integer.parseInt(code)
            if (codeInt > 0) Result.OK else Result.ERROR
        } catch (e: NumberFormatException) {
            Result.ERROR
        }

    }

    fun isValidCode(code: String?): Result {
        return if (code != null && code.length == 6 && isValidNumber(code) == Result.OK)
            Result.OK
        else
            Result.ERROR
    }

    fun isValidPassword(password: String?): Result {
        if (password == null) return Result.PASSWORD_NULL
        //if (hasSpecialCharacters(password)) return VALIDATION_PASSWORD_HAS_SPECIAL_CHARACTERS;
        return if (password.length < 6) Result.PASSWORD_WRONG_LENGTH else Result.OK
    }

    fun hasSpecialCharacters(str: String): Boolean {
        val pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        return pattern.matcher(str).find()
    }

    fun isValidText(text: String?): Result {
        return if (text != null && !text.isEmpty()) Result.OK else Result.ERROR
    }
}

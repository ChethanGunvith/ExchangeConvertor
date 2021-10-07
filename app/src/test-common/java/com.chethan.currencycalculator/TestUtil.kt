package com.chethan.currencycalculator

import java.io.File

object TestUtil {

    fun loadJsonFile(name: String): String {
        return File("src/test/resources/$name")
            .inputStream()
            .readBytes()
            .toString(Charsets.UTF_8)
    }
}

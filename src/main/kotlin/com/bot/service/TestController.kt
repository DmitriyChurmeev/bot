package com.bot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * TODO javadoc
 *
 * @author Churmeev Dmitriy (churmeev@yoomoney.ru)
 * @since 30.12.2021
 */
@RestController
class TestController {

    @GetMapping("test")
    fun test(): String {
        return "test"
    }
}
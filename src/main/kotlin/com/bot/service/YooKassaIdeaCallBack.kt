package com.bot.service

/**
 * Типы CallBack для диалога "Идеи и предложений" с ботом Юкасса
 *
 * @author Churmeev Dmitriy (churmeev@yoomoney.ru)
 * @since 24.12.2021
 */
enum class YooKassaIdeaCallBack(val code: String) {

    /**
     * Стартовая стадия диалога
     */
    CALL_BACK_IDEA("IdeaButton"),

    /**
     * Стадия предоставления информации о магазине
     */
    CALL_BACK_IDEA_SHOP("Shop"),

    /**
     * Стадия предоставления информации о мерчанте
     */
    CALL_BACK_IDEA_MERCHANT_INFO("MerchantInfo"),

    /**
     * Стадия первого вопроса о идеи
     */
    CALL_BACK_IDEA_FIRST_QUESTION("FirstQuestion"),

    /**
     * Стадия второго вопроса о идеи
     */
    CALL_BACK_IDEA_SECOND_QUESTION("SecondQuestion"),

    /**
     * Стадия третьего вопроса о идеи
     */
    CALL_BACK_IDEA_THIRD_QUESTION("ThirdQuestion"),

    /**
     * Стадия обзора введенных данных
     */
    CALL_BACK_IDEA_RESUME("Resume"),

    /**
     * Стадия приоритета идеи
     */
    CALL_BACK_IDEA_PRIORITY("Priority"),

    /**
     * Стадия важности идеи
     */
    CALL_BACK_IDEA_IMPORTANT("Important"),
    /**
     * Стадия важности идеи
     */
    CALL_BACK_IDEA_IMPORTANT_LOW("Important.Low"),
    /**
     * Стадия важности идеи
     */
    CALL_BACK_IDEA_IMPORTANT_NORMAL("Important.Normal"),
    /**
     * Стадия важности идеи
     */
    CALL_BACK_IDEA_IMPORTANT_CRITICAL("Important.Critical"),

    /**
     * Финальная стадия диалога
     */
    CALL_BACK_IDEA_FINISH("Finish")
}
package com.bot.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

/**
 * Long polling коннектор к telegram боту ЮKassa
 *
 * @author Daniil Chernyshov
 * @since 20.12.2021
 */
@Service
open class YookassaTelegramBotConnector(
) : TelegramLongPollingBot() {

    private var HELP_COMMAND_MESSAGE_TEXT = ""
    private var START_MESSAGE_TEXT = ""

    companion object {
        private const val UNKNOWN_USER_ERROR_MESSAGE_TEXT = "Кажется мы не знакомы, свяжитесь с администратором"
        private const val UNKNOWN_COMMAND_ERROR_MESSAGE_TEXT = "Неизвестная команда"
        private const val NOT_TEXT_MESSAGE_ERROR_MESSAGE_TEXT = "Я понимаю только текст"
        private const val MAIN_MENU_TEXT = "О чём хочешь рассказать?"
    }

    init {
        HELP_COMMAND_MESSAGE_TEXT =
            """
              */start:* Начать работу с ботом
              */menu:* Главное меню
              
            """.trimIndent()
        START_MESSAGE_TEXT = """
            Привет! Я Юра
        """.trimIndent()
    }

    override fun onUpdateReceived(update: Update) {
        val mainMenuButton = InlineKeyboardButton()
        mainMenuButton.text = "Вперед"

        mainMenuButton.callbackData = "MainMenu"

        execute(
            SendMessage(update.message.chatId.toString(), "hi!")
        )
    }

    private fun onMessageUpdateReceived(update: Update) {
        val message = update.message

        val userName = message.from.userName
        val chatId = message.chatId.toString()


        if (message.hasText()) {
            val messageText = message.text
            execute(
                when {
                    messageText.startsWith("/start") ->
                        handleStartUpdate(update)

                    messageText.startsWith("/menu") ->
                        handleMainMenu(chatId)

                    messageText.startsWith("/help") ->
                        handleHelpMessage(chatId)


                    else -> createMarkdownMessage(
                        chatId,
                        NOT_TEXT_MESSAGE_ERROR_MESSAGE_TEXT
                    )
                }
            )
        } else {
            execute(
                createMarkdownMessage(
                    chatId,
                    NOT_TEXT_MESSAGE_ERROR_MESSAGE_TEXT
                )
            )
        }
    }

    fun createMarkdownMessage(chatId: String, text: String) : SendMessage {
        val sendMessage = SendMessage(chatId, text)
        sendMessage.parseMode ="Markdown"
        return sendMessage
    }

//    private fun handleMessage(chatId: String, message: String): Optional<SendMessage> {
//        val stage = yooKassaChatContextService.findByChatId(chatId).get().contextData.orEmpty().get("stage")
//
//        if (stage != null && stage.startsWith("IdeaButton.")) {
//            return Optional.of(
//                yooKassaIdeaMessageService.handleCallBackMessage(
//                    YooKassaIdeaChatMessage(
//                        chatId,
//                        stage,
//                        message
//                    )
//                )
//            )
//        }
//
//        return Optional.empty()
//    }

    private fun onCallbackUpdateReceived(update: Update) {
        val userName = update.callbackQuery.from.userName
        val chatId = update.callbackQuery.from.id.toString()


        val callbackData = update.callbackQuery
        execute(
            when {
                callbackData.data.startsWith("MainMenu") ->
                    handleMainMenu(chatId)



//                callbackData.data.startsWith(YooKassaIdeaCallBack.CALL_BACK_IDEA.code) ->
//                    yooKassaIdeaMessageService.handleCallBackMessage(
//                        YooKassaIdeaChatMessage(
//                            chatId,
//                            callbackData.data,
//                            callbackData.message.text
//                        )
//                    )

                else -> createMarkdownMessage(
                    chatId,
                    UNKNOWN_COMMAND_ERROR_MESSAGE_TEXT
                )
            }
        )
        executeAsync(AnswerCallbackQuery(callbackData.id))
    }

    /**
     * Присваиваем пользователю chatId
     */
    private fun handleStartUpdate(update: Update): SendMessage {
        val userName = update.message.from.userName
    //    yooKassaChatContextService.cleanContext(update.message.chatId.toString())
    //    yookassaTelegramBotUserService.upsertUser(YookassaTelegramBotUser(userName, update.message.chatId.toString()))

        val mainMenuButton = InlineKeyboardButton()
        mainMenuButton.text = "Вперед"

        mainMenuButton.callbackData = "MainMenu"

        return createMessageWithInlineKeyboard(
            update.message.chatId.toString(),
            START_MESSAGE_TEXT,
            listOf(listOf(mainMenuButton))
        )
    }

    fun createMessageWithInlineKeyboard(chatId: String, text: String, keyboard: List<List<InlineKeyboardButton>>) : SendMessage {
        val markup = InlineKeyboardMarkup()

        markup.keyboard = keyboard

        val sendMessage = SendMessage(chatId, text)

        sendMessage.replyMarkup = markup
        return sendMessage
    }

    /**
     * Отображение главного меню
     */
    private fun handleMainMenu(chatId: String): SendMessage {


        val troubleButton = InlineKeyboardButton()
        troubleButton.text = "В ЮKassa что-то сломалось"

        troubleButton.callbackData = "TroubleButton"

        val ideaButton = InlineKeyboardButton()
        ideaButton.text = "Есть идея или пожелание от клиента"
        ideaButton.callbackData = YooKassaIdeaCallBack.CALL_BACK_IDEA.code

        val questionButton = InlineKeyboardButton()
        questionButton.text = "У меня вопрос о ЮKassa"
        questionButton.callbackData = "QuestionButton"

        return createMessageWithInlineKeyboard(
            chatId,
            MAIN_MENU_TEXT,
            listOf(listOf(troubleButton), listOf(ideaButton), listOf(questionButton))
        )
    }

    /**
     * Сообщение с инструкцией по использованию бота
     */
    private fun handleHelpMessage(chatId: String): SendMessage {
        return createMarkdownMessage(
            chatId,
            HELP_COMMAND_MESSAGE_TEXT
        )
    }

    override fun getBotUsername(): String = "test_super_puper2_bot"

    override fun getBotToken(): String = "5069543666:AAF93KMUnQtUq537as1HCTBfDXq_kB9JZj0"
}
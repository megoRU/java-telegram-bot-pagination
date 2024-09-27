package org.telegram.pagination;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        InlineKeyboardPaginator inlineKeyboardPaginator = new InlineKeyboardPaginator(10, 0, "TEST: ");
        List<InlineKeyboardButton> keyboard = inlineKeyboardPaginator.getKeyboard();

        System.out.println(inlineKeyboardPaginator.toString());
    }
}
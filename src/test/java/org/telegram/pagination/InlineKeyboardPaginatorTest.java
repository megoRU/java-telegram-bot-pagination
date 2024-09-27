package org.telegram.pagination;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InlineKeyboardPaginatorTest {

    @Test
    public void testSinglePage() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(1, 0, "page_");
        List<InlineKeyboardButton> keyboard = paginator.getKeyboard();
        assertEquals(0, keyboard.size(), "Клавиатура должна быть пустой, когда всего одна страница.");
    }

    @Test
    public void testMultiplePages() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(5, 2, "page_");
        List<InlineKeyboardButton> keyboard = paginator.getKeyboard();
        assertEquals(5, keyboard.size(), "Клавиатура должна содержать 5 кнопок.");
        assertEquals("· 3 ·", keyboard.get(2).getText(), "Текущая страница должна быть третьей.");
    }

    @Test
    public void testMiddlePage() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(10, 5, "page_");
        List<InlineKeyboardButton> keyboard = paginator.getKeyboard();
        assertEquals("« 1", keyboard.get(0).getText());
        assertEquals("‹ 5", keyboard.get(1).getText());
        assertEquals("· 6 ·", keyboard.get(2).getText());
        assertEquals("7 ›", keyboard.get(3).getText());
        assertEquals("10 »", keyboard.get(4).getText());
    }

    @Test
    public void testStartPage() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(10, 0, "page_");
        List<InlineKeyboardButton> keyboard = paginator.getKeyboard();
        assertEquals("· 1 ·", keyboard.get(0).getText());
        assertEquals("2", keyboard.get(1).getText());
        assertEquals("3", keyboard.get(2).getText());
        assertEquals("4 ›", keyboard.get(3).getText());
    }

    @Test
    public void testLastPage() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(10, 9, "page_");
        List<InlineKeyboardButton> keyboard = paginator.getKeyboard();
        assertEquals("« 1", keyboard.get(0).getText());
        assertEquals("‹ 7", keyboard.get(1).getText());
        assertEquals("8", keyboard.get(2).getText());
    }

    @Test
    public void testToString() {
        InlineKeyboardPaginator paginator = new InlineKeyboardPaginator(5, 2, "page_");
        String result = paginator.toString();
        assertEquals("1 2 · 3 · 4 5", result, "Неверное строковое представление кнопок.");
    }
}

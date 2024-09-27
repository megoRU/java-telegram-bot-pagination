package org.telegram.pagination;

import java.util.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineKeyboardPaginator {

    private List<InlineKeyboardButton> keyboard;
    private final int currentPage;
    private final int pageCount;
    private final String dataPattern;

    private static final String FIRST_PAGE_LABEL = "« %s";
    private static final String PREVIOUS_PAGE_LABEL = "‹ %s";
    private static final String NEXT_PAGE_LABEL = "%s ›";
    private static final String LAST_PAGE_LABEL = "%s »";
    private static final String CURRENT_PAGE_LABEL = "· %s ·";

    public InlineKeyboardPaginator(int pageCount, int currentPage, String dataPattern) {
        this.currentPage = Math.max(0, Math.min(currentPage, pageCount - 1)); // 0-based indexing
        this.pageCount = pageCount;
        this.dataPattern = dataPattern;
    }

    private void build() {
        if (pageCount == 1) {
            keyboard = new ArrayList<>();
            return;
        }

        Map<Integer, Object> keyboardDict = new LinkedHashMap<>();
        if (pageCount <= 5) {
            for (int page = 0; page < pageCount; page++) {
                keyboardDict.put(page, page + 1);
            }
        } else {
            keyboardDict.putAll(buildForMultiPages());
        }

        keyboardDict.put(currentPage, String.format(CURRENT_PAGE_LABEL, currentPage + 1));
        keyboard = toButtonArray(keyboardDict);
    }

    private Map<Integer, Object> buildForMultiPages() {
        if (currentPage < 3) {
            return buildStartKeyboard();
        } else if (currentPage > pageCount - 4) {
            return buildFinishKeyboard();
        } else {
            return buildMiddleKeyboard();
        }
    }

    private Map<Integer, Object> buildStartKeyboard() {
        Map<Integer, Object> keyboardDict = new LinkedHashMap<>();
        for (int page = 0; page < 3; page++) {
            keyboardDict.put(page, page + 1);
        }
        keyboardDict.put(3, String.format(NEXT_PAGE_LABEL, 4));
        keyboardDict.put(pageCount - 1, String.format(LAST_PAGE_LABEL, pageCount));
        return keyboardDict;
    }

    private Map<Integer, Object> buildFinishKeyboard() {
        Map<Integer, Object> keyboardDict = new LinkedHashMap<>();
        keyboardDict.put(0, String.format(FIRST_PAGE_LABEL, 1));
        keyboardDict.put(pageCount - 4, String.format(PREVIOUS_PAGE_LABEL, pageCount - 3));
        for (int page = pageCount - 3; page < pageCount; page++) {
            keyboardDict.put(page, page + 1);
        }
        return keyboardDict;
    }

    private Map<Integer, Object> buildMiddleKeyboard() {
        Map<Integer, Object> keyboardDict = new LinkedHashMap<>();
        keyboardDict.put(0, String.format(FIRST_PAGE_LABEL, 1)); // First page
        keyboardDict.put(currentPage - 1, String.format(PREVIOUS_PAGE_LABEL, currentPage)); // Previous page
        keyboardDict.put(currentPage, currentPage + 1); // Current page
        keyboardDict.put(currentPage + 1, String.format(NEXT_PAGE_LABEL, currentPage + 2)); // Next page
        keyboardDict.put(pageCount - 1, String.format(LAST_PAGE_LABEL, pageCount)); // Last page
        return keyboardDict;
    }

    private List<InlineKeyboardButton> toButtonArray(Map<Integer, Object> keyboardDict) {
        List<InlineKeyboardButton> buttonArray = new ArrayList<>();
        List<Integer> keys = new ArrayList<>(keyboardDict.keySet());
        Collections.sort(keys);

        for (Integer key : keys) {
            InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
                    .text(String.valueOf(keyboardDict.get(key)))
                    .callbackData(dataPattern + key)
                    .build();
            buttonArray.add(inlineKeyboardButton);
        }
        return buttonArray;
    }

    public List<InlineKeyboardButton> getKeyboard() {
        if (keyboard == null) {
            build();
        }
        return keyboard;
    }

    @Override
    public String toString() {
        if (keyboard == null) {
            build();
        }
        StringBuilder result = new StringBuilder();
        for (InlineKeyboardButton button : keyboard) {
            result.append(button.getText()).append(" ");
        }
        return result.toString().trim();
    }
}
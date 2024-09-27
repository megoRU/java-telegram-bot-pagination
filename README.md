# java-telegram-bot-pagination

### Maven

https://jitpack.io/#megoRU/java-telegram-bot-pagination

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.megoRU</groupId>
    <artifactId>java-telegram-bot-pagination</artifactId>
    <version>1.0</version>
</dependency>
```

## Examples

### Create pagination

```java
public class Main {
    public static void main(String[] args) {
        InlineKeyboardPaginator inlineKeyboardPaginator = new InlineKeyboardPaginator(10, 0, "TEST: ");
        List<InlineKeyboardButton> keyboard = inlineKeyboardPaginator.getKeyboard();
    }
}
```
package io.ylab.intensive.lesson05.messagefilter.message;

import java.util.List;
import java.util.Objects;

public abstract class MessageHandlerAbs implements MessageHandler {
    @Override
    public void handleMessage(String message) {
        Objects.requireNonNull(message);
        if (message.isEmpty()) {
            return;
        }
        String correctMessage;
        String[] subStrings = splitMessage(message.toLowerCase());
        List<String> similarStrings = getSimilarStrings(subStrings);
        if (similarStrings.size() == 0) {
            correctMessage = message;
        } else {
            correctMessage = getCorrectMessage(message, similarStrings);
        }
        sendMessage(correctMessage);
    }

    private String getCorrectMessage(String message, List<String> similarStrings) {
        String[] subStringsMessage = splitMessage(message);
        String similarString = similarStrings.toString();
        StringBuilder builder = new StringBuilder(message);
        for (String s : subStringsMessage) {
            if (similarString.contains(s.toLowerCase())) {
                int startIndex = builder.indexOf(s);
                builder.replace(startIndex, startIndex + s.length(), getNewSubstring(s));
            }
        }
        return builder.toString();
    }

    private String getNewSubstring(String srcString) {
        char[] chars = srcString.toCharArray();
        for (int i = 1; i < srcString.length() - 1; i++) {
            chars[i] = '*';
        }
        return String.valueOf(chars);
    }

    private String[] splitMessage(String message) {
        return message.split(" +|\\p{Punct}");
    }

    protected abstract List<String> getSimilarStrings(String[] subStrings);

    protected abstract void sendMessage(String correctMessage);
}

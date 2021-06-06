package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        // Так как в сигнатуре привязываемся к Message, то и какое-то хитрое глубокое
        // копирование делать нет смысла
        ObjectForMessage dataCopy = new ObjectForMessage();
        dataCopy.setData(msg.getField13().getData());

        Message copy = msg.toBuilder().field13(dataCopy).build();
        history.put(msg.getId(), copy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(history.get(id));
    }
}

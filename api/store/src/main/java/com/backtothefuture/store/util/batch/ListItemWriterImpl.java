package com.backtothefuture.store.util.batch;


import com.backtothefuture.domain.common.util.fcm.FCMUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.support.ListItemWriter;

public class ListItemWriterImpl<T extends String> extends ListItemWriter<T> {

    private final FCMUtil fcmUtil;

    public ListItemWriterImpl(FCMUtil fcmUtil) {
        this.fcmUtil= fcmUtil;
    }

    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {

        List<T> tokens = new ArrayList<>();

        for (T item : chunk.getItems()) {
            tokens.add(item);
        }

        List<String> modifiedTokens = tokens.stream()
                .map(Object::toString)
                .collect(Collectors.toList());


        // FCM 문자 전송
        fcmUtil.sendReservationRemindMessages(modifiedTokens);
    }

}

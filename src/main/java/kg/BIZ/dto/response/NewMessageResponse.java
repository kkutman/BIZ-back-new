package kg.BIZ.dto.response;

import lombok.Builder;

@Builder
public record NewMessageResponse (
        Boolean isNewMessage,
        Long chatId,
        String fullName,
        int countNewMessage
){
}

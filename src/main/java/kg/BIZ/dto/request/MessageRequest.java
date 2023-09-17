package kg.BIZ.dto.request;

import lombok.Builder;

@Builder
public record MessageRequest (
        Long chatId,
        Long volunteerId,
        Long managerId,
        String message
){
    public MessageRequest withVolunteerId(Long volunteerId) {
        return new MessageRequest(this.chatId(), volunteerId, this.managerId(), this.message());
    }

    public MessageRequest withManagerId(Long managerId) {
        return new MessageRequest(this.chatId(), this.volunteerId(), managerId, this.message());
    }
}

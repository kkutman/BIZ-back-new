package kg.BIZ.service;

import kg.BIZ.dto.request.MessageRequest;
import kg.BIZ.dto.response.ChatResponse;
import kg.BIZ.dto.response.NewMessageResponse;
import kg.BIZ.dto.response.SimpleResponse;

import java.util.List;

public interface ChatService {
    List<ChatResponse> findAll();
    ChatResponse findById(Long targetChatId);
    SimpleResponse sendMessage(MessageRequest messageRequest);

}

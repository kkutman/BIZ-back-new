package kg.BIZ.service.impl;

import jakarta.transaction.Transactional;
import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.request.MessageRequest;
import kg.BIZ.dto.response.ChatResponse;
import kg.BIZ.dto.response.MessageResponse;
import kg.BIZ.dto.response.NewMessageResponse;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.Chat;
import kg.BIZ.model.Manager;
import kg.BIZ.model.Message;
import kg.BIZ.model.User;
import kg.BIZ.model.enums.Role;
import kg.BIZ.repository.ChatRepository;
import kg.BIZ.repository.ManagerRepository;
import kg.BIZ.repository.MessageRepository;
import kg.BIZ.repository.UserRepository;
import kg.BIZ.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final JwtService jwtService;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;


    @Override
    public SimpleResponse sendMessage(MessageRequest messageRequest) {

        User user = jwtService.getAuthenticate();

        if (messageRequest.chatId() == null){
            User user1 = userRepository.findById(messageRequest.userId())
                    .orElseThrow(()-> new NotFoundException("User with Id not found " + messageRequest.userId()));

            Chat chat = new Chat();
            Message message = new Message();
            message.setMessage(messageRequest.message());
            if (user.getRole().name().equals("MANAGER")){
                message.setManager(true);
                chat.setManager(managerRepository.findById(user1.getId())
                        .orElseThrow(()-> new NotFoundException("User with Id not found " + messageRequest.userId())));
            }

            chat.addMessage(message);

            message.setChat(chat);

        }else {
            Chat chat = chatRepository.findById(messageRequest.chatId())
                    .orElseThrow(()-> new NotFoundException("User with Id not found " + messageRequest.userId()));

            Message message = new Message();
            message.setMessage(messageRequest.message());

            if (user.getRole().name().equals("MANAGER")){
                message.setManager(true);
                chat.setManager(managerRepository.findById(chat.getUser().getId())
                        .orElseThrow(()-> new NotFoundException("User with Id not found " + messageRequest.userId())));
            }



        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Сообщения успешно отправлено!")
                .build();
    }


    @Override
    public List<ChatResponse> findAll() {
        User user = jwtService.getAuthenticate();

        List<ChatResponse> chatResponses = new ArrayList<>();

        List<Chat> allChats = chatRepository.findByUserId(user.getId());

        for (Chat allChat : allChats) {
            ChatResponse chatResponse = new ChatResponse();

            chatResponse.setId(allChat.getId());
            List<MessageResponse> messageResponses = new ArrayList<>();

            for (Message message : allChat.getMessages()) {
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setId(message.getId());
                messageResponse.setMessage(message.getMessage());
                messageResponse.setIsManager(message.isManager());
            }
            chatResponse.setMessages(messageResponses);

            chatResponse.setFullName(allChat.getUser().getFirstName() + " " + allChat.getUser().getLastName());
            chatResponses.add(chatResponse);
        }

        return chatResponses;
    }

    @Override
    public ChatResponse findById(Long targetChatId) {

        Chat chat = chatRepository.findById(targetChatId).orElseThrow(()-> new NotFoundException(String.format("Чат с ID %s не найден!", targetChatId)));

        ChatResponse chatResponse = new ChatResponse();

        chatResponse.setId(chat.getId());
        List<MessageResponse> messageResponses = new ArrayList<>();

        for (Message message : chat.getMessages()) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(message.getId());
            messageResponse.setMessage(message.getMessage());
            messageResponse.setIsManager(message.isManager());
        }
        chatResponse.setMessages(messageResponses);

        chatResponse.setFullName(chat.getUser().getFirstName() + " " + chat.getUser().getLastName());

        return chatResponse;
    }
}

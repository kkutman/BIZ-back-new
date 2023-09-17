package kg.BIZ.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.request.MessageRequest;
import kg.BIZ.dto.response.ChatResponse;
import kg.BIZ.dto.response.MessageResponse;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.*;
import kg.BIZ.model.enums.Role;
import kg.BIZ.repository.*;
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
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final VolunteerRepository volunteerRepository;


    @Override
    public SimpleResponse sendMessage(MessageRequest messageRequest) {
        User sender = jwtService.getAuthenticate();

        Manager manager = new Manager();
        Volunteer volunteer = new Volunteer();

        if (messageRequest.volunteerId() == 0 && sender != null && sender.getRole() != Role.MANAGER) {
            messageRequest = messageRequest.withVolunteerId(sender.getId());
            manager = managerRepository.findByUserId(messageRequest.managerId())
                    .orElseThrow(() -> new NotFoundException("Manager with ID not found "));
            volunteer = volunteerRepository.findByUserId(sender.getId())
                    .orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));
        }

        if (messageRequest.managerId() == 0 && sender != null && sender.getRole() == Role.MANAGER) {
            messageRequest = messageRequest.withManagerId(sender.getId());
            volunteer = volunteerRepository.findByUserId(messageRequest.volunteerId()).orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));
            manager = managerRepository.findByUserId(sender.getId())
                    .orElseThrow(() -> new NotFoundException("Manager with ID not found "));
        }

        User recipient = userRepository.findById(messageRequest.volunteerId())
                .orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));

        Chat chat = new Chat();

        Message message = new Message();
        message.setMessage(messageRequest.message());
        assert sender != null;
        message.setManager(sender.getRole() == Role.MANAGER);
        message.setSender(sender);
        message.setRecipient(recipient);

        if (messageRequest.chatId() == 0){
            chat.setManager(manager);
            chat.setVolunteer(volunteer);

            chat.addMessage(message);
        } else {
            chat = chatRepository.findById(messageRequest.chatId())
                    .orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));
            chat.addMessage(message);
        }

        message.setChat(chat);

        chatRepository.save(chat);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Message sent successfully!")
                .build();
    }


    @Override
    public List<ChatResponse> findAll() {
        User user = jwtService.getAuthenticate();
        boolean isManager = user.getRole() == Role.MANAGER;

        List<ChatResponse> chatResponses = new ArrayList<>();

        // Retrieve all chats for the user
        List<Chat> allChats = new ArrayList<>();

        if (user.getRole().name().equals("MANAGER")){
            allChats = chatRepository.findByManagerId(user.getManager().getId());
        }else allChats = chatRepository.findByVolunteerId(user.getVolunteer().getId());

        for (Chat chat : allChats) {
            ChatResponse chatResponse = new ChatResponse();

            List<MessageResponse> messageResponses = new ArrayList<>();

            // Iterate through the messages in the chat
            for (Message message : chat.getMessages()) {
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setId(message.getId());
                messageResponse.setMessage(message.getMessage());
                messageResponse.setIsManager(message.isManager());
                messageResponses.add(messageResponse);
            }

            Long userId;

            if (user.getRole().name().equals("MANAGER")){
                userId = user.getManager().getId();
            }else userId = user.getVolunteer().getId();

            chatResponse.setId(chat.getId());
            chatResponse.setUserId(userId);
            chatResponse.setManagerId(chat.getManager().getId());

            // Set the user's or manager's full name based on the role
            if (isManager) {
                chatResponse.setFullName(chat.getManager().getUser().getFirstName() + " " + chat.getManager().getUser().getLastName());
            } else {
                chatResponse.setFullName(chat.getVolunteer().getUser().getFirstName() + " " + chat.getVolunteer().getUser().getLastName());
            }

            chatResponse.setMessages(messageResponses);
            chatResponses.add(chatResponse);
        }

        return chatResponses;
    }

    @Override
    public ChatResponse findById(Long chatId) {
        User user = jwtService.getAuthenticate();
        boolean isManager = user.getRole() == Role.MANAGER;

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat with ID not found: " + chatId));

        ChatResponse chatResponse = new ChatResponse();

        List<MessageResponse> messageResponses = new ArrayList<>();

        // Iterate through the messages in the chat
        for (Message message : chat.getMessages()) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(message.getId());
            messageResponse.setMessage(message.getMessage());
            messageResponse.setIsManager(message.isManager());
            messageResponses.add(messageResponse);
        }

        Long userId;

        if (user.getRole() == Role.MANAGER) {
            userId = user.getManager().getId();
        } else {
            userId = user.getVolunteer().getId();
        }

        chatResponse.setId(chat.getId());
        chatResponse.setUserId(userId);
        chatResponse.setManagerId(chat.getManager().getId());

        // Set the user's or manager's full name based on the role
        if (isManager) {
            chatResponse.setFullName(chat.getManager().getUser().getFirstName() + " " + chat.getManager().getUser().getLastName());
        } else {
            chatResponse.setFullName(chat.getVolunteer().getUser().getFirstName() + " " + chat.getVolunteer().getUser().getLastName());
        }

        chatResponse.setMessages(messageResponses);

        return chatResponse;
    }

}

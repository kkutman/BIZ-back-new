package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.BIZ.dto.request.MessageRequest;
import kg.BIZ.dto.response.ChatResponse;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatApi {
    private final ChatService chatService;

    @Operation(summary = "Get all chats", description = "This is the method to return all chats")
    @GetMapping
    public List<ChatResponse> findAll() {
        return chatService.findAll();
    }

    @GetMapping("/find_by_id")
    public ChatResponse findById(Long targetChatId){
        return chatService.findById(targetChatId);
    }

    @Operation(summary = "Send message", description = "This method send a message")
    @PostMapping("/send_message")
    public SimpleResponse sendMessage(@RequestBody MessageRequest messageRequest) {
        return chatService.sendMessage(messageRequest);
    }
}

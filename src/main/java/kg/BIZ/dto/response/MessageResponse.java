package kg.BIZ.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String message;
    private Boolean isManager;
    private Long senderUserId;
    private Long recipientUserId;
}

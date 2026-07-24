package kh.edu.paragoniu.court_shared.dto.permission;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RoleUserSummaryDTO {
    private UUID userId;
    private String fullName;
    private String email;
    private boolean active;
}

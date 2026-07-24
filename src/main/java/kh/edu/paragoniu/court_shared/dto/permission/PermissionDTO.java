package kh.edu.paragoniu.court_shared.dto.permission;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class PermissionDTO {
    private Integer permissionId;
    private String code;  
    private boolean assigned;
}

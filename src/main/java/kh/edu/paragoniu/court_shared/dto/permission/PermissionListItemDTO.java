package kh.edu.paragoniu.court_shared.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionListItemDTO {
    
    private Integer permissionId;
    private String code;
    private long roleCount;
}

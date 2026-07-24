package kh.edu.paragoniu.court_shared.dto.permission;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoleDetailDTO {
    private Integer roleId;
    private String name;
    private List<RoleUserSummaryDTO> users;
    private List<PermissionDTO> permissions;
}

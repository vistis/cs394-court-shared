package kh.edu.paragoniu.court_shared.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleListItemDTO {
    private Integer roleId;
    private String name;
    private long userCount;
    private long permissionCount;
}

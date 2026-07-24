package kh.edu.paragoniu.court_shared.dto.permission;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionListModuleDTO {
    
    private String moduleName;
    private List<PermissionListItemDTO> permissions;
}

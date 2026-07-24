package kh.edu.paragoniu.court_shared.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequestDTO {
    @NotBlank(message = "Role name is required")
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Select at least one permission")
    private List<Integer> permissionIds;
}

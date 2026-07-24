package kh.edu.paragoniu.court_shared.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionRequestDTO {
    
    @NotBlank(message = "Permission code is required")
    @Size(max = 255)
    @Pattern(
        regexp = "^[A-Z]+(_[A-Z]+)+$",
        message = "Code must be uppercase words seperated by underscores, e.g. CASE_VIEW"
    )
    private String code;
}

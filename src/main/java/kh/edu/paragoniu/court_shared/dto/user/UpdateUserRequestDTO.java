package kh.edu.paragoniu.court_shared.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {
    
    @NotBlank @Size(max = 255)
    private String username;

    @NotBlank @Email String email;

    @NotBlank @Size(max = 255)
    private String firstName;

    @NotBlank @Size(max = 255)
    private String lastName;

    private Boolean isActive;

    @NotBlank (message = "Please select a role")
    private String roles;
}

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
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequestDTO {
    
    @NotBlank @Size(max = 255)
    private String username;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(max = 255)
    private String firstName;

    @NotBlank @Size(max = 255)
    private String LastName;

}

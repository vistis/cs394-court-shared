package kh.edu.paragoniu.court_shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable  {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicturePath;
    private boolean isActive;
    // private List<String> roles;
    private String roles;

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

    public String getInitials() {
        StringBuilder result = new StringBuilder();
        for (String part: getFullName().split(" ")) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        return result.toString();
    }
}

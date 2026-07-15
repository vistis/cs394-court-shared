package kh.edu.paragoniu.court_shared.dto.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResultDTO {
    private List<UserDTO> users;
    private int currentPage;
    private int totalPages;
    private boolean hasPrevious;
    private boolean hasNext;
}

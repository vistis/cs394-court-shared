package kh.edu.paragoniu.court_shared.dto.storage;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class RecentUploadDTO {
    private String key;
    private String sizeDisplay;
    private Instant lastModified;
}
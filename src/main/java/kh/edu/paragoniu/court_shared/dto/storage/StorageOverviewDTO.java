package kh.edu.paragoniu.court_shared.dto.storage;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
public class StorageOverviewDTO {
    private long totalObjectCount;
    private String totalSizeDisplay;
    private List<PrefixBreakdownDTO> prefixBreakdown;
    private List<RecentUploadDTO> recentUploads;
}
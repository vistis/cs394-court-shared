package kh.edu.paragoniu.court_shared.dto.storage;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class PrefixBreakdownDTO {
    private String prefix;
    private long objectCount;
    private String sizeDisplay;
}
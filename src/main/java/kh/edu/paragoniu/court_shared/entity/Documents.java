package kh.edu.paragoniu.court_shared.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Documents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("case_id")
    private UUID caseId;

    @Field("document_type")
    private String documentType;

    private String title;

    @Field("submitted_by_id")
    private UUID submittedById;

    @Field("file_path")
    private String filePath;

    @Field("is_confidential")
    private boolean isConfidential;

    @Field("uploaded_at")
    private Instant uploadedAt;

    private Map<String, Object> metadata;
}

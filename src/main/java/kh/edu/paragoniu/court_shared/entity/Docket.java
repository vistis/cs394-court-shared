package kh.edu.paragoniu.court_shared.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("docket_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Docket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("case_id")
    private UUID caseId;

    @Field("activity_type")
    private String activityType;

    private String description;

    @Field("performed_by_id")
    private UUID performedById;

    private Instant timestamp;
}

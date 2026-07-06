package kh.edu.paragoniu.court_shared.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;

@Entity
@Table(name = "participants")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "participant_id", nullable = false)
    private UUID participantId;

    @Column(name = "party_type", nullable = false)
    private String partyType;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_info", columnDefinition = "jsonb", nullable = false)
    private JsonNode contactInfo;

    @Column(name = "profile_picture_path", nullable = false)
    private String profilePicturePath;
}

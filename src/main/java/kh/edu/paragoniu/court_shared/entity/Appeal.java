package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "appeals",
    indexes = { @Index(name = "idx_appeals_status", columnList = "status") }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appeal_id", nullable = false)
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_case_id", nullable = false)
    private Case originalCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_case_id", nullable = true)
    private Case newCase;

    @Column(nullable = false)
    private String status;
}

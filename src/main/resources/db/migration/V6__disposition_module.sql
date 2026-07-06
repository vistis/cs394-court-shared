CREATE TABLE disposition_outcomes (
    outcome_type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE dispositions (
    disposition_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID NOT NULL,
    judge_id UUID NOT NULL,
    outcome_type_id INT NOT NULL,
    ruling_details TEXT NOT NULL,
    effective_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_dispositions_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_dispositions_judge FOREIGN KEY (judge_id) REFERENCES judges(judge_id) ON DELETE CASCADE,
    CONSTRAINT fk_dispositions_outcome_type FOREIGN KEY (outcome_type_id) REFERENCES disposition_outcomes(outcome_type_id) ON DELETE CASCADE
);

CREATE TABLE appeals (
    appeal_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_case_id UUID NOT NULL,
    new_case_id UUID,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_appeals_original_case FOREIGN KEY (original_case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_appeals_new_case FOREIGN KEY (new_case_id) REFERENCES cases(case_id) ON DELETE SET NULL
);

CREATE INDEX idx_appeals_status ON appeals(status);

INSERT INTO disposition_outcomes (outcome_type_id, name) VALUES
(1, 'Guilty Verdict'),
(2, 'Acquittal / Not Guilty'),
(3, 'Dismissed with Prejudice'),
(4, 'Settled Out of Court'),
(5, 'Judgment for Plaintiff');

CREATE TABLE participant_roles (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

CREATE TABLE participants (
    participant_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    party_type VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    contact_info JSONB NOT NULL,
    profile_picture_path VARCHAR(255) NOT NULL
);

CREATE TABLE case_participants (
    case_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (case_id, participant_id),
    CONSTRAINT fk_case_participants_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_case_participants_participant FOREIGN KEY (participant_id) REFERENCES participants(participant_id) ON DELETE CASCADE,
    CONSTRAINT fk_case_participants_role FOREIGN KEY (role_id) REFERENCES participant_roles(role_id)
);

CREATE TABLE legal_representations (
    case_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    lawyer_id UUID NOT NULL,
    PRIMARY KEY (case_id, participant_id, lawyer_id),
    CONSTRAINT fk_legal_representations_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_legal_representations_participant FOREIGN KEY (participant_id) REFERENCES participants(participant_id) ON DELETE CASCADE
);

INSERT INTO participant_roles (role_name) VALUES
('Plaintiff'),
('Defendant'),
('Victim'),
('Witness'),
('Expert Witness'),
('Respondent'),
('Appellant');

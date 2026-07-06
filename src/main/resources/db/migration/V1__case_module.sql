CREATE TABLE case_statuses (
    status_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE case_classifications (
    classification_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE cases (
    case_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_number VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status_id INT NOT NULL,
    classification_id INT NOT NULL,
    is_public BOOLEAN NOT NULL,
    filed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    last_updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_cases_status FOREIGN KEY (status_id) REFERENCES case_statuses(status_id),
    CONSTRAINT fk_cases_classification FOREIGN KEY (classification_id) REFERENCES case_classifications(classification_id),
    CONSTRAINT uq_cases_case_number UNIQUE (case_number)
);

CREATE UNIQUE INDEX idx_cases_case_number ON cases(case_number);

INSERT INTO case_statuses (name) VALUES
('DRAFT'),
('FILING_OPEN'),
('SCHEDULED'),
('IN_TRIAL'),
('ADJOURNED'),
('DISPOSED'),
('UNDER_APPEAL');

INSERT INTO case_classifications (name) VALUES
('Civil Lawsuit'),
('Criminal Felony'),
('Criminal Misdemeanor'),
('Commercial Dispute'),
('Administrative Appeal');

CREATE TABLE judges (
    judge_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL,
    profile_picture_path VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    CONSTRAINT uq_judges_license UNIQUE (license_number)
);

CREATE UNIQUE INDEX idx_judges_license ON judges(license_number);

CREATE TABLE case_judges (
    case_id UUID NOT NULL,
    judge_id UUID NOT NULL,
    is_presiding BOOLEAN NOT NULL,
    assigned_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (case_id, judge_id),
    CONSTRAINT fk_case_judges_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_case_judges_judge FOREIGN KEY (judge_id) REFERENCES judges(judge_id) ON DELETE CASCADE
);

CREATE TABLE lawyers (
    lawyer_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL,
    profile_picture_path VARCHAR(255) NOT NULL,
    firm_name VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    CONSTRAINT uq_lawyers_license UNIQUE (license_number)
);

CREATE UNIQUE INDEX idx_lawyers_license ON lawyers(license_number);

INSERT INTO judges (judge_id, first_name, last_name, license_number, profile_picture_path, is_active) VALUES
('4a8d011b-7140-4f51-872c-ec7e4dfce901', 'Sokha', 'Kem', 'JUD-2026-0001', 'profiles/judges/default.webp', true),
('70d52aff-e544-4b1f-ab55-95e854f342e2', 'Vannak', 'Chan', 'JUD-2026-0002', 'profiles/judges/default.webp', true);

INSERT INTO lawyers (lawyer_id, first_name, last_name, license_number, profile_picture_path, firm_name, is_active) VALUES
('22107eb9-4287-4b5b-9a25-3f29c60a210d', 'Bora', 'Sam', 'LAW-2026-5501', 'profiles/lawyers/default.webp', 'Phnom Penh Legal Associates', true),
('622481e5-d08a-4204-b478-62f9a75230e3', 'Srey', 'Leak', 'LAW-2026-7702', 'profiles/lawyers/default.webp', 'Angkor Justice Law Firm', true);

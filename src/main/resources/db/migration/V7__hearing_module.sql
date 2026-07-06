CREATE TABLE courtrooms (
    courtroom_id SERIAL PRIMARY KEY,
    room_number VARCHAR(255) NOT NULL,
    CONSTRAINT uq_courtrooms_room_number UNIQUE (room_number)
);

CREATE TABLE hearing_types (
    hearing_type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE hearings (
    hearing_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID NOT NULL,
    courtroom_id INT NOT NULL,
    hearing_type_id INT NOT NULL,
    start_at TIMESTAMP WITH TIME ZONE NOT NULL,
    end_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_hearings_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_hearings_courtroom FOREIGN KEY (courtroom_id) REFERENCES courtrooms(courtroom_id) ON DELETE CASCADE,
    CONSTRAINT fk_hearings_hearing_type FOREIGN KEY (hearing_type_id) REFERENCES hearing_types(hearing_type_id) ON DELETE CASCADE
);

CREATE INDEX idx_case_id ON hearings(case_id);
CREATE INDEX idx_courtroom_collision_check ON hearings(courtroom_id, status, start_at, end_at);

INSERT INTO courtrooms (courtroom_id, room_number) VALUES
(1, 'Courtroom A - Ground Floor'),
(2, 'Courtroom B - Ground Floor'),
(3, 'Supreme Chamber 101'),
(4, 'Appellate Chamber 202');

INSERT INTO hearing_types (hearing_type_id, name) VALUES
(1, 'Arraignment'),
(2, 'Pre-Trial Conference'),
(3, 'Evidentiary Hearing'),
(4, 'Trial Session'),
(5, 'Verdict Reading');

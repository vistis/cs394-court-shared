CREATE TABLE case_assignments (
    assignment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id UUID NOT NULL,
    greffier_id UUID NOT NULL,
    assigned_by UUID NOT NULL,
    CONSTRAINT fk_case_assignments_case FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    CONSTRAINT fk_case_assignments_greffier FOREIGN KEY (greffier_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_case_assignments_assigned_by FOREIGN KEY (assigned_by) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE INDEX idx_assignment_greffier_id ON case_assignments(greffier_id);

CREATE TABLE greffier_supervisions (
    supervision_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chief_greffier_user_id UUID NOT NULL,
    subordinate_greffier_user_id UUID NOT NULL,
    CONSTRAINT uq_greffier_supervisions_subordinate UNIQUE (subordinate_greffier_user_id),
    CONSTRAINT fk_greffier_supervisions_chief FOREIGN KEY (chief_greffier_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_greffier_supervisions_subordinate FOREIGN KEY (subordinate_greffier_user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE INDEX idx_chief_greffier_user_id ON greffier_supervisions(chief_greffier_user_id);

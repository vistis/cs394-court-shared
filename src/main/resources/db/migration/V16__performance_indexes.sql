-- Performance indexes for the 1M+ record seed. Idempotent (CREATE INDEX IF NOT
-- EXISTS) so it applies cleanly whether or not an earlier performance-index
-- migration already ran. These change no query results and no schema -- they
-- only let Postgres use index scans instead of sequential scans at scale.
--
-- Ordered after the seed migration so the indexes build on populated tables
-- (building after a bulk load is faster than maintaining them during insert).

-- ---------------------------------------------------------------------------
-- Cases: search + listing (ORDER BY filed_at, filtered by status/classification)
-- ---------------------------------------------------------------------------
-- Leading-wildcard LIKE search on case number/title needs a GIN trigram index;
-- a plain btree cannot serve LOWER(col) LIKE '%q%'.
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX IF NOT EXISTS idx_cases_case_number_trgm
    ON cases USING gin (lower(case_number) gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_cases_title_trgm
    ON cases USING gin (lower(title) gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_cases_filed_at
    ON cases (filed_at);
CREATE INDEX IF NOT EXISTS idx_cases_status_filed_at
    ON cases (status_id, filed_at);
CREATE INDEX IF NOT EXISTS idx_cases_classification_filed_at
    ON cases (classification_id, filed_at);

-- ---------------------------------------------------------------------------
-- Hearings: listing (ORDER BY start_at, filtered by status/type)
-- ---------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_hearings_start_at
    ON hearings (start_at);
CREATE INDEX IF NOT EXISTS idx_hearings_status_start_at
    ON hearings (status, start_at);
CREATE INDEX IF NOT EXISTS idx_hearings_type_start_at
    ON hearings (hearing_type_id, start_at);

-- ---------------------------------------------------------------------------
-- Child-table foreign keys used by the directory count subqueries and the
-- profile reverse-lookups. Each table's only index was its composite primary
-- key, whose non-leading columns cannot serve these single-column filters --
-- so they were sequential-scanning millions of rows.
-- ---------------------------------------------------------------------------
-- Judge directory active-case count + judge profile cases.
CREATE INDEX IF NOT EXISTS idx_case_judges_judge_id
    ON case_judges (judge_id);
-- Lawyer directory active-case count + lawyer profile cases.
CREATE INDEX IF NOT EXISTS idx_legal_rep_lawyer_id
    ON legal_representations (lawyer_id);
-- Participant profile "involved cases" reverse lookup.
CREATE INDEX IF NOT EXISTS idx_case_participants_participant_id
    ON case_participants (participant_id);
-- Case detail disposition tab + document disposition options.
CREATE INDEX IF NOT EXISTS idx_dispositions_case_id
    ON dispositions (case_id);
-- Case detail assigned-greffier lookup (case_assignments already indexes
-- greffier_id; this adds the reverse, by case).
CREATE INDEX IF NOT EXISTS idx_case_assignments_case_id
    ON case_assignments (case_id);

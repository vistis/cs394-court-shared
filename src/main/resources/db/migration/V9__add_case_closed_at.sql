-- The Case entity maps a `closed_at` column (set when a case is closed/disposed),
-- but the original V1 case_module migration never created it, so any query over
-- `cases` failed. Add the missing nullable column.
ALTER TABLE cases ADD COLUMN IF NOT EXISTS closed_at TIMESTAMP WITH TIME ZONE;

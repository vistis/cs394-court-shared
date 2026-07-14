-- Sample cases for development / demo of the Cases Directory.
-- status_id:         1 DRAFT, 2 FILING_OPEN, 3 SCHEDULED, 4 IN_TRIAL,
--                    5 ADJOURNED, 6 DISPOSED, 7 UNDER_APPEAL
-- classification_id: 1 Civil Lawsuit, 2 Criminal Felony, 3 Criminal Misdemeanor,
--                    4 Commercial Dispute, 5 Administrative Appeal
-- judges:            4a8d011b-...-ce901 Sokha Kem, 70d52aff-...-42e2 Vannak Chan

INSERT INTO cases (case_id, case_number, title, description, status_id, classification_id, is_public, filed_at, last_updated_at) VALUES
('6f1a9c2e-1111-4aaa-8bbb-000000000001', 'CR-2026-0881', 'State vs. Henderson',        'Armed robbery of a commercial premises.',        4, 2, true,  '2026-01-12T09:00:00Z', '2026-06-01T10:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000002', 'CV-2026-0142', 'TechFlow Inc. vs. Davis',     'Breach of a software licensing contract.',       2, 1, true,  '2026-02-05T09:00:00Z', NULL),
('6f1a9c2e-1111-4aaa-8bbb-000000000003', 'CR-2026-0599', 'State vs. Miller',            'Petty theft misdemeanor.',                       5, 3, true,  '2026-03-10T09:00:00Z', NULL),
('6f1a9c2e-1111-4aaa-8bbb-000000000004', 'CR-2025-1102', 'State vs. O''Connor',         'Aggravated assault felony, currently on appeal.',7, 2, false, '2025-11-22T09:00:00Z', '2026-05-20T10:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000005', 'CM-2026-0203', 'Acme Corp vs. BuildRight',    'Commercial construction dispute.',               3, 4, true,  '2026-04-01T09:00:00Z', NULL),
('6f1a9c2e-1111-4aaa-8bbb-000000000006', 'AA-2026-0044', 'Nguyen vs. City Council',     'Administrative appeal over a zoning permit.',    2, 5, true,  '2026-05-14T09:00:00Z', NULL),
('6f1a9c2e-1111-4aaa-8bbb-000000000007', 'CV-2025-0987', 'Rivera vs. Sunrise Insurance','Insurance claim dispute, disposed.',             6, 1, true,  '2025-09-30T09:00:00Z', '2026-02-10T10:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000008', 'CR-2026-0710', 'State vs. Blake',             'Traffic misdemeanor, draft filing.',             1, 3, false, '2026-06-02T09:00:00Z', NULL),
('6f1a9c2e-1111-4aaa-8bbb-000000000009', 'CM-2025-0555', 'Global Traders vs. PortCo',   'International shipping contract dispute.',        4, 4, true,  '2025-12-15T09:00:00Z', '2026-06-10T10:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000010', 'CV-2026-0321', 'Alvarez vs. Metro Health',    'Medical negligence civil suit.',                 3, 1, true,  '2026-03-28T09:00:00Z', NULL);

INSERT INTO case_judges (case_id, judge_id, is_presiding, assigned_at) VALUES
('6f1a9c2e-1111-4aaa-8bbb-000000000001', '4a8d011b-7140-4f51-872c-ec7e4dfce901', true, '2026-01-13T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000002', '70d52aff-e544-4b1f-ab55-95e854f342e2', true, '2026-02-06T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000003', '4a8d011b-7140-4f51-872c-ec7e4dfce901', true, '2026-03-11T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000004', '70d52aff-e544-4b1f-ab55-95e854f342e2', true, '2025-11-23T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000005', '70d52aff-e544-4b1f-ab55-95e854f342e2', true, '2026-04-02T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000007', '4a8d011b-7140-4f51-872c-ec7e4dfce901', true, '2025-10-01T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000009', '70d52aff-e544-4b1f-ab55-95e854f342e2', true, '2025-12-16T09:00:00Z'),
('6f1a9c2e-1111-4aaa-8bbb-000000000010', '4a8d011b-7140-4f51-872c-ec7e4dfce901', true, '2026-03-29T09:00:00Z');

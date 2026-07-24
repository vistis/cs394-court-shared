"""
generate_seed.py
================
Generates two seed files for the Court Management System:

  V13__seed_1m_cases.sql      - PostgreSQL (Flyway migration)
  seed_mongodb.js             - MongoDB (mongosh script)

RECORD COUNT TARGET: ~1,000,000 total
--------------------------------------
PostgreSQL (~811 k rows):
  participant_roles extension   :       3
  default users (named)         :       3  (+3 user_roles)
  users                         :   3,000
  user_roles                    :   3,000
  lawyers                       :   2,000
  judges                        :     500
  participants                  :  60,000
  cases                         : 200,000
  case_judges                   : 200,000
  case_participants             : ~400,000  (avg 2/case)
  legal_representations         :  80,000
  hearings                      : 100,000
  dispositions                  :  80,000
  case_assignments              :  50,000
  appeals                       :  30,000
  greffier_supervisions         :   2,500

MongoDB (~220 k docs):
  documents collection          : 120,000
  docket_logs collection        : 100,000

GRAND TOTAL ~1,031,000+

ROLES  (from V4__user_module.sql - these IDs are fixed, do NOT add fakes)
--------------------------------------------------------------------------
  1 = CHIEF_GREFFIER
  2 = GREFFIER
  3 = ADMINISTRATOR

PERMISSIONS (from V4__user_module.sql)
---------------------------------------
  1=CASE_VIEW  2=CASE_CREATE  3=CASE_UPDATE  4=CASE_ASSIGN  5=USER_MANAGE

Role->Permission mapping (seeded in V4, NOT duplicated here):
  CHIEF_GREFFIER : CASE_VIEW, CASE_UPDATE, CASE_ASSIGN
  GREFFIER       : CASE_VIEW, CASE_UPDATE
  ADMINISTRATOR  : all five
"""

import datetime
import json
import random
import uuid

# ---------------------------------------------------------------------------
# Output file names (written to the same directory as this script)
# ---------------------------------------------------------------------------
SQL_OUTPUT = "V13__seed_1m_cases.sql"
MONGO_OUTPUT = "seed_mongodb.js"

# ---------------------------------------------------------------------------
# Realistic Khmer/Cambodian data pools
# ---------------------------------------------------------------------------
FIRST_NAMES = [
    "Sokha",
    "Vannak",
    "Chanthou",
    "Bora",
    "Srey",
    "Dara",
    "Kanha",
    "Ratha",
    "Phearun",
    "Chanthy",
    "Moniroth",
    "Sovann",
    "Kesor",
    "Vanna",
    "Nimol",
    "Chenda",
    "Piseth",
    "Ratanak",
    "Sreyleak",
    "Kosal",
    "Davuth",
    "Vireak",
    "Sokunthea",
    "Makara",
    "Bopha",
    "Sambath",
    "Kakada",
    "Chamroeun",
    "Sopheak",
    "Heng",
    "Sitha",
    "Rithy",
    "Leakhena",
    "Channary",
    "Panha",
]

LAST_NAMES = [
    "Kem",
    "Chan",
    "Sok",
    "Sam",
    "Leak",
    "Oum",
    "Noun",
    "Seng",
    "Lim",
    "Phorn",
    "Tep",
    "Nget",
    "Ly",
    "Pen",
    "Ros",
    "Hout",
    "Keo",
    "Chhun",
    "Meas",
    "Suon",
    "Kong",
    "Chhoeum",
    "Sorn",
    "Dim",
    "Long",
    "Heng",
    "Chea",
    "Khun",
    "Lorn",
    "Mean",
    "Khiev",
    "Duch",
    "Sek",
    "Prak",
]

LAW_FIRMS = [
    "Phnom Penh Legal Associates",
    "Angkor Justice Law Firm",
    "Mekong River Counsel",
    "Royal Khmer Barristers",
    "Pacific Legal Partners",
    "Tonle Sap Advocates",
    "Bayon Law Group",
    "Cambodian Business Law Centre",
    "Capital City Solicitors",
    "Heritage Legal Services",
]

COMPANIES = [
    "TechFlow Inc.",
    "Acme Corp",
    "BuildRight Co.",
    "Global Traders Ltd.",
    "Sunrise Insurance",
    "Metro Health Group",
    "PortCo Logistics",
    "Angkor Enterprises",
    "Delta Construction",
    "Pacific Holdings",
    "Royal Import-Export",
    "National Shipping Ltd.",
    "PhnomPenh Motors",
    "Bayon Development",
    "Tonle Sap Fisheries",
]

CASE_TITLE_TEMPLATES = [
    "State vs. {last}",
    "{company} vs. {last}",
    "{last} vs. City Council",
    "{last} vs. Ministry of Finance",
    "State vs. {last} and Associates",
    "{company} vs. {company2}",
    "{last} vs. {last2}",
    "State vs. {last} (Appeal)",
    "{company} vs. National Revenue Authority",
    "{last} vs. Provincial Court",
]

CASE_DESCRIPTIONS = [
    "Armed robbery of a commercial premises.",
    "Breach of a software licensing contract.",
    "Petty theft misdemeanor charge.",
    "Aggravated assault felony charge.",
    "Commercial construction contract dispute.",
    "Administrative appeal over a zoning permit.",
    "Insurance claim settlement dispute.",
    "Traffic violation misdemeanor.",
    "International shipping contract breach.",
    "Medical negligence civil suit.",
    "Fraud and financial misrepresentation charges.",
    "Property boundary encroachment dispute.",
    "Unlawful dismissal employment claim.",
    "Tax evasion criminal charges.",
    "Intellectual property infringement claim.",
    "Domestic violence protective order application.",
    "Corporate shareholder dispute.",
    "Environmental regulation violation.",
    "Drug trafficking charges.",
    "Money laundering investigation.",
    "Public nuisance ordinance violation.",
    "Debt recovery civil action.",
    "Custody and family court matter.",
    "Land title registration dispute.",
    "Consumer protection act violation.",
]

DOCUMENT_TYPES = [
    "FILING",
    "MOTION",
    "APPEAL",
    "EVIDENCE",
    "AFFIDAVIT",
    "ORDER",
    "SUMMONS",
    "SUBPOENA",
    "BRIEF",
    "EXHIBIT",
]

DOCUMENT_TITLE_TEMPLATES = [
    "Initial Case Filing",
    "Motion to Dismiss",
    "Evidence Exhibit A",
    "Affidavit of {name}",
    "Court Order Hearing {n}",
    "Summons to {name}",
    "Legal Brief for Plaintiff",
    "Legal Brief for Defendant",
    "Appeal Filing Document",
    "Subpoena for Witness",
    "Request for Discovery",
    "Settlement Agreement Draft",
    "Expert Witness Report",
    "Bail Application",
    "Sentencing Memorandum",
]

DOCKET_ACTIVITY_TYPES = [
    "CASE_FILED",
    "HEARING_SCHEDULED",
    "DOCUMENT_SUBMITTED",
    "CASE_ASSIGNED",
    "JUDGMENT_ENTERED",
    "APPEAL_FILED",
    "CASE_ADJOURNED",
    "PARTICIPANT_ADDED",
    "LAWYER_ASSIGNED",
    "CASE_CLOSED",
    "EVIDENCE_SUBMITTED",
    "MOTION_FILED",
]

DOCKET_DESCRIPTIONS = {
    "CASE_FILED": "Case officially filed and entered into the system.",
    "HEARING_SCHEDULED": "A hearing has been scheduled for this case.",
    "DOCUMENT_SUBMITTED": "A new document was submitted to the case record.",
    "CASE_ASSIGNED": "Case assigned to a greffier for management.",
    "JUDGMENT_ENTERED": "Judgment entered by the presiding judge.",
    "APPEAL_FILED": "An appeal has been filed against the ruling.",
    "CASE_ADJOURNED": "Hearing adjourned; new date to be scheduled.",
    "PARTICIPANT_ADDED": "A new participant has been added to the case.",
    "LAWYER_ASSIGNED": "Legal representation has been confirmed.",
    "CASE_CLOSED": "Case proceedings have concluded.",
    "EVIDENCE_SUBMITTED": "New evidence submitted and logged.",
    "MOTION_FILED": "A motion has been formally filed.",
}

HEARING_STATUSES = ["SCHEDULED", "COMPLETED", "CANCELLED", "ADJOURNED"]

RULING_TEMPLATES = [
    "After careful deliberation, the court finds the defendant {verdict}.",
    "The parties have reached an agreement; the case is settled.",
    "The case is dismissed with prejudice due to insufficient evidence.",
    "Judgment entered in favour of the plaintiff for damages.",
    "Appeal upheld; case remanded for retrial.",
    "Sentence of {years} years imprisonment with possibility of parole.",
    "Fine of USD {amount} imposed and payable within 30 days.",
    "Acquitted on all counts; defendant released immediately.",
]

APPEAL_STATUSES = ["UNDER_APPEAL", "APPEAL_GRANTED", "APPEAL_DENIED"]
PARTY_TYPES = ["Individual", "Corporation", "Government Entity", "NGO"]


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------


def random_date(start_year: int = 2024, end_year: int = 2026) -> str:
    start = datetime.datetime(start_year, 1, 1, 8, 0, 0)
    end = datetime.datetime(end_year, 12, 31, 18, 0, 0)
    delta = (end - start).days
    if delta <= 0:
        delta = 1
    d = start + datetime.timedelta(
        days=random.randrange(delta),
        hours=random.randint(0, 9),
        minutes=random.choice([0, 15, 30, 45]),
    )
    return d.strftime("%Y-%m-%dT%H:%M:%SZ")


def random_end_date(start_iso: str, h_min: int = 1, h_max: int = 4) -> str:
    dt = datetime.datetime.strptime(start_iso, "%Y-%m-%dT%H:%M:%SZ")
    return (dt + datetime.timedelta(hours=random.randint(h_min, h_max))).strftime(
        "%Y-%m-%dT%H:%M:%SZ"
    )


def rname():
    return random.choice(FIRST_NAMES), random.choice(LAST_NAMES)


def case_title() -> str:
    fn, ln = rname()
    fn2, ln2 = rname()
    t = random.choice(CASE_TITLE_TEMPLATES)
    return (
        t.replace("{last}", ln)
        .replace("{last2}", ln2)
        .replace("{company}", random.choice(COMPANIES))
        .replace("{company2}", random.choice(COMPANIES))
    )


def sql_esc(s: str) -> str:
    return s.replace("'", "''")


def ruling() -> str:
    t = random.choice(RULING_TEMPLATES)
    return sql_esc(
        t.replace("{verdict}", random.choice(["guilty", "not guilty"]))
        .replace("{years}", str(random.randint(1, 20)))
        .replace("{amount}", f"{random.randint(500, 50000):,}")
    )


def write_batches(f, table: str, cols: str, rows: list, size: int = 2000):
    for i in range(0, len(rows), size):
        chunk = rows[i : i + size]
        f.write(f"INSERT INTO {table} ({cols}) VALUES\n")
        f.write(",\n".join(chunk))
        f.write(";\n\n")


# ===========================================================================
# MAIN GENERATOR
# ===========================================================================


def generate():
    print(f"Generating PostgreSQL seed  -> {SQL_OUTPUT}")
    print(f"Generating MongoDB seed     -> {MONGO_OUTPUT}")

    # ------------------------------------------------------------------
    # Pre-generate all ID pools up front for referential integrity
    # ------------------------------------------------------------------
    USERS_COUNT = 3000
    CG_COUNT = 300  # role 1 = CHIEF_GREFFIER
    GREFFIER_COUNT = 2400  # role 2 = GREFFIER
    ADMIN_COUNT = 300  # role 3 = ADMINISTRATOR

    user_ids = [str(uuid.uuid4()) for _ in range(USERS_COUNT)]
    chief_ids = user_ids[:CG_COUNT]
    greffier_ids = user_ids[CG_COUNT : CG_COUNT + GREFFIER_COUNT]
    # admin_ids  = user_ids[CG_COUNT + GREFFIER_COUNT:]  (not needed by FK)

    LAWYERS_COUNT = 2000
    lawyer_ids = [str(uuid.uuid4()) for _ in range(LAWYERS_COUNT)]

    JUDGES_COUNT = 500
    judge_ids = [str(uuid.uuid4()) for _ in range(JUDGES_COUNT)]

    PART_COUNT = 60_000
    part_ids = [str(uuid.uuid4()) for _ in range(PART_COUNT)]

    CASES_COUNT = 200_000
    case_ids = [str(uuid.uuid4()) for _ in range(CASES_COUNT)]

    # Pre-build case metadata (so filed_at is reusable across related rows)
    case_meta = []
    for j in range(CASES_COUNT):
        yr = random.choice([2023, 2024, 2025])
        prefix = random.choice(["CR", "CV", "CM", "AA"])
        cnum = f"{prefix}-{yr}-{j:07d}"
        title = sql_esc(case_title())
        desc = sql_esc(random.choice(CASE_DESCRIPTIONS))
        status = random.randint(1, 7)
        classif = random.randint(1, 5)
        pub = "true" if random.random() > 0.25 else "false"
        filed = random_date(yr, yr)
        case_meta.append((cnum, title, desc, status, classif, pub, filed))

    # ==================================================================
    # PostgreSQL SQL file
    # ==================================================================
    with open(SQL_OUTPUT, "w", encoding="utf-8") as f:
        f.write(
            "-- ============================================================\n"
            "-- AUTO-GENERATED SEED  ~1,000,000 records (PostgreSQL + MongoDB)\n"
            "-- Roles: 1=CHIEF_GREFFIER  2=GREFFIER  3=ADMINISTRATOR\n"
            "-- NOTE: system_roles, system_permissions, role_permissions,\n"
            "--       courtrooms, hearing_types, disposition_outcomes,\n"
            "--       case_statuses, case_classifications are all already\n"
            "--       seeded in V1-V7 migrations. This file does NOT touch them.\n"
            "-- ============================================================\n\n"
        )

        # ----------------------------------------------------------
        # 0. Extend participant_roles (V3 seeded IDs 1-7)
        # ----------------------------------------------------------
        f.write(
            "-- Additional participant roles (V3 already has IDs 1-7)\n"
            "INSERT INTO participant_roles (role_id, role_name) VALUES\n"
            "(8, 'Interested Party'),\n"
            "(9, 'Third-Party Defendant'),\n"
            "(10, 'Co-Defendant');\n\n"
        )

        # ----------------------------------------------------------
        # 1. Default named accounts - one per role
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- DEFAULT SEED ACCOUNTS  (easy to find by username)\n"
            "--   default.admin     password: admin123\n"
            "--   default.chief     password: chief123\n"
            "--   default.greffier  password: greffier123\n"
            "-- Hashes reuse the same bcrypt salts from V4__user_module.sql\n"
            "-- -------------------------------------------------------\n"
        )
        f.write(
            "INSERT INTO users\n"
            "  (user_id, username, email, first_name, last_name, password,\n"
            "   profile_picture_path, is_active, created_at, updated_at)\n"
            "VALUES\n"
            "  ('aaaaaaaa-0001-0001-0001-000000000001',\n"
            "   'default.admin', 'default.admin@court.gov.kh',\n"
            "   'Default', 'Admin',\n"
            "   '$2b$12$eKR10mpra8BUKBwtM3PqDOELvqdf.y/UjMQ5NbS09.FiM2ptoM5Ui',\n"
            "   'users/default.webp', true,\n"
            "   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),\n"
            "  ('aaaaaaaa-0002-0002-0002-000000000002',\n"
            "   'default.chief', 'default.chief@court.gov.kh',\n"
            "   'Default', 'Chief',\n"
            "   '$2b$12$pnsbB2CjqI3kX.9kMgFMyuN.uYyFXSwF5Z4tKt5v6Jx1vO7JRUZWy',\n"
            "   'users/default.webp', true,\n"
            "   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),\n"
            "  ('aaaaaaaa-0003-0003-0003-000000000003',\n"
            "   'default.greffier', 'default.greffier@court.gov.kh',\n"
            "   'Default', 'Greffier',\n"
            "   '$2b$12$WaoIHRMHEioDgrgzUai8Wu8YsUuJVC8bzxfz9VlJFe2ioPfWW7Ca.',\n"
            "   'users/default.webp', true,\n"
            "   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);\n\n"
        )
        f.write(
            "INSERT INTO user_roles (user_id, system_role_id) VALUES\n"
            "  ('aaaaaaaa-0001-0001-0001-000000000001', 3),\n"  # ADMINISTRATOR
            "  ('aaaaaaaa-0002-0002-0002-000000000002', 1),\n"  # CHIEF_GREFFIER
            "  ('aaaaaaaa-0003-0003-0003-000000000003', 2);\n\n"  # GREFFIER
        )

        # ----------------------------------------------------------
        # 2. Bulk users (3000)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- USERS (3,000)  role distribution:\n"
            "--   0 ..  299  -> CHIEF_GREFFIER   (system_role_id=1)\n"
            "--   300..2699  -> GREFFIER          (system_role_id=2)\n"
            "--   2700..2999 -> ADMINISTRATOR     (system_role_id=3)\n"
            "-- -------------------------------------------------------\n"
        )
        user_rows = []
        for j, uid in enumerate(user_ids):
            fn, ln = rname()
            user_rows.append(
                f"('{uid}', 'user.{j:04d}', 'user{j:04d}@court.gov.kh',"
                f" '{fn}', '{ln}',"
                f" '$2b$12$placeholder_hash_{j:04d}',"
                f" 'users/default.webp', true,"
                f" CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)"
            )
        write_batches(
            f,
            "users",
            "user_id, username, email, first_name, last_name, password,"
            " profile_picture_path, is_active, created_at, updated_at",
            user_rows,
        )

        role_rows = []
        for j, uid in enumerate(user_ids):
            if j < CG_COUNT:
                rid = 1
            elif j < CG_COUNT + GREFFIER_COUNT:
                rid = 2
            else:
                rid = 3
            role_rows.append(f"('{uid}', {rid})")
        write_batches(f, "user_roles", "user_id, system_role_id", role_rows)

        # ----------------------------------------------------------
        # 3. Lawyers (2,000)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- LAWYERS (2,000)\n"
            "-- -------------------------------------------------------\n"
        )
        law_rows = []
        for j, lid in enumerate(lawyer_ids):
            fn, ln = rname()
            firm = sql_esc(random.choice(LAW_FIRMS))
            law_rows.append(
                f"('{lid}', '{fn}', '{ln}', 'LAW-{j:07d}',"
                f" 'lawyers/default.webp', '{firm}', true)"
            )
        write_batches(
            f,
            "lawyers",
            "lawyer_id, first_name, last_name, license_number,"
            " profile_picture_path, firm_name, is_active",
            law_rows,
        )

        # ----------------------------------------------------------
        # 4. Judges (500)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- JUDGES (500)\n"
            "-- -------------------------------------------------------\n"
        )
        jud_rows = []
        for j, jid in enumerate(judge_ids):
            fn, ln = rname()
            jud_rows.append(
                f"('{jid}', '{fn}', '{ln}', 'JUD-{j:07d}', 'judges/default.webp', true)"
            )
        write_batches(
            f,
            "judges",
            "judge_id, first_name, last_name, license_number,"
            " profile_picture_path, is_active",
            jud_rows,
        )

        # ----------------------------------------------------------
        # 5. Participants (60,000)  - contact_info is JSONB
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- PARTICIPANTS (60,000)\n"
            "-- -------------------------------------------------------\n"
        )
        part_rows = []
        for j, pid in enumerate(part_ids):
            fn, ln = rname()
            ptype = random.choice(PARTY_TYPES)
            phone = f"+855 {random.randint(10, 99)} {random.randint(100, 999)} {random.randint(1000, 9999)}"
            ci = json.dumps({"email": f"participant{j}@example.com", "phone": phone})
            part_rows.append(
                f"('{pid}', '{ptype}', '{sql_esc(fn + chr(32) + ln)}',"
                f" '{ci}'::jsonb, 'participants/default.webp')"
            )
        write_batches(
            f,
            "participants",
            "participant_id, party_type, name, contact_info, profile_picture_path",
            part_rows,
        )

        # ----------------------------------------------------------
        # 6. Cases (200,000) + case_judges + case_participants
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- CASES (200,000) + case_judges + case_participants\n"
            "-- -------------------------------------------------------\n"
        )
        case_participant_pairs = []  # for legal_representations
        BATCH = 2000

        for i in range(0, CASES_COUNT, BATCH):
            end = min(i + BATCH, CASES_COUNT)
            c_rows, cj_rows, cp_rows = [], [], []

            for j in range(i, end):
                cid = case_ids[j]
                cnum, title, desc, status, classif, pub, filed = case_meta[j]
                c_rows.append(
                    f"('{cid}', '{cnum}', '{title}', '{desc}',"
                    f" {status}, {classif}, {pub}, '{filed}')"
                )
                jid = random.choice(judge_ids)
                cj_rows.append(f"('{cid}', '{jid}', true, '{filed}')")

                added = set()
                for _ in range(random.randint(1, 3)):
                    pid = part_ids[random.randint(0, PART_COUNT - 1)]
                    if pid not in added:
                        added.add(pid)
                        cp_rows.append(f"('{cid}', '{pid}', {random.randint(1, 10)})")
                        case_participant_pairs.append((cid, pid))

            f.write(
                "INSERT INTO cases"
                " (case_id, case_number, title, description, status_id,"
                " classification_id, is_public, filed_at) VALUES\n"
                + ",\n".join(c_rows)
                + ";\n\n"
            )
            f.write(
                "INSERT INTO case_judges"
                " (case_id, judge_id, is_presiding, assigned_at) VALUES\n"
                + ",\n".join(cj_rows)
                + ";\n\n"
            )
            f.write(
                "INSERT INTO case_participants"
                " (case_id, participant_id, role_id) VALUES\n"
                + ",\n".join(cp_rows)
                + ";\n\n"
            )

        # ----------------------------------------------------------
        # 7. Legal representations (80,000)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- LEGAL REPRESENTATIONS (80,000)\n"
            "-- -------------------------------------------------------\n"
        )
        random.shuffle(case_participant_pairs)
        TARGET_LR = min(80_000, len(case_participant_pairs))
        lr_rows = []
        for j in range(TARGET_LR):
            cid, pid = case_participant_pairs[j]
            lid = random.choice(lawyer_ids)
            lr_rows.append(f"('{cid}', '{pid}', '{lid}')")
        write_batches(
            f,
            "legal_representations",
            "case_id, participant_id, lawyer_id",
            lr_rows,
        )

        # ----------------------------------------------------------
        # 8. Hearings (100,000)
        #    courtroom_id 1-4   (seeded in V7)
        #    hearing_type_id 1-5 (seeded in V7)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- HEARINGS (100,000)\n"
            "-- courtroom_id 1-4, hearing_type_id 1-5 (from V7 seed)\n"
            "-- -------------------------------------------------------\n"
        )
        hr_rows = []
        for j in range(100_000):
            cid = case_ids[j % CASES_COUNT]
            start = random_date(2023, 2026)
            end2 = random_end_date(start, 1, 4)
            hr_rows.append(
                f"('{uuid.uuid4()}', '{cid}',"
                f" {random.randint(1, 4)}, {random.randint(1, 5)},"
                f" '{start}', '{end2}', '{random.choice(HEARING_STATUSES)}')"
            )
        write_batches(
            f,
            "hearings",
            "hearing_id, case_id, courtroom_id, hearing_type_id, start_at, end_at, status",
            hr_rows,
        )

        # ----------------------------------------------------------
        # 9. Dispositions (80,000)
        #    outcome_type_id 1-5 (seeded in V6)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- DISPOSITIONS (80,000)\n"
            "-- outcome_type_id 1-5 (from V6 seed)\n"
            "-- -------------------------------------------------------\n"
        )
        disp_rows = []
        for j in range(80_000):
            cid = case_ids[j % CASES_COUNT]
            jid = random.choice(judge_ids)
            eff = random_date(2023, 2026)
            disp_rows.append(
                f"('{uuid.uuid4()}', '{cid}', '{jid}',"
                f" {random.randint(1, 5)}, '{ruling()}', '{eff}')"
            )
        write_batches(
            f,
            "dispositions",
            "disposition_id, case_id, judge_id, outcome_type_id, ruling_details, effective_at",
            disp_rows,
        )

        # ----------------------------------------------------------
        # 10. Case assignments (50,000)
        #     greffier_id -> GREFFIER role only
        #     assigned_by -> CHIEF_GREFFIER role only
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- CASE ASSIGNMENTS (50,000)\n"
            "-- greffier_id  = GREFFIER users only\n"
            "-- assigned_by  = CHIEF_GREFFIER users only\n"
            "-- -------------------------------------------------------\n"
        )
        asgn_rows = []
        for j in range(50_000):
            cid = case_ids[j % CASES_COUNT]
            gid = random.choice(greffier_ids)
            cuid = random.choice(chief_ids)
            at = random_date(2023, 2026)
            asgn_rows.append(f"('{uuid.uuid4()}', '{cid}', '{gid}', '{cuid}', '{at}')")
        write_batches(
            f,
            "case_assignments",
            "assignment_id, case_id, greffier_id, assigned_by, assigned_at",
            asgn_rows,
        )

        # ----------------------------------------------------------
        # 11. Appeals (30,000)
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- APPEALS (30,000)\n"
            "-- -------------------------------------------------------\n"
        )
        app_rows = []
        for j in range(30_000):
            orig = case_ids[j % CASES_COUNT]
            # 20% have a linked new_case_id
            new_cid = (
                f"'{case_ids[(j + 50_000) % CASES_COUNT]}'"
                if random.random() < 0.2
                else "NULL"
            )
            app_rows.append(
                f"('{uuid.uuid4()}', '{orig}', {new_cid},"
                f" '{random.choice(APPEAL_STATUSES)}')"
            )
        write_batches(
            f,
            "appeals",
            "appeal_id, original_case_id, new_case_id, status",
            app_rows,
        )

        # ----------------------------------------------------------
        # 12. Greffier supervisions (2,500)
        #     subordinate_greffier_user_id is UNIQUE - shuffle to avoid dupes
        # ----------------------------------------------------------
        f.write(
            "-- -------------------------------------------------------\n"
            "-- GREFFIER SUPERVISIONS (2,500)\n"
            "-- chief_greffier_user_id  = CHIEF_GREFFIER users only\n"
            "-- subordinate             = GREFFIER users only, each used once\n"
            "-- -------------------------------------------------------\n"
        )
        shuffled_g = list(greffier_ids)
        random.shuffle(shuffled_g)
        SUP_COUNT = min(2_500, len(shuffled_g))
        sup_rows = []
        for j in range(SUP_COUNT):
            sub = shuffled_g[j]
            chief = random.choice(chief_ids)
            sup_rows.append(f"('{uuid.uuid4()}', '{chief}', '{sub}')")
        for i in range(0, len(sup_rows), 2000):
            chunk = sup_rows[i : i + 2000]
            f.write(
                "INSERT INTO greffier_supervisions"
                " (supervision_id, chief_greffier_user_id, subordinate_greffier_user_id)"
                " VALUES\n" + ",\n".join(chunk) + " ON CONFLICT DO NOTHING;\n\n"
            )

        f.write(
            "-- ============================================================\n"
            "-- END OF PostgreSQL SEED\n"
            "-- ============================================================\n"
        )

    print(f"  OK  {SQL_OUTPUT} written")

    # ==================================================================
    # MongoDB JavaScript seed file
    # ==================================================================
    #
    # KEY DESIGN: Shared fake file paths
    # -----------------------------------
    # Instead of generating a unique file path per document (which would
    # require 120,000 actual files in R2/S3), we define a small pool of
    # 15 fake file paths - one per document type variant.  Every document
    # record randomly picks one of these shared paths.  You only need to
    # upload those 15 small PDF files to R2 once.
    #
    # Fake file pool (upload these to R2 under the 'documents/' prefix):
    #   documents/fake_filing.pdf
    #   documents/fake_motion.pdf
    #   documents/fake_continuance.pdf
    #   documents/fake_evidence.pdf
    #   documents/fake_disposition.pdf
    #   documents/fake_appeal.pdf
    #   documents/fake_affidavit.pdf
    #   documents/fake_order.pdf
    #   documents/fake_summons.pdf
    #   documents/fake_subpoena.pdf
    #   documents/fake_brief.pdf
    #   documents/fake_exhibit.pdf
    #   documents/fake_report.pdf
    #   documents/fake_agreement.pdf
    #   documents/fake_memo.pdf
    # ==================================================================

    FAKE_FILE_POOL = [
        "documents/fake_filing.pdf",
        "documents/fake_motion.pdf",
        "documents/fake_continuance.pdf",
        "documents/fake_evidence.pdf",
        "documents/fake_disposition.pdf",
        "documents/fake_appeal.pdf",
        "documents/fake_affidavit.pdf",
        "documents/fake_order.pdf",
        "documents/fake_summons.pdf",
        "documents/fake_subpoena.pdf",
        "documents/fake_brief.pdf",
        "documents/fake_exhibit.pdf",
        "documents/fake_report.pdf",
        "documents/fake_agreement.pdf",
        "documents/fake_memo.pdf",
    ]

    # Map document types to a preferred fake file (fallback to random)
    DOCTYPE_TO_FAKE = {
        "FILING": "documents/fake_filing.pdf",
        "MOTION": "documents/fake_motion.pdf",
        "APPEAL": "documents/fake_appeal.pdf",
        "EVIDENCE": "documents/fake_evidence.pdf",
        "AFFIDAVIT": "documents/fake_affidavit.pdf",
        "ORDER": "documents/fake_order.pdf",
        "SUMMONS": "documents/fake_summons.pdf",
        "SUBPOENA": "documents/fake_subpoena.pdf",
        "BRIEF": "documents/fake_brief.pdf",
        "EXHIBIT": "documents/fake_exhibit.pdf",
    }

    with open(MONGO_OUTPUT, "w", encoding="utf-8") as f:
        f.write(
            "// ============================================================\n"
            "// AUTO-GENERATED MONGODB SEED FILE\n"
            "// Run:  mongosh court seed_mongodb.js\n"
            "//   or: Get-Content seed_mongodb.js | docker exec -i court_mongodb mongosh court --quiet\n"
            "//\n"
            "// IMPORTANT - SHARED FAKE FILES\n"
            "// Before browsing documents, upload these 15 small PDFs to your\n"
            "// R2/S3 bucket (any tiny PDF will work - they are shared by all records):\n"
            "//\n"
            "//   documents/fake_filing.pdf\n"
            "//   documents/fake_motion.pdf\n"
            "//   documents/fake_continuance.pdf\n"
            "//   documents/fake_evidence.pdf\n"
            "//   documents/fake_disposition.pdf\n"
            "//   documents/fake_appeal.pdf\n"
            "//   documents/fake_affidavit.pdf\n"
            "//   documents/fake_order.pdf\n"
            "//   documents/fake_summons.pdf\n"
            "//   documents/fake_subpoena.pdf\n"
            "//   documents/fake_brief.pdf\n"
            "//   documents/fake_exhibit.pdf\n"
            "//   documents/fake_report.pdf\n"
            "//   documents/fake_agreement.pdf\n"
            "//   documents/fake_memo.pdf\n"
            "//\n"
            "// All 120,000 document records randomly share one of these 15 paths.\n"
            "// Collections:\n"
            "//   documents   - 120,000 docs\n"
            "//   docket_logs - 100,000 docs\n"
            "// ============================================================\n\n"
            "db = db.getSiblingDB('court');\n\n"
            "// Drop existing data for a clean seed\n"
            "db.documents.drop();\n"
            "db.docket_logs.drop();\n\n"
            "// Indexes (match @Indexed in Java entities)\n"
            "db.documents.createIndex({ case_id: 1 });\n"
            "db.documents.createIndex({ uploaded_at: -1 });\n"
            "db.docket_logs.createIndex({ case_id: 1 });\n"
            "db.docket_logs.createIndex({ timestamp: -1 });\n\n"
        )

        DOCS_COUNT = 120_000
        DOCKET_COUNT = 100_000
        MBATCH = 500

        # Readable document types (match DocumentService.DOCUMENT_TYPES codes)
        APP_DOC_TYPES = [
            "Filing",
            "Motion",
            "Continuance",
            "Evidence",
            "Disposition",
            "Appeal",
        ]
        # Map app document type -> fake file path
        APP_DOCTYPE_TO_FAKE = {
            "Filing": "documents/fake_filing.pdf",
            "Motion": "documents/fake_motion.pdf",
            "Continuance": "documents/fake_continuance.pdf",
            "Evidence": "documents/fake_evidence.pdf",
            "Disposition": "documents/fake_disposition.pdf",
            "Appeal": "documents/fake_appeal.pdf",
        }

        # ---- documents -----------------------------------------------
        f.write(
            "// --------------------------------------------------------\n"
            f"// DOCUMENTS  ({DOCS_COUNT:,})\n"
            "// Each record's file_path points to one of 15 shared fake\n"
            "// PDFs - you only need those 15 files in R2, not 120,000.\n"
            "// --------------------------------------------------------\n"
            "print('Inserting documents...');\n"
        )
        for i in range(0, DOCS_COUNT, MBATCH):
            end = min(i + MBATCH, DOCS_COUNT)
            docs = []
            for j in range(i, end):
                cid = case_ids[j % CASES_COUNT]
                uid = user_ids[j % USERS_COUNT]
                dtype = random.choice(APP_DOC_TYPES)
                fake_path = APP_DOCTYPE_TO_FAKE[dtype]
                fn, ln = rname()
                title = (
                    random.choice(DOCUMENT_TITLE_TEMPLATES)
                    .replace("{name}", fn + " " + ln)
                    .replace("{n}", str(random.randint(1, 10)))
                )
                uploaded = random_date(2023, 2026)
                conf = "true" if random.random() < 0.15 else "false"
                pages = random.randint(1, 120)
                lang = random.choice(["en", "km", "fr"])
                docs.append(
                    "  {"
                    f"case_id: UUID('{cid}'),"
                    f" document_type: '{dtype}',"
                    f" title: '{sql_esc(title)}',"
                    f" submitted_by_id: UUID('{uid}'),"
                    f" file_path: '{fake_path}',"
                    f" is_confidential: {conf},"
                    f" uploaded_at: new Date('{uploaded}'),"
                    f" metadata: {{pages: {pages}, format: 'PDF', language: '{lang}'}}"
                    "}"
                )
            f.write("db.documents.insertMany([\n" + ",\n".join(docs) + "\n]);\n")
        f.write(f"print('  OK {DOCS_COUNT:,} documents inserted');\n\n")

        # ---- docket_logs ---------------------------------------------
        f.write(
            "// --------------------------------------------------------\n"
            f"// DOCKET LOGS  ({DOCKET_COUNT:,})\n"
            "// --------------------------------------------------------\n"
            "print('Inserting docket_logs...');\n"
        )
        for i in range(0, DOCKET_COUNT, MBATCH):
            end = min(i + MBATCH, DOCKET_COUNT)
            dockets = []
            for j in range(i, end):
                cid = case_ids[j % CASES_COUNT]
                uid = user_ids[j % USERS_COUNT]
                atype = random.choice(DOCKET_ACTIVITY_TYPES)
                desc = DOCKET_DESCRIPTIONS[atype]
                ts = random_date(2023, 2026)
                dockets.append(
                    "  {"
                    f"case_id: UUID('{cid}'),"
                    f" activity_type: '{atype}',"
                    f" description: '{desc}',"
                    f" performed_by_id: UUID('{uid}'),"
                    f" timestamp: new Date('{ts}')"
                    "}"
                )
            f.write("db.docket_logs.insertMany([\n" + ",\n".join(dockets) + "\n]);\n")
        f.write(
            f"print('  OK {DOCKET_COUNT:,} docket_logs inserted');\n\n"
            "// ============================================================\n"
            "// END OF MONGODB SEED\n"
            "// ============================================================\n"
            "print('MongoDB seed complete. Remember to upload the 15 fake PDFs to R2!');\n"
        )

    print(f"  OK  {MONGO_OUTPUT} written")

    # Summary
    print()
    print("=" * 60)
    print("SEED GENERATION COMPLETE")
    print("=" * 60)
    print()
    print(f"PostgreSQL ({SQL_OUTPUT}):")
    print(f"  participant_roles ext  :         3")
    print(f"  default users          :         3  (+3 user_roles)")
    print(f"  users                  :     {USERS_COUNT:,}")
    print(f"  user_roles             :     {USERS_COUNT:,}")
    print(f"  lawyers                :     {LAWYERS_COUNT:,}")
    print(f"  judges                 :       {JUDGES_COUNT:,}")
    print(f"  participants           :    {PART_COUNT:,}")
    print(f"  cases                  :   {CASES_COUNT:,}")
    print(f"  case_judges            :   {CASES_COUNT:,}")
    print(f"  case_participants      :  ~{CASES_COUNT * 2:,}  (avg 2)")
    print(f"  legal_representations  :    {TARGET_LR:,}")
    print(f"  hearings               :   100,000")
    print(f"  dispositions           :    80,000")
    print(f"  case_assignments       :    50,000")
    print(f"  appeals                :    30,000")
    print(f"  greffier_supervisions  :     {SUP_COUNT:,}")
    print()
    print(f"MongoDB ({MONGO_OUTPUT}):")
    print(
        f"  documents              :   {DOCS_COUNT:,}  (all share 15 fake file paths)"
    )
    print(f"  docket_logs            :   {DOCKET_COUNT:,}")
    print()
    print("R2/S3 - only 15 small PDFs needed (upload to 'documents/' prefix):")
    for p in FAKE_FILE_POOL:
        print(f"  {p}")
    print()
    print("Default accounts (bcrypt hashes reused from V4):")
    print("  default.admin     role: ADMINISTRATOR  (admin123)")
    print("  default.chief     role: CHIEF_GREFFIER (chief123)")
    print("  default.greffier  role: GREFFIER       (greffier123)")
    print()
    print("To run the MongoDB seed:")
    print(
        "  Get-Content seed_mongodb.js | docker exec -i court_mongodb mongosh court --quiet"
    )


if __name__ == "__main__":
    generate()

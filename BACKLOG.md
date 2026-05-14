# Backlog

## Current

- [x] Create Gradle project
- [x] Add Jackson
- [x] Create PatientRecord model
- [x] Parse sample JSON

## Next

- [ ] Print records cleanly
- [ ] Add SPO2 validation
- [ ] Add missing timestamp warning
- [ ] Create ValidationIssue model
- [ ] Add first JUnit test

## Later

- [ ] Export cleaned JSON
- [ ] CLI flags

## JSON Notes

### patients-1.json

Contains mostly valid sample data.

### patients-2.json

Contains intentionally malformed records for validation testing.

Examples:

- P-1004 → invalid SPO2 (>100)
- P-1005 → missing SPO2
- P-1006 → malformed temperature
- empty timestamps
- null user IDs

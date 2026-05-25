# Backlog

## Completed

- [x] Additional tests
- [x] Fix smaller validation bugs
- [x] Fix RecordCleanerTest path
- [x] Create Gradle project
- [x] Add Jackson JSON parsing
- [x] Create PatientRecord model
- [x] Load JSON from resources
- [x] Add validation pipeline
- [x] Add RecordCleaner normalization
- [x] Add ValidationIssue model
- [x] Add JUnit tests
- [x] Export cleaned JSON
- [x] Add CLI file argument support
- [x] Create README

## Optional Improvements

- [ ] CLI flags
- [ ] Validation summary statistics
- [ ] CSV export

## Sample Data

Sample data is mostly used for manual testing in addition to JUnit.

### patients-1.json

Mixed valid and invalid records that can still be parsed successfully (7 issues in 3 records).

Used for:

- cleaning
- validation
- JSON export

### patients-2.json

Malformed records mixed with valid ones for validation testing (11 issues in 7 records), including SpO₂ out of range and empty dateTimeTaken values.

### patients-3.json

Additional malformed but parseable records for validator testing (10 issues in 3 records).

Examples:

- future timestamps
- invalid patient IDs
- impossible vital signs

### patients-2-invalid.json

Contains intentionally incompatible JSON values that trigger parser-level validation failures during Jackson deserialization.

Examples:

- non-numeric strings in numeric fields
- invalid type mappings

Used to test parser-level error handling.

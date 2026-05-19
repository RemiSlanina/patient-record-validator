# Patient Vital Record Validator

Small Java CLI prototype for validating and cleaning patient vital record JSON data.

## Features

- Parse patient records from JSON
- Validate malformed or suspicious values
- Normalize fields
- Generate validation summaries
- Export cleaned JSON

## Tech

- Java
- Gradle
- Jackson
- JUnit 5

## Current Functionality

- Validate patient records and log issues
- Normalize selected malformed fields
- Remove invalid weights during cleaning
- Print validation issues and cleaned records in the CLI

_Validator_:
business/range rules
(example: SpO₂ range, missing heart rate, invalid timestamps)

_Cleaner_:
normalization and safe transformations
(example: trimming strings, rounding values, removing invalid weights)
Rounding values is technically interpretive, but not a clear semantic rewrite.
36.8888 -> 36.9 is performed as abounded precision normalization.

## Run

```bash
./gradlew run
```

## Tests

```bash
./gradlew test
```

## Scope

Small internal-tooling style prototype.

Intentionally excludes:

- Spring
- databases
- REST APIs
- Docker
- frontend
- microservices

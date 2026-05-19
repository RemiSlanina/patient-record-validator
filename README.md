# Patient Vital Record Validator

Small Java CLI prototype for validating and cleaning patient vital record JSON data.

## Features

- Parse patient records from JSON
- Validate malformed values
- Normalize fields
- Generate validation summary
- Export cleaned JSON

## Tech

- Java
- Gradle
- Jackson
- JUnit

## Feats

- Validate reports and log issues
- Clean reports (invalid weights are dropped currently)
- Print issues and cleaned reports (cli version only)

Cleaner: normalization (trim, format, canonical shape)
Validator: business/range rules (acceptable Spo2, heart rate range, etc.)

## Run

```bash
./gradlew run
```

## Scope

Small internal-tooling style prototype.

No:

- Spring
- databases
- APIs
- Docker
- frontend

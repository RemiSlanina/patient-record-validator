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
- Normalize recoverable input values
- Remove invalid weights during cleaning
- Print validation issues and cleaned records in the CLI

**Prevalidator**

- detects malformed JSON and incompatible input before deserialization
- example use cases:
  - non-numeric strings in numeric fields
  - invalid type mappings
  - malformed JSON syntax
- prevents runtime parsing failures and exits safely with structured errors

**Validator**

- business/range rules
- example: invalid SpO₂ values, missing heart rate, invalid timestamps

**Cleaner**

- normalization and safe transformations
- example: trimming strings, rounding temperatures, removing invalid weights

## Run

```bash
./gradlew run
```

Run with a specific input file:

```bash
./gradlew run --args="sample-data/patients-2.json"
./gradlew run --args="sample-data/patients-3.json"
./gradlew run --args="sample-data/patients-invalid.json"
```

JSON files are currently loaded from:

```
app/src/main/resources/
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

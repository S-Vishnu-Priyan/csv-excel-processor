# CSV to Excel Processor — Coding Standards Workshop

Maven Java demo used in a programming / coding-standards awareness session.

The application:

1. Reads employee data from a CSV file
2. Calculates bonus, tax, grade, and net pay
3. Writes a payroll Excel (`.xlsx`) report

The source follows the quality and security practices demonstrated by the workshop.

## Requirements

- JDK 17+ (the demo compiles with `--release 17`)
- Apache Maven 3.8+, or use the included Maven Wrapper (`mvnw.cmd` on Windows / `./mvnw` on macOS/Linux)
- Optional: SonarQube / SonarCloud for the quality-gate exercise

## Project layout

```
csv-excel-processor/
├── .github/workflows/quality-gate.yml
├── pom.xml
├── sonar-project.properties
├── src/main/java/com/training/codingstandards/
│   ├── App.java                 # entry point
│   ├── CsvEmployeeReader.java   # CSV input
│   ├── EmployeeProcessor.java   # business rules
│   ├── ExcelReportWriter.java   # Excel output
│   ├── Employee.java
│   ├── ReportConfig.java
│   ├── SecurityUtil.java        # hashing and secure token generation
│   └── DatabaseHelper.java      # parameterized database and file operations
├── src/main/resources/
│   ├── employees.csv
│   └── application.properties   # environment-based configuration
└── src/test/java/...            # unit tests
```

## Issues found and fixes

The original implementation was intentionally prepared with coding-standard and
security problems. The following fixes were applied:

| Area | Issue | Fix |
| --- | --- | --- |
| Security | MD5 was used for identifier hashing. | Replaced MD5 with SHA-256 and explicit UTF-8 encoding. |
| Security | Session tokens used predictable `Random` values. | Replaced them with cryptographically secure `SecureRandom` tokens. |
| Security | Database passwords and API keys were hard-coded. | Removed secrets from source code and switched to environment-based configuration. |
| Database | Employee IDs were concatenated into SQL statements. | Added a parameterized `PreparedStatement` to prevent SQL injection. |
| Command execution | User input was passed to `cmd.exe`. | Replaced shell execution with validated Java file APIs. |
| Resource handling | CSV, database, and Excel resources were not consistently closed. | Added try-with-resources blocks. |
| Error handling | Exceptions were swallowed or printed with stack traces. | Added explicit exceptions and structured logging. |
| Correctness | Strings were compared with `==`. | Replaced identity comparisons with value comparisons. |
| Maintainability | `EmployeeProcessor` had high cognitive complexity. | Extracted bonus and tax calculations into focused methods. |
| Encapsulation | Domain fields were publicly mutable. | Added private fields and accessors for `Employee` and `PayrollRow`. |
| Logging | Passwords and API keys were printed to the console. | Removed sensitive output and used Java logging for operational messages. |
| SonarQube | Restricted names, nested ternaries, and utility-constructor findings remained. | Renamed restricted variables, extracted conditional logic, and added private utility constructors. |
| Test coverage | Important payroll and security branches were not tested. | Added tests for payroll rules, employee equality, hashing, tokens, null input, and CSV processing. |

## Verification result

The final local verification command is:

```bat
mvnw.cmd -B clean verify
```

The verification currently completes successfully with:

```text
Tests run: 8
Failures: 0
Errors: 0
BUILD SUCCESS
```

## Run the demo

From this folder (Windows):

```bat
mvnw.cmd -q test
mvnw.cmd -q exec:java
```

From this folder (macOS / Linux, or if Maven is already on PATH):

```bash
./mvnw -q test
./mvnw -q exec:java
```

Or after packaging:

```bash
mvnw.cmd -q package
java -jar target/csv-excel-processor-1.0.0-SNAPSHOT.jar
```

Custom paths:

```bash
java -jar target/csv-excel-processor-1.0.0-SNAPSHOT.jar path/to/employees.csv payroll-report.xlsx
```

Excel is written to `payroll-report.xlsx` in the working directory.

## Scan with SonarQube

With a local SonarQube server:

```bash
mvn -q verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=<TOKEN>
```

The quality gate should pass when the tests and analysis complete successfully.

## GitHub Actions quality gate

[`.github/workflows/quality-gate.yml`](.github/workflows/quality-gate.yml) starts **SonarQube Community LTS in the workflow** (service container on `localhost:9000`), runs `mvn verify sonar:sonar`, and waits for the quality gate.

The workflow fails the job when the quality gate or build fails
(`sonar.qualitygate.wait=true`).

The Maven log link `http://localhost:9000/dashboard?id=csv-excel-processor` is **only on the GitHub Actions runner**. You cannot open it from your laptop.

Download the report from the workflow run instead:

1. GitHub → **Actions** → **Quality Gate** → the failed run
2. **Artifacts** → `sonar-quality-gate-report`
3. Open `quality-gate-report.md` (summary) plus `quality-gate.json` / `issues.json`

Requires no SonarCloud account or repository secrets. It is triggered on pushes
to `main` and `fix/**` branches, pull requests, and manual
`workflow_dispatch`.

## Workshop flow (suggested)

1. Run the app and open `payroll-report.xlsx`.
2. Run the tests and SonarQube analysis.
3. Review the quality-gate report artifact when needed.
4. Keep the CSV-to-Excel behaviour unchanged while maintaining the fixes.

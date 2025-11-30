<!-- Copilot instructions for the `sanhakin-api` repository. Keep concise and focused on project-specific conventions. -->

# Project Snapshot

- Minimal Spring Boot (2.7.1) microservice with scheduling enabled (`SanhakinSchedulerApplication.java`).
- Purpose: fetch and persist SBC01 records from an external SME API on a schedule (hourly batch).
- Key packages: `controller`, `service`, `repository`, `entity`, `dto`, `util` (under `src/main/java/sanhakin/api`).

# What to Know First

- The app is a Spring Boot app (main class: `SanhakinSchedulerApplication`) that enables scheduling (`@EnableScheduling`).
-- External API endpoint used in code: `https://www.smes.go.kr/sanhakin/api/sbc01` (called by `Sbc01BatchService`).
-- Data flow: `Sbc01BatchService.execute()` builds a token, calls the external API, maps each `Sbc01RecordDto` into `Sbc01RecordEntity.applyFromDto(...)`, then saves via `Sbc01RecordRepository`.

# Build / Run / Test (project-specific)

Windows (PowerShell) using included Gradle wrapper:

```
.\gradlew.bat build    # compile + tests
.\gradlew.bat bootRun  # run the Spring Boot app locally
```

To build a jar and run:

```
.\gradlew.bat bootJar
java -jar build\libs\<artifact>.jar
```

Run tests:

```
.\gradlew.bat test
```

Notes:
- The project uses the Gradle wrapper; prefer the wrapper commands above rather than a system gradle.

# Runtime / Debugging Tips

- App is a scheduled batch service. The scheduler (`Sbc01Scheduler`) runs the batch hourly; there is no HTTP endpoint required to trigger the job in this simplified setup.
- API client: the batch uses `RestTemplate` to POST JSON to the external endpoint and will propagate remote errors as runtime exceptions — preserve this behavior when modifying error handling.
- Encryption: `EncDecSupportUtil` performs AES/CBC/PKCS5Padding and the batch constructs tokens with `USER_KEY + yyyyMMddHH` then `encryptAesToHex(...)`. Do not change the encoding without verifying compatibility with the external service.

# Code Patterns & Conventions (important for automated edits)

- DTOs: classes under `dto` use public, UPPER_CASE field names (e.g. `Sbc01RecordDto.BIZR_NO`). Mapping code relies on those exact names (see `Sbc01RecordEntity.applyFromDto`).
- Entities: mapping from DTO -> entity is done manually using `applyFromDto(...)` on the entity class rather than automatic mappers. Keep this mapping style when adding or modifying fields.
- Repository: JPA repositories extend `JpaRepository` and use simple derived queries such as `findByBizrNo(String)` in `Sbc01RecordRepository`.
- Utilities: Use `ApiUtil` for HTTP POST JSON interactions and `EncDecSupportUtil` for AES encrypt/decrypt utilities — both are simple static/instance helpers used throughout.
- Error handling: remote API call errors are surfaced by throwing `RuntimeException` in `ApiUtil`; code calling it (e.g., batch) expects exceptions to propagate.

# External Integrations & Dependencies

 - External API: `https://www.smes.go.kr/sanhakin/api/sbc01` — host of primary integration (used by the scheduled batch).
- Libraries: Spring Boot Web + Data JPA, Apache HttpClient (present but `ApiUtil` uses `RestTemplate`), Gson available.
- JDK compatibility: `sourceCompatibility = '1.8'` in `build.gradle`.

# When Making Changes — Practical Rules

- If you change a DTO field name, update `Sbc01RecordEntity.applyFromDto(...)` and any callers in `service` or `controller` to avoid silent mapping bugs.
- When changing request/response shapes for external API calls, add regression checks by invoking the real endpoint (or a mocked server) because the encryption + token format is time-sensitive.
- Prefer using `ApiUtil.postJson(...)` for outgoing POSTs to keep consistent headers/content-type and centralized error handling.
- Keep `EncDecSupportUtil` usage intact for token encryption; do not inline alternative AES code without tests demonstrating compatibility.

## Files to Inspect for Context

- `src/main/java/sanhakin/api/SanhakinSchedulerApplication.java` (app entry + scheduling)
- `src/main/java/sanhakin/api/service/Sbc01BatchService.java` (batch logic, token generation, mapping & persistence)
- `src/main/java/sanhakin/api/util/EncDecSupportUtil.java` (encryption helper)
- `src/main/java/sanhakin/api/entity/Sbc01RecordEntity.java` (mapping and persistence model)

# Quick Questions to Ask the Maintainer

- Are the `KEY`, `IV`, and `USER_KEY` placeholders in `Sbc01BatchService` expected to be replaced with secure config (e.g., from `application.properties` or a secrets store)?
- Should new endpoints or batch schedules use Spring profiles or feature flags during rollout?

---
If anything here looks incorrect or you'd like the file to include examples (curl/PowerShell calls, sample DTO JSON), tell me which section to expand.
 
## Examples (quick)

- The batch runs automatically on schedule (hourly). To test locally you can run the application and verify scheduler logs or invoke the batch logic from a temporary test/runner. Typical developer checks:

	- Build and run the app, then inspect logs for scheduler start/end messages:

	```powershell
	.
	.\gradlew.bat build
	.\gradlew.bat bootRun
	# watch for logs such as: "[Scheduler] SBC01 Batch Start"
	```

	- For quick manual testing without an HTTP endpoint, add a temporary `CommandLineRunner` or a unit test that calls `Sbc01BatchService.execute()`.

## Sample DTO JSON

- Example minimal `Sbc01RequestBody` (what the proxy expects):

```json
{ "DATA": { "token": "<HEX_TOKEN_OR_PAYLOAD>" } }
```

- Example `Sbc01Response` (external API response mapped into project DTOs):

```json
{
	"RESULT": { "code": "00", "message": "OK" },
	"RECORD": [
		{
			"BIZR_NO": "1234567890",
			"ENT_NM": "Example Co",
			"SPCLHS_RCRUT_ACML_NOPE": 0,
			"PLAN_INTR_NOCS": 1
			/* other numeric fields present in Sbc01RecordDto */
		}
	]
}
```

## Suggested config keys (optional)

To avoid hardcoding secrets in `Sbc01BatchService`, prefer wiring keys from `application.properties`:

```
# recommended names (example):
sanhakin.sbc01.key=YOUR_AES_KEY_16_CHARS
sanhakin.sbc01.iv=YOUR_AES_IV_16_CHARS
sanhakin.sbc01.user-key=sanhakinSBC01
```

Then inject via `@Value` or a `@ConfigurationProperties` bean into `Sbc01BatchService`.

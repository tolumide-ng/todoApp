# todoApp

1. `gradlew clean build --no-build-cache`
2. `./gradlew --refresh-dependencies`
3. To run migrations: ` ./gradlew app:flywayMigrate`
4. To run tests: `./gradlew test`
5. To run tests (watch mode): `./gradlew test --continuous`




### Notes
1. Tests: set environment variable for `APPENV` first before running the test:
2. Sometimes vscode doesn't get updated immediately after/for some time after adding a new dependency, fix it by running:
   - a. cmd + shft + P
   - b Java: Clean Java Language Server Workspace

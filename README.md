# todoApp

1. `gradlew clean build --no-build-cache`
2. ` gradlew --refresh-dependencies`
3. To run migrations: ` ./gradlew app:flywayMigrate`

### Notes

1. Sometimes vscode doesn't get updated immediately after/for some time after adding a new dependency, fix it by running:
   - a. cmd + shft + P
   - b Java: Clean Java Language Server Workspace

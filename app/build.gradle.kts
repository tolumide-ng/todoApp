/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.6/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.flywaydb.flyway") version "10.15.0"
}

application {
    mainClass = "org.example.App"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    // runtimeOnly(libs.postgresql)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // runtimeOnly("org.flywaydb:flyway-database-postgresql:10.9.1")

    // This dependency is used by the application.
    // implementation(libs.guava)
    implementation(libs.javalin)
    implementation(libs.slf4j)
    implementation(libs.jackson)
    implementation(libs.jdbi.core)
    implementation(libs.jdbi.sqlobject)
    implementation(libs.jdbi.postgres)
    implementation(libs.jdbi.jackson)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.annotations)
    implementation(libs.h2database)
    implementation(libs.hikari.cp)
    implementation(libs.postgresql)

    implementation("org.jdbi:jdbi3-stringtemplate4:3.45.1")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
    // implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.0")
    // implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    // implementation("com.fasterxml.jackson.core:jackson-core:2.16.0")
    // implementation("com.google.code.gson:gson:2.10.1")
    // implementation("org.wso2.carbon:org.wso2.carbon.core:5.3.0")


}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}


buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.9.1")

    }
}

flyway {
    // postgres://{user}:{password}@{hostname}:{port}/{database-name}
    // url = "jdbc:postgresql://tolumide:postgres@localhost:5432/todoapp"
    url = "jdbc:postgresql://localhost:5432/todoapp"
    driver = "org.postgresql.Driver"
    baselineOnMigrate = true
    locations = arrayOf("filesystem:src/main/resources/db/migration/")
    // locations = arrayOf("classpath:db/migration")
}


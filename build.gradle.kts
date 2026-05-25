import java.util.*

val env = Properties()
file("env/config.env")
    .takeIf { it.exists() }?.reader()?.use { env.load(it) }

group = "hu.bozgab"
version = "0.0.1-SNAPSHOT"

val springBootVersion = "4.0.6"
val jjwtVersion = "0.13.0"

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    kotlin("plugin.jpa") version "2.0.0"
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    // Source: https://plugins.gradle.org/plugin/org.flywaydb.flyway
    id("org.flywaydb.flyway") version "12.6.2"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Source: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-webmvc
    implementation("org.springframework.boot:spring-boot-starter-webmvc:$springBootVersion")
    // Source: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    // Source: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    // Source: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")

    // Database
    // Source: https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.11")

    // Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    // Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    // Source: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

buildscript {
    dependencies {
        // Source: https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
        classpath("org.flywaydb:flyway-database-postgresql:12.6.2")
    }
}

flyway {
    url = "jdbc:postgresql://${env.getProperty("DATABASE.URL")}"
    user = env.getProperty("DATABASE.USERNAME")
    password = env.getProperty("DATABASE.PASSWORD")
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "woowacourse-precoruse"
version = "0.0.1-SNAPSHOT"
description = "우아한테크코스 8기 오픈 미션 - 백엔드 API 서버"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
	implementation("com.github.woowacourse-projects:mission-utils:1.2.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.21.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

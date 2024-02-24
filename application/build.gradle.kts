plugins {
    java
    id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
}

springBoot {
    mainClass.set("br.com.fullcycle.application.Application")
}

group = "br.com.fullcycle.application"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}



dependencies {
    implementation(project(":domain"))

    implementation("io.hypersistence:hypersistence-tsid:2.1.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("jakarta.inject:jakarta.inject-api:2.0.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")

	testRuntimeOnly("com.h2database:h2")
	implementation("junit:junit:4.12")
}

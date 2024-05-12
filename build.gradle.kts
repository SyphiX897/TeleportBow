plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = ("ir.syphix")
version = ("2.0.0")

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.sayandev.org/snapshots")
    maven("https://repo.sayandev.org/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("com.alessiodp.libby:libby-bukkit:2.0.0-SNAPSHOT")
    implementation("org.sayandev:stickynote-core:1.0.20")
    implementation("org.sayandev:stickynote-bukkit:1.0.20")
}

tasks {

    processResources {
        filesMatching(listOf("**plugin.yml", "**plugin.json")) {
            expand(
                "version" to project.version as String,
                "name" to rootProject.name,
                "description" to project.description
            )
        }
    }

    build {
        dependsOn(clean)
        dependsOn(shadowJar)
    }

    shadowJar {
        relocate("org.sayandev.stickynote", "ir.syphix.teleportbow.lib.stickynote")
        archiveFileName.set("${rootProject.name}-${version}.jar")
        archiveClassifier.set("")
        destinationDirectory.set(file(rootProject.projectDir.path + "/bin"))
        exclude("META-INF/**")
        from("LICENSE")
        minimize()
    }

    java {
        toolchain{
            languageVersion.set(JavaLanguageVersion.of(17))
        }
        withJavadocJar()
        withSourcesJar()
    }

    jar {
        enabled = false
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenBuild") {
            from(components["java"])
            pom {
                name.set("teleportbow")
                description.set(rootProject.description)
                url.set("https://github.com/SyphiX897/TeleportBow")
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://github.com/SyphiX897/TeleportBow/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("syphix")
                        name.set("nima")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/syphix897/teleportbow.git")
                    developerConnection.set("scm:git:ssh://github.com/syphix897/teleportbow.git")
                    url.set("https://github.com/SyphiX897/TeleportBow/tree/master")
                }
            }
        }
    }

    repositories {
        maven {
            name = "sayandev-repo"
            url = uri("https://repo.sayandev.org/snapshots/")

            credentials {
                username = project.findProperty("repo.sayan.user") as String
                password = project.findProperty("repo.sayan.token") as String
            }
        }
    }
}

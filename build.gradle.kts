plugins {
    java

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

val jenkinsVersion = "1.11.7-b$buildNumber"

group = "com.badbones69.crazycrates"
version = "1.11.7"
description = "Add unlimited crates to your server with 10 different crate types to choose from!"

repositories {

    /**
     * PAPI Team
     */
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    /**
     * NBT Team
     */
    maven("https://repo.codemc.org/repository/maven-public/")

    /**
     * Paper Team
     */
    maven("https://repo.papermc.io/repository/maven-public/")

    /**
     * Triumph Team
     */
    maven("https://repo.triumphteam.dev/snapshots/")

    /**
     * Vault Team
     */
    maven("https://jitpack.io/")

    /**
     * Minecraft Team
     */
    maven("https://libraries.minecraft.net/")

    /**
     * Everything else we need.
     */
    mavenCentral()
}

dependencies {
    implementation(libs.triumph.cmds)
    //implementation(libs.triumph.guis)

    implementation(libs.bukkit.bstats)

    implementation(libs.nbt.api)

    compileOnly(libs.holographic.displays)
    compileOnly(libs.decent.holograms)

    compileOnly(libs.placeholder.api)

    compileOnly(libs.vault.api)

    compileOnly(libs.paper)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    shadowJar {
        if (buildNumber != null) {
            archiveFileName.set("${rootProject.name}-[v${jenkinsVersion}].jar")
        } else {
            archiveFileName.set("${rootProject.name}-[v${rootProject.version}].jar")
        }

        listOf(
            "de.tr7zw",
            "org.bstats",
            //"dev.triumphteam.gui",
            "dev.triumphteam.cmd"
        ).forEach {
            relocate(it, "${rootProject.group}.plugin.lib.$it")
        }
    }

    compileJava {
        options.release.set(17)
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "group" to rootProject.group,
                "version" to rootProject.version,
                "description" to rootProject.description
            )
        }
    }
}
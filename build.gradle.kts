plugins {
	id("fabric-loom") version "0.6-SNAPSHOT"
	id("maven-publish")
}

val modVersion: String by project
val mavenGroup: String by project
version = modVersion
group = mavenGroup

val sodiumVersion: String by project

dependencies {
	val minecraftVersion: String by project
	val yarnMappings: String by project
	val loaderVersion: String by project
	minecraft("com.mojang:minecraft:${minecraftVersion}")
	mappings("net.fabricmc:yarn:${yarnMappings}:v2")
	modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
	modImplementation("com.github.CaffeineMC:sodium-fabric:${sodiumVersion}")
}

tasks.processResources {
	inputs.property("version", modVersion)

	filesMatching("fabric.mod.json") {
		expand(mutableMapOf(
				"version" to modVersion
		))
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	sourceCompatibility = "1.8"
	targetCompatibility = "1.8"
}

tasks.withType<AbstractArchiveTask> {
	val archivesBaseName: String by project
	archiveBaseName.set(archivesBaseName)
}

tasks.jar {
	from("LICENSE.md")
	from("LICENSE.LESSER.md")
}

publishing {
	publications {
		create("mavenJava", MavenPublication::class.java) {
			artifact(tasks.remapJar) {
				builtBy(tasks.remapJar)
			}
		}
	}
}

repositories {
	maven("https://jitpack.io") {
		content {
			includeGroup("com.github.CaffeineMC")
		}
	}
}

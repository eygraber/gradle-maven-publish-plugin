package com.vanniktech.maven.publish.tasks

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar

open class SourcesJar : Jar() {

  init {
    archiveClassifier.set("sources")
  }

  internal companion object {
    private fun Project.emptySourcesJar(): TaskProvider<*> = tasks.register("emptySourcesJar", SourcesJar::class.java)

    internal fun Project.javaSourcesJar(sourcesJar: Boolean): TaskProvider<*> {
      if (!sourcesJar) {
        return emptySourcesJar()
      }

      return tasks.register("javaSourcesJar", SourcesJar::class.java) {
        val javaPlugin = convention.getPlugin(JavaPluginConvention::class.java)
        it.from(javaPlugin.sourceSets.getByName("main").allSource)
      }
    }

    internal fun Project.kotlinSourcesJar(sourcesJar: Boolean): TaskProvider<*> {
      if (!sourcesJar) {
        return emptySourcesJar()
      }

      return project.tasks.named("kotlinSourcesJar")
    }
  }
}

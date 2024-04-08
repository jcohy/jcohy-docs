package com.jcohy.docs.build;

import java.io.File;

import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginManager;
import org.gradle.api.tasks.TaskContainer;

/**
 * Copyright: Copyright (c) 2023 <a href="https://www.jcohy.com" target="_blank">jcohy.com</a>
 * <p> Description:
 *
 * @author jiac
 * @version 2024.0.1 2024/1/16 10:24
 * @since 2024.0.1
 */
public class JcohyDocsPlugins implements Plugin<Project> {

	@Override
	public void apply(Project project) {
// Apply default plugins
		PluginManager pluginManager = project.getPluginManager();
		pluginManager.apply(BasePlugin.class);
		pluginManager.apply(JavaPlugin.class);
//		pluginManager.apply(SpringManagementConfigurationPlugin.class);
//		pluginManager.apply(SpringRepositoryPlugin.class);
//		pluginManager.apply(SpringAsciidoctorPlugin.class);
//		// Note: Applying plugin via id since it requires groovy compilation
//		pluginManager.apply("org.springframework.gradle.deploy-docs");
//		pluginManager.apply(SpringJavadocApiPlugin.class);
//		pluginManager.apply(SpringJavadocOptionsPlugin.class);

		TaskContainer tasks = project.getTasks();
		project.configure(tasks.withType(AbstractAsciidoctorTask.class), (task) -> {
			File destination = new File(project.getBuildDir(), "docs");
			task.setOutputDir(destination);
			task.sources((patternSet) -> {
				patternSet.include("**/*.adoc");
				patternSet.exclude("_*/**");
			});
		});
	}
}

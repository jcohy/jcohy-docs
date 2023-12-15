package com.jcohy.docs.build;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.github.jcohy.gradle.asciidoctor.AsciidoctorConventionsPlugin;
import io.github.jcohy.gradle.conventions.ConventionsPlugin;
import io.github.jcohy.gradle.deployed.DeployedPlugin;
import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJExtension;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.asciidoctor.gradle.jvm.AsciidoctorTask;
import org.asciidoctor.gradle.jvm.pdf.AsciidoctorPdfTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Sync;

/**
 * Copyright: Copyright (c) 2021 <a href="https://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/7/5:23:11
 * @since 1.0.0
 */
public class JcohyAsciidoctorPlugins implements Plugin<Project> {

	private final Set<String> excludePdfProject = Set.of("spring-security","spring-boot","spring-framework");

    @Override
    public void apply(Project project) {
        PluginContainer plugins = project.getPlugins();
		String version = project.getVersion().toString();
        if(version.equalsIgnoreCase("unspecified")) {
            version = ProjectVersion.getVersionFromName(project.getName());
        }
		project.setVersion(version);

        plugins.apply(AsciidoctorJPlugin.class);
        plugins.apply(AsciidoctorConventionsPlugin.class);
        plugins.apply(ConventionsPlugin.class);
        plugins.apply(DeployedPlugin.class);

		configureAsciidoctorTask(project);

		createAggregatedTask(project);
    }

	private void createAggregatedTask(Project project) {
		File syncSource = new File(project.getRootProject().getBuildDir(),"reference/");
		project.getTasks().create("aggregatedProject", Copy.class,aggregatedProject -> {
			aggregatedProject.setGroup("documentation");

			aggregatedProject.setDestinationDir(syncSource);

			if(project.getTasks().getNames().contains("asciidoctor")) {
				Task asciidoctor = project.getTasks().getByName("asciidoctor");
				aggregatedProject.dependsOn(asciidoctor);

				aggregatedProject.from(asciidoctor.getOutputs(),spec -> {
					spec.into(project.getName()+"/"+project.getVersion()+"/htmlsingle");
				});
			}

			if(project.getTasks().getNames().contains("asciidoctorPdf")) {
				if(!excludePdfProject.contains(project.getName())) {
					Task asciidoctorPdf = project.getTasks().getByName("asciidoctorPdf");
					aggregatedProject.dependsOn(asciidoctorPdf);
					aggregatedProject.from(asciidoctorPdf.getOutputs(),spec -> {
						spec.into(project.getName()+"/"+project.getVersion()+"/pdf");
					});
				}
			}

			if(project.getTasks().getNames().contains("asciidoctorMultiPage")) {
				Task multiPage = project.getTasks().getByName("asciidoctorMultiPage");
				aggregatedProject.dependsOn(multiPage);
				aggregatedProject.from(multiPage.getOutputs(),spec -> {
					spec.into(project.getName()+"/"+project.getVersion()+"/html5");
				});
			}
		});
	}

	private void configureAsciidoctorTask(Project project) {
		project.getTasks().withType(AsciidoctorTask.class,asciidoctorTask -> {
			asciidoctorTask.setLanguages(List.of("zh-cn"));
			asciidoctorTask.setLogDocuments(true);
			configureCommonAttributes(project, asciidoctorTask);
			project.getExtensions().getByType(AsciidoctorJExtension.class).fatalWarnings(false);
		});
    }

    private void configureCommonAttributes(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        Map<String, Object> attributes = ProjectVersion.getAttributesMap();
        attributes.put("spring-boot-xsd-version",getVersion());
        Map<String, Object> docsUrlMaps = ProjectVersion.getDocsUrlMaps();
        addAsciidoctorTaskAttributes(project,attributes);
        asciidoctorTask.attributes(attributes);
        asciidoctorTask.attributes(docsUrlMaps);
    }

    // 获取 spring-boot-xsd-version
    private String getVersion() {
        String[] versionEl = ProjectVersion.SPRING_BOOT.getVersion().split("\\.");
        return versionEl[0] + "." + versionEl[1];
    }

    private void addAsciidoctorTaskAttributes(Project project,Map<String, Object> attributes) {
        attributes.put("author", "Author: Jcohy ");
        attributes.put("email", "Email: jia_chao23@126.com ");
        attributes.put("rootProject", project.getRootProject().getProjectDir());
        attributes.put("sources-root", project.getProjectDir() + "/src");
        attributes.put("image-resource", "https://resources.jcohy.com/jcohy-docs/images/" + ProjectVersion.getVersionfromAttr("spring-boot-version") + "/" + project.getName());
        attributes.put("spring-api-doc", "https://docs.spring.io/" + project.getName());
        attributes.put("doc-root","https://docs.jcohy.com");

		attributes.put("docs-java",project.getProjectDir() + "/src/main/java/org/springframework/docs");
		attributes.put("docs-kotlin",project.getProjectDir() + "/src/main/kotlin/org/springframework/docs");
		attributes.put("docs-groovy",project.getProjectDir() + "/src/main/groovy/org/springframework/docs");

        if(project.getName().startsWith("spring-boot")) {
            attributes.put("docs-java",project.getProjectDir() + "/src/main/java/org/springframework/boot/docs");
            attributes.put("docs-kotlin",project.getProjectDir() + "/src/main/kotlin/org/springframework/boot/docs");
            attributes.put("docs-groovy",project.getProjectDir() + "/src/main/groovy/org/springframework/boot/docs");
        }
        attributes.put("spring-docs-prefix","https://docs.spring.io/spring-framework/docs/");
        attributes.put("gh-samples-url","https://github.com/spring-projects/spring-security/master/");
        attributes.put("version",ProjectVersion.getVersionFromName(project.getName()));
        attributes.put("releasetrainVersion",ProjectVersion.getVersionFromName("spring-data-bom"));
        attributes.put("revnumber",ProjectVersion.getVersionFromName(project.getName()));
        if(project.getName().startsWith("spring")) {
            attributes.put("native-build-tools-version","0.9.18");
        }
    }
}

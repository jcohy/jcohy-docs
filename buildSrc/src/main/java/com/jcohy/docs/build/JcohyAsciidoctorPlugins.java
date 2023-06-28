package com.jcohy.docs.build;

import java.util.Map;

import io.github.jcohy.gradle.asciidoctor.AsciidoctorConventionsPlugin;
import io.github.jcohy.gradle.conventions.ConventionsPlugin;
import io.github.jcohy.gradle.deployed.DeployedPlugin;
import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJExtension;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.asciidoctor.gradle.jvm.AsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

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
        project.afterEvaluate(p -> p.getTasks().withType(AsciidoctorTask.class, asciidoctorTask -> {
           configureAsciidoctorTask(p, asciidoctorTask);
        }));
    }

    private void configureAsciidoctorTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        asciidoctorTask.languages("zh-cn");
        asciidoctorTask.setLogDocuments(true);

        if (asciidoctorTask.getName().equals("asciidoctorMultiPage")) {
            asciidoctorTask.sources(patternSet -> {
                patternSet.include("*.adoc","index.multiadoc");
                patternSet.exclude("index.adoc");
            });
//            asciidoctorTask.sources(new String[]{"(?!^index).adoc", "index.multiadoc"});
        }
        configureCommonAttributes(project, asciidoctorTask);
        project.getExtensions().getByType(AsciidoctorJExtension.class).fatalWarnings(false);
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
        attributes.put("author", "Author:Jcohy");
        attributes.put("email", "Email:jia_chao23@126.com");
        attributes.put("rootProject", project.getRootProject().getProjectDir());
        attributes.put("sources-root", project.getProjectDir() + "/src");
        attributes.put("image-resource", "https://resources.jcohy.com/jcohy-docs/images/" + ProjectVersion.getVersionfromAttr("spring-boot-version") + "/" + project.getName());
        attributes.put("spring-api-doc", "https://docs.spring.io/" + project.getName());
        attributes.put("doc-root","https://docs.jcohy.com");
        if(project.getName().startsWith("spring-boot")) {
            attributes.put("docs-java",project.getProjectDir() + "/src/main/java/org/springframework/boot/docs");
            attributes.put("docs-kotlin",project.getProjectDir() + "/src/main/kotlin/org/springframework/boot/docs");
            attributes.put("docs-groovy",project.getProjectDir() + "/src/main/groovy/org/springframework/boot/docs");
        } else {
            attributes.put("docs-java",project.getProjectDir() + "/src/main/java/org/springframework/docs");
            attributes.put("docs-kotlin",project.getProjectDir() + "/src/main/kotlin/org/springframework/docs");
            attributes.put("docs-groovy",project.getProjectDir() + "/src/main/groovy/org/springframework/docs");
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

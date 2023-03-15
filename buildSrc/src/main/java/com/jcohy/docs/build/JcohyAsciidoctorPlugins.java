package com.jcohy.docs.build;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.jcohy.convention.conventions.ConventionsPlugin;
import com.jcohy.convention.deployed.DeployedPlugin;
import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJExtension;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.plugins.PluginContainer;
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

    private List<String> singlePage = Lists.newArrayList("spring-boot","spring-framework","spring-security");

    @Override
    public void apply(Project project) {
        PluginContainer plugins = project.getPlugins();
        plugins.apply(AsciidoctorJPlugin.class);
        plugins.apply(ConventionsPlugin.class);
        plugins.apply(DeployedPlugin.class);
        project.setVersion(ProjectVersion.getVersionFromName(project.getName()));
        plugins.withType(AsciidoctorJPlugin.class, (asciidoctorPlugin) -> project.getTasks().withType(AbstractAsciidoctorTask.class, (asciidoctorTask) -> configureAsciidoctorTask(project, asciidoctorTask)));
    }

    private void configureAsciidoctorTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        asciidoctorTask.languages("zh-cn");
        if(singlePage.contains(project.getName()) && !asciidoctorTask.getName().equals("asciidoctorMultipage")) {
            asciidoctorTask.sources("*.singleadoc");
        } else {
            asciidoctorTask.sources("index.adoc");
        }
        asciidoctorTask.setLogDocuments(true);
        configureCommonAttributes(project, asciidoctorTask);
        project.getExtensions().getByType(AsciidoctorJExtension.class).fatalWarnings(false);
        project.getTasks()
                .withType(Sync.class, (sync -> sync.from("src/main/resources", (spec) -> {
                    spec.into("main/resources");
                    spec.setDuplicatesStrategy(DuplicatesStrategy.INCLUDE);
                })));
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
        attributes.put("rootProject", project.getRootProject().getProjectDir());
        attributes.put("sources-root", project.getProjectDir() + "/src");
        attributes.put("image-resource", "https://resources.jcohy.com/jcohy-docs/images/" + ProjectVersion.getVersionfromAttr("spring-boot-version") + "/" + project.getName());
        attributes.put("spring-api-doc", "https://docs.spring.io/" + project.getName());
        attributes.put("doc-root","https://docs.jcohy.com");
        attributes.put("spring-docs-prefix","https://docs.spring.io/spring-framework/docs/");
        attributes.put("gh-samples-url","https://github.com/spring-projects/spring-security/master/");

        if(project.getName().startsWith("spring")) {
            attributes.put("native-build-tools-version","0.9.18");
        }
    }
}

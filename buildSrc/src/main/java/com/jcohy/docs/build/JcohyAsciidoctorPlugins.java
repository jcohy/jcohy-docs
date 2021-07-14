package com.jcohy.docs.build;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jcohy.convention.conventions.ConventionsPlugin;
import com.jcohy.convention.deployed.DeployedPlugin;
import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.asciidoctor.gradle.jvm.AsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Sync;
import org.springframework.util.StringUtils;
/**
 * Copyright: Copyright (c) 2021 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
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
        plugins.apply(AsciidoctorJPlugin.class);
        plugins.apply(ConventionsPlugin.class);
        plugins.apply(DeployedPlugin.class);
        plugins.withType(AsciidoctorJPlugin.class,(asciidoctorPlugin) -> {
            project.getTasks().withType(AbstractAsciidoctorTask.class, (asciidoctorTask) -> {
                configureAsciidoctorPdfTask(project);
                configureAsciidoctorTask(project, asciidoctorTask);
            });
        });
    }

    private void configureAsciidoctorPdfTask(Project project) {
        AsciidoctorTask asciidoctorPdf = project.getTasks().maybeCreate("asciidoctorPdf", AsciidoctorTask.class);
        asciidoctorPdf.sources("index.adoc");
    }

    private void configureAsciidoctorTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        asciidoctorTask.sources("index.adoc");
        configureCommonAttributes(project, asciidoctorTask);
        // 修改源目录
        project.getTasks().withType(Sync.class,(sync -> {
            if(sync.getName().startsWith("syncDocumentationSourceFor")){
                File syncedSource = sync.getDestinationDir();
                asciidoctorTask.getInputs().dir(syncedSource);
                asciidoctorTask.setSourceDir(project.relativePath(new File(syncedSource, "asciidoc/zh-cn")));
            }
        }));

        // 替换 logo
        asciidoctorTask.doLast((replaceIcon) -> {
            project.delete(project.getBuildDir() + "/docs/asciidoc/img/banner-logo.svg");
            project.copy((copySpec -> {
                copySpec.from(project.getBuildDir() + "/docs/asciidoc/images/banner-logo.svg");
                copySpec.into(project.getBuildDir() + "/docs/asciidoc/img");
            }));
        });
    }

    private void configureCommonAttributes(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("allow-uri-read", true);
//        attributes.put("resource-url", "http://resources.jcohy.com");
//        attributes.put("software-url", "http://software.jcohy.com");
//        attributes.put("study-url", "http://study.jcohy.com");
//        attributes.put("project-url", "http://project.jcohy.com");
//        attributes.put("revnumber", null);
        asciidoctorTask.attributes(attributes);
    }
}

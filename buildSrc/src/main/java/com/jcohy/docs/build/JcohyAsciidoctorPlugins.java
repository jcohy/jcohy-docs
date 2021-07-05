package com.jcohy.docs.build;

import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJExtension;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.asciidoctor.gradle.jvm.AsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Sync;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private static final String ASCIIDOCTORJ_VERSION = "2.4.3";

    private static final String EXTENSIONS_CONFIGURATION_NAME = "asciidoctorExtensions";


    @Override
    public void apply(Project project) {
        project.getPlugins().withType(AsciidoctorJPlugin.class,(asciidoctorPlugin) -> {
            configureDocumentationDependenciesRepository(project);
            makeAllWarningsFatal(project);
            upgradeAsciidoctorJVersion(project);
            createAsciidoctorExtensionsConfiguration(project);
            project.getTasks().withType(AbstractAsciidoctorTask.class,
                    (asciidoctorTask) -> configureAsciidoctorTask(project, asciidoctorTask));
        });

    }

    /**
     * 添加文档依赖仓库
     * @param project project
     */
    private void configureDocumentationDependenciesRepository(Project project) {
        project.getRepositories().maven((mavenRepo) -> {
            mavenRepo.setUrl(URI.create("https://repo.spring.io/release"));
            mavenRepo.mavenContent((mavenContent) -> {
                mavenContent.includeGroup("io.spring.asciidoctor");
                mavenContent.includeGroup("io.spring.asciidoctor.backends");
                mavenContent.includeGroup("io.spring.docresources");
            });
        });
    }

    /**
     *  添加警告错误
     * @param project project
     */
    private void makeAllWarningsFatal(Project project) {
        project.getExtensions().getByType(AsciidoctorJExtension.class).fatalWarnings(".*");
    }

    /**
     * 创建 asciidoctorExtensions 配置
     * @param project project
     */
    private void createAsciidoctorExtensionsConfiguration(Project project) {
        project.getConfigurations().create(EXTENSIONS_CONFIGURATION_NAME, (configuration) -> {
            project.getConfigurations().matching((candidate) -> "dependencyManagement".equals(candidate.getName()))
                    .all(configuration::extendsFrom);
            configuration.getDependencies().add(project.getDependencies()
                    .create("io.spring.asciidoctor.backends:spring-asciidoctor-backends:0.0.2"));
            configuration.getDependencies()
                    .add(project.getDependencies().create("org.asciidoctor:asciidoctorj-pdf:1.5.3"));
        });
    }

    /**
     * 配置 asciidoctorTask
     * @param project project
     * @param asciidoctorTask asciidoctorTask
     */
    private void configureAsciidoctorTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        asciidoctorTask.configurations(EXTENSIONS_CONFIGURATION_NAME);
        configureCommonAttributes(project, asciidoctorTask);
        configureOptions(asciidoctorTask);
        asciidoctorTask.baseDirFollowsSourceDir();
        createSyncDocumentationSourceTask(project, asciidoctorTask);
        if (asciidoctorTask instanceof AsciidoctorTask) {
            boolean pdf = asciidoctorTask.getName().toLowerCase().contains("pdf");
            String backend = (!pdf) ? "spring-html" : "spring-pdf";
            ((AsciidoctorTask) asciidoctorTask).outputOptions((outputOptions) -> outputOptions.backends(backend));
        }
    }


    /**
     * AsciidoctorJ 版本为 2.4.1.
     * @param project project
     */
    private void upgradeAsciidoctorJVersion(Project project) {
        project.getExtensions().getByType(AsciidoctorJExtension.class).setVersion(ASCIIDOCTORJ_VERSION);
    }

    /**
     * 设置文档的 doctpye 类型
     * @param asciidoctorTask asciidoctorTask
     */
    private void configureOptions(AbstractAsciidoctorTask asciidoctorTask) {
        asciidoctorTask.options(Collections.singletonMap("doctype", "book"));
    }

    /**
     * 设置通用属性
     * @param project project
     * @param asciidoctorTask asciidoctorTask
     */
    private void configureCommonAttributes(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("attribute-missing", "warn");
        attributes.put("github-tag", determineGitHubTag(project));
        attributes.put("spring-boot-artifactory-repo", determineArtifactoryRepo(project));
        attributes.put("revnumber", null);
        asciidoctorTask.attributes(attributes);
    }

    private static String determineArtifactoryRepo(Project project) {
        String version = project.getVersion().toString();
        int modifierIndex = version.lastIndexOf('-');
        if (modifierIndex == -1) {
            return "release";
        }
        String type = version.substring(modifierIndex + 1);
        if (type.startsWith("M") || type.startsWith("RC")) {
            return "milestone";
        }
        return "snapshot";
    }

    private String determineGitHubTag(Project project) {
        String version = "v" + project.getVersion();
        return (version.endsWith("-SNAPSHOT")) ? "2.4.x" : version;
    }

    /**
     * 设置 html 属性
     * @param asciidoctorTask asciidoctorTask
     */
    private void configureHtmlOnlyAttributes(AbstractAsciidoctorTask asciidoctorTask) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("source-highlighter", "highlightjs");
        attributes.put("highlightjsdir", "js/highlight");
        attributes.put("highlightjs-theme", "github");
        attributes.put("linkcss", true);
        attributes.put("icons", "font");
        attributes.put("stylesheet", "css/spring.css");
        asciidoctorTask.attributes(attributes);
    }

    /**
     * 异步创建文档源文件
     * @param project project
     * @param asciidoctorTask asciidoctorTask
     * @return /
     */
    private Sync createSyncDocumentationSourceTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        Sync syncDocumentationSource = project.getTasks()
                .create("syncDocumentationSourceFor" + StringUtils.capitalize(asciidoctorTask.getName()), Sync.class);
        File syncedSource = new File(project.getBuildDir(), "docs/src/" + asciidoctorTask.getName());
        syncDocumentationSource.setDestinationDir(syncedSource);
        syncDocumentationSource.from("/src/docs");
        asciidoctorTask.dependsOn(syncDocumentationSource);
        asciidoctorTask.getInputs().dir(syncedSource);
        asciidoctorTask.setSourceDir(project.relativePath(new File(syncedSource, "asciidoc/")));
        return syncDocumentationSource;
    }

}

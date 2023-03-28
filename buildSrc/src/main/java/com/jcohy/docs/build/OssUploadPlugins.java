package com.jcohy.docs.build;

import io.github.jcohy.gradle.oss.OssUploadPlugin;
import io.github.jcohy.gradle.oss.OssUploadTask;
import io.github.jcohy.gradle.oss.dsl.AliOssExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Copyright: Copyright (c) 2021 <a href="https://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/7/28:1:01
 * @since 1.0.0
 */
public class OssUploadPlugins implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(OssUploadPlugin.class);
        project.getTasks().withType(OssUploadTask.class, (ossUploadTask) -> {
            ossUploadTask.dependsOn("aggregatedAsciidoctor");
        });
        AliOssExtension extension = project.getExtensions().getByType(AliOssExtension.class);
//        extension.setAccessKey("xxx");
//        extension.setSecretKey("xxx");
        extension.setBucket("jcohy-docs");
        String buildDir = project.getRootProject().getBuildDir().getName();
        extension.getUpload().setSource(buildDir);
        extension.getUpload().setPrefix("/docs");

        extension.getUpload().setIgnoreSourceDir(true);
    }
}

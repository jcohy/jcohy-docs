package com.jcohy.docs.build;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 描述: .
 * Copyright: Copyright (c) 2021.
 * <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2021/7/16:15:28
 * @since 1.0.0
 */
public enum ProjectVersion {

    GRADLE_DOCS("gradle-docs","gradle-version","6.7",""),
    CHECKSTYLE("checkstyle","checkstyle-version","8.44",""),
    REACTOR("reactor","reactor-version","3.3.1.RELEASE",""),
    RFC("rfc","rfc-version","1.0.0.RELEASE",""),
    SPRING_FRAMEWORK("spring-framework","spring-framework-version","5.3.6",""),
    SPRING_HATEOAS("spring-hateoas","spring-hateoas-version","1.2.5",""),
    SPRING_SECURITY("spring-security","spring-security-version","5.4.6",""),
    SPRING_INTEGRATION("spring-integration","spring-integration-version","5.4.6",""),
    SPRING_WEBSERVICES("spring-webservices","spring-webservices-version","3.0.10.RELEASE",""),
    SPRING_BOOT("spring-boot","spring-boot-version","2.4.5",""),
    SPRING_BOOT_ACTUATOR_AUTOCONFIGURE("spring-boot-actuator-autoconfigure","spring-boot-version","2.4.5",""),
    SPRING_DATA_COMMONS("spring-data-commons","spring-data-commons-version","2.4.8",""),
    SPRING_DATA_ELASTICSEARCH("spring-data-elasticsearch","spring-data-elasticsearch-version","4.1.8",""),
    SPRING_DATA_JPA("spring-data-jpa","spring-data-jpa-version","2.4.8",""),
    SPRING_DATA_REDIS("spring-data-redis","spring-data-redis-version","2.4.8",""),
    SPRING_DATA_REST("spring-data-rest","spring-data-rest-version","3.4.8",""),
    SPRING_GRADLE_PLUGINS("spring-gradle-plugins","spring-boot-version","2.4.5",""),
    SPRING_MAVEN_PLUGINS("spring-maven-plugins","spring-boot-version","2.4.5",""),
    SPRING_CLOUD("spring-cloud","spring-cloud-version","Hoxton SR3","")
    ;
    private final String name;

    private final String attr;

    private final String version;

    private final String url;

    ProjectVersion(String name,String attr, String version,String url) {
        this.attr = attr;
        this.name = name;
        this.version = version;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getAttr() {
        return attr;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public static String getVersionFromName(String projectName){
        for(ProjectVersion projectVersion : ProjectVersion.values()){
            if(projectVersion.getName().equalsIgnoreCase(projectName)){
                return projectVersion.getVersion();
            }
        }
        return "unspecified";
    }

    public static String getVersionfromAttr(String attr){
        for(ProjectVersion projectVersion : ProjectVersion.values()){
            if(projectVersion.getAttr().equalsIgnoreCase(attr)){
                return projectVersion.getVersion();
            }
        }
        return "";
    }

    public static Map<String,Object> getAttributesMap(){
        Map<String,Object> map = new HashMap<>(16);
        for(ProjectVersion projectVersion : ProjectVersion.values()){
            map.put(projectVersion.getAttr(),projectVersion.getVersion());
        }
        return map;
    }

    public static Map<String,Object> getDocsUrlMaps(){
        Map<String,Object> map = new HashMap<>(16);
        // https://docs.jcohy.com/docs/spring-framework/5.3.6/html5/zh-cn/index.html
        String docsRoot ="http://docs.jcohy.com/docs/";
        for(ProjectVersion projectVersion : ProjectVersion.values()){
            String url = projectVersion.getUrl();
            if(url.isEmpty()){
                map.put(projectVersion.getName() + "-docs", docsRoot +
                        projectVersion.getName() + "/" + projectVersion.getVersion() + "/html5/zh-cn/index.html");
            } else {
                map.put(projectVersion.getName() + "-docs", projectVersion.getUrl());
            }
        }

        return map;
    }
}

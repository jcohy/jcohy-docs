package com.jcohy.docs.build;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 描述: .
 * Copyright: Copyright (c) 2021.
 * <a href="https://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2021/7/16:15:28
 * @since 1.0.0
 */
public enum ProjectVersion {

    GRADLE_DOCS("gradle-docs","gradle-version","6.9.3",""),
    CHECKSTYLE("checkstyle","checkstyle-version","8.44",""),
    REACTOR("reactor","reactor-version","3.4.8",""),
    REACTIVE_STREAM("reactive-stream-jvm","reactive-stream-jvm-version","1.0.3",""),
    RFC("rfc","rfc-version","1.0.0.RELEASE",""),
    HIBERNATE_ORM("hibernate-orm","hibernate-orm-version","5.5.7",""),
    DEPENDENCY_MANAGEMENT_PLUGIN("dependency-management-plugin","dependency-management-plugin-version","1.0.11.RELEASE",""),

    SPRING_BOOT("spring-boot","spring-boot-version","3.0.0",""),
    SPRING_BOOT_ACTUATOR_AUTOCONFIGURE("spring-boot-actuator-autoconfigure","spring-boot-actuator-autoconfigure-version",ProjectVersion.SPRING_BOOT.getVersion(),""),
    SPRING_BOOT_GRADLE_PLUGINS("spring-gradle-plugins","spring-gradle-plugins-version",ProjectVersion.SPRING_BOOT.getVersion(),""),
    SPRING_BOOT_MAVEN_PLUGINS("spring-maven-plugins","spring-maven-plugins-version",ProjectVersion.SPRING_BOOT.getVersion(),""),
    SPRING_FRAMEWORK("spring-framework","spring-framework-version","6.0.2",""),
    SPRING_AMQP("spring-amqp","spring-amqp-version","3.0.0","https://docs.spring.io/spring-amqp/docs/2.4.6/reference/html/"),
    SPRING_BATCH("spring-batch","spring-batch-version","5.0.0","https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/"),
    SPRING_DATA_BOM("spring-data-bom","spring-data-bom-version","2022.0.0",""),
    SPRING_HATEOAS("spring-hateoas","spring-hateoas-version","2.0.0",""),
    SPRING_INTEGRATION("spring-integration","spring-integration-version","6.0.0","https://docs.spring.io/spring-integration/docs/5.5.13/reference/html/"),
    SPRING_KAFKA("spring-kafka","spring-kafka-version","3.0.0","https://docs.spring.io/spring-kafka/docs/2.8.7/reference/html/"),
    SPRING_LDAP("spring-ldap","spring-ldap-version","3.0.0",""),
    SPRING_RESTDOCS("spring-restdocs","spring-restdocs-version","3.0.0",""),
    SPRING_RETRY("spring-retry","spring-retry-version","2.0.0",""),
    SPRING_SECURITY("spring-security","spring-security-version","6.0.0",""),
    SPRING_WS("spring-ws","spring-ws-version","4.0.0","https://docs.spring.io/spring-ws/docs/3.1.3/reference/html/"),
    SPRING_GRAPHQL("spring-graphql","spring-graphql-version","1.1.0",""),

    SPRING_DATA_CASSANDRA("spring-data-cassandra","spring-data-cassandra-version","4.0.0",""),
    SPRING_DATA_COMMONS("spring-data-commons","spring-data-commons-version","3.0.0",""),
    SPRING_DATA_COUCHBASE("spring-data-couchbase","spring-data-couchbase-version","5.0.0","https://docs.spring.io/spring-data/couchbase/docs/4.1.8/reference/html/"),
    SPRING_DATA_ELASTICSEARCH("spring-data-elasticsearch","spring-data-elasticsearch-version","5.0.0",""),
    SPRING_DATA_GEODE("spring-data-geode","spring-data-geode-version","3.0.0",""),
    SPRING_DATA_JDBC("spring-data-jdbc","spring-data-jdbc-version","3.0.0",""),
    SPRING_DATA_RELATIONAL("spring-data-relational","spring-data-relational-version","3.0.0",""),
    SPRING_DATA_JPA("spring-data-jpa","spring-data-jpa-version","3.0.0",""),
    SPRING_DATA_MONGODB("spring-data-mongodb","spring-data-mongodb-version","4.0.0","https://docs.spring.io/spring-data/mongodb/docs/3.3.6/reference/html/"),
    SPRING_DATA_NEO4J("spring-data-neo4j","spring-data-neo4j-version","7.0.0","https://docs.spring.io/spring-data/neo4j/docs/6.3.1/reference/html/"),
    SPRING_DATA_R2DBC("spring-data-r2dbc","spring-data-r2dbc-version","3.0.0",""),
    SPRING_DATA_REDIS("spring-data-redis","spring-data-redis-version","3.0.0",""),
    SPRING_DATA_REST("spring-data-rest","spring-data-rest-version","4.0.0",""),
    SPRING_DATA_KEYVALUE("spring-data-keyvalue","spring-data-keyvalue-version","3.0.0",""),
    SPRING_DATA_ENVERS("spring-data-envers","spring-data-envers-version","3.0.0",""),
    SPRING_DATA_LDAP("spring-data-ldap","spring-data-ldap-version","3.0.0",""),

    SPRING_CLOUD("spring-cloud","spring-cloud-version","2022.0.0",""),
    SPRING_CLOUD_BUS("spring-cloud-bus","spring-cloud-bus-version","4.0.0",""),
    SPRING_CLOUD_CIRCUITBREAKER("spring-cloud-circuitbreaker","spring-cloud-circuitbreaker-version","3.0.0",""),
    SPRING_CLOUD_CLI("spring-cloud-cli","spring-cloud-cli-version","4.0.0",""),
    SPRING_CLOUD_CLOUDFOUNDRY("spring-cloud-cloudfoundry","spring-cloud-cloudfoundry-version","4.0.0",""),
    SPRING_CLOUD_COMMONS("spring-cloud-commons","spring-cloud-commons-version","4.0.0",""),
    SPRING_CLOUD_CONFIG("spring-cloud-config","spring-cloud-config-version","4.0.0",""),
    SPRING_CLOUD_CONSUL("spring-cloud-consul","spring-cloud-consul-version","4.0.0",""),
    SPRING_CLOUD_CONTRACT("spring-cloud-contract","spring-cloud-contract-version","4.0.0",""),
    SPRING_CLOUD_FUNCTION("spring-cloud-function","spring-cloud-function-version","4.0.0",""),
    SPRING_CLOUD_GATEWAY("spring-cloud-gateway","spring-cloud-gateway-version","4.0.0",""),
    SPRING_CLOUD_KUBERNETES("spring-cloud-kubernetes","spring-cloud-kubernetes-version","3.0.0",""),
    SPRING_CLOUD_NETFLIX("spring-cloud-netflix","spring-cloud-netflix-version","4.0.0",""),
    SPRING_CLOUD_OPENFEIGN("spring-cloud-openfeign","spring-cloud-openfeign-version","4.0.0",""),
    SPRING_CLOUD_SLEUTH("spring-cloud-sleuth","spring-cloud-sleuth-version","4.0.0",""),
    SPRING_CLOUD_STREAM("spring-cloud-stream","spring-cloud-stream-version","4.0.0",""),
    SPRING_CLOUD_TASK("spring-cloud-task","spring-cloud-task-version","3.0.0",""),
    SPRING_CLOUD_VAULT("spring-cloud-vault","spring-cloud-vault-version","4.0.0",""),
    SPRING_CLOUD_ZOOKEEPER("spring-cloud-zookeeper","spring-cloud-zookeeper-version","4.0.0",""),
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
        for(ProjectVersion projectVersion : ProjectVersion.values()){
            String url = projectVersion.getUrl();
            if(url.isEmpty()){
                map.put(projectVersion.getName() + "-docs", "https://docs.jcohy.com/docs/" +
                        projectVersion.getName() + "/" + projectVersion.getVersion() + "/html5/zh-cn");
            } else {
                map.put(projectVersion.getName() + "-docs", projectVersion.getUrl());
            }
        }

        return map;
    }
}

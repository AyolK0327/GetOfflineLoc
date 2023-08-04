plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

group = "yumcraft.getofflineloc"
version = "1.0.3-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://repo.papermc.io/repository/maven-public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly ("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")
    compileOnly("redis.clients:jedis:2.9.0")
    compileOnly("org.json:json:20230227")
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("org.slf4j:slf4j-api:1.7.30")
    compileOnly("org.slf4j:slf4j-log4j12:1.7.30")
    compileOnly("com.alibaba:fastjson:1.2.67_noneautotype2")
    compileOnly(fileTree("lib"))
}

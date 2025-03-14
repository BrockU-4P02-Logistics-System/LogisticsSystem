plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.12.4544")
    implementation(group = "com.graphhopper", name = "graphhopper-core", version = "7.0")
    implementation(group = "com.graphhopper", name = "graphhopper-reader-osm", version = "3.0-pre3")
    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.32")
    implementation("org.json:json:20210307")
    implementation("org.knowm.xchart:xchart:3.8.0")

}

tasks.named<JavaExec>("run") {
    mainClass.set("org.example.RoutingExample")
}
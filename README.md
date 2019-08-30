[![Build Status](https://travis-ci.org/ida-mediafoundry/jetpack-carve.svg?branch=master)](https://travis-ci.org/ida-mediafoundry/jetpack-carve) [![codecov](https://codecov.io/gh/ida-mediafoundry/jetpack-carve/branch/master/graph/badge.svg)](https://codecov.io/gh/ida-mediafoundry/jetpack-carve)

# Jetpack Carve - AEM Object Relational Mapper
(powered by iDA Mediafoundry)

This is a project that contains a Sling Model Object Relational Mapper named **Carve** that aims 
to simplify writing data to the repository with Sling Models. Jetpack Carve aims to provide an ORM like experience 
like Hibernate offers for SQL based systems, but then for AEM.

## Usage

* Add Carve as a dependency in your parent pom. Its recommeded to define the version in a property (carve.version):

```xml
<dependency>
    <groupId>be.ida-mediafoundry.jetpack</groupId>
    <artifactId>carve.ui.apps</artifactId>
    <version>${carve.version}</version>
    <type>content-package</type>
</dependency>
<dependency>
    <groupId>be.ida-mediafoundry.jetpack</groupId>
    <artifactId>carve.core</artifactId>
    <version>${carve.version}</version>
</dependency>
```

In your content module (apps module) add the following subpackage to be included by the content package plugin:

```xml
<plugin>
    <groupId>com.day.jcr.vault</groupId>
    <artifactId>content-package-maven-plugin</artifactId>
    ...
    <configuration>
        <subPackages>
            <subPackage>
                <groupId>be.ida-mediafoundry.jetpack</groupId>
                <artifactId>carve.ui.apps</artifactId>
                <filter>true</filter>
            </subPackage>
        </subPackages>
    </configuration>
</plugin>
```

In your OSGI bundle (core module) add this dependency:

```xml
<dependency>
    <groupId>be.ida-mediafoundry.jetpack</groupId>
    <artifactId>carve.core</artifactId>
    <scope>provided</scope>
</dependency>
```

* Add **@CarveModel** and **@CarveId** annotations on the sling models that need to be persisted:

```java
@CarveModel(pathPolicyProvider = SimplePathPolicyProvider.class, location = "/content/data")
@Model(adaptables = Resource.class)
public class MySlingModel {

    @CarveId
    @Inject
    private String id;
   
```

* Use the modelmanager to perist and/or load the data:

```java
modelManager.persist(model);
modelManager.retrieve(MySlingModel.class, "123");
```

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, Sling Models and WCMCommand.
* ui.apps: contains the /apps part containing the html, js, css and .content.xml files.

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish

Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Testing

There are three levels of testing contained in the project:
Unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

    mvn clean test

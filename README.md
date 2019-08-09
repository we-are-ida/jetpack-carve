[![Build Status](https://travis-ci.org/ida-mediafoundry/jetpack-carve.svg?branch=master)](https://travis-ci.org/ida-mediafoundry/jetpack-carve) [![codecov](https://codecov.io/gh/ida-mediafoundry/jetpack-carve/branch/master/graph/badge.svg)](https://codecov.io/gh/ida-mediafoundry/jetpack-carve)

# Jetpack Carve - Object Relational Mapper
(powered by iDA Mediafoundry)

This is a project that contains a Sling Model Object Relational Mapper named **Carve** that aims 
to simplify writing to data with Sling Models. Jetpack Carve aims to provide an ORM like experience 
like Hibernate offers for SQL based systems.

## Usage

Add dependency in your pom.xml to Jetpack carve.

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

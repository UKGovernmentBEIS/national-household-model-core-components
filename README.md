# Synopsis
The repository holds the repositories required to build the core components for the National Household Model (NHM), the components include projects to define the properties and actions a Survey Case has within the model, projects to compile and execute scenarios as a simulation, projects that define the actions and measures than can can be applied within a simulation and code to import a housing stock to run simulations on.

Full documentation on what the NHM is and how it can be used can be found here: [https://github.com/cse-bristol/national-household-model-documentation/releases/tag/Current] (Documentation Repository)

If you would like to run a compiled version of the National Household Model, the latest application can be found here [https://github.com/cse-bristol/national-household-model-standalone/releases/tag/Current] (Standalone releases)

The project has been written using Java 1.7.

# Installation
The project and sub-projects here can be built using Gradle, there is a single gradle build file for all projects. To install the projects into your local Maven respository use the command './gradlew install' (linux) 'gradle.bat install' (win).

# External Project Dependencies
1. Requires [https://github.com/cse-bristol/sedbuk-boiler-matching] (Sebuk boiler matcher) as subproject within this build, this is configured a a git submodule at present, ensure that the commit you have checked out for this project does not include any Gradle build files.

# License
[Open Government License] (http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/)



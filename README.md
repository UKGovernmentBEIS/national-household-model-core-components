# Synopsis
The repository holds the repositories required to build the core components for the National Household Model (NHM), the components include projects to define the properties and actions a Survey Case has within the model, projects to compile and execute scenarios as a simulation, projects that define the actions and measures than can can be applied within a simulation and code to import a housing stock to run simulations on.

Full documentation on what the NHM is and how it can be used can be found here: [Documentation Repository](https://github.com/cse-bristol/national-household-model-documentation/releases/tag/Current).

If you would like to run a compiled version of the National Household Model, the latest application can be found here [Standalone releases](https://github.com/cse-bristol/national-household-model-standalone/releases/tag/Current)

The project has been written using Java 1.7, will shortly be java 1.8. Our advice is build using java 8 as p2 drone requires it anyway.

# Installation
This is a bit basic, we will fill in later, the project can be built end-to-end on a linux machine.

## Pre-requisits
- Linux os - tested on Ubuntu 15+ and Debian?
- Java 7, but will also work on 8
- Maven 3

## Build process
There is a shell script named build, this wil build the core projects, then start a p2 drone server, build some p2 bundles and push them to drone server, build the ide-project using the drone server to fetch the osgi bundles, then stop the drone server.

- Make sure you add the following to your maven settigns file (/usr/share/maven/conf/settings.xml). 

```xml
<servers>
    <server>
      <id>package-drone</id>
      <username>deploy</username>
      <password>da9fc69a5d93f07432ace3fc7015fffa4d4b032a88d267f88cb2dd5369d1b6af</password>
    </server>
  </servers>
```

This gives the documentation the credentials required to push the p2 server.

- [ ] At the moment the build script always pushes a version of the nhm-api-bundler at version 2, this causes issues when repeatedly building, we need to remove this from the repeated build script and only run it when the api changes...
- The p2 drone server admin user-name and password are buildprocess@nhm.org.uk and password


### Things stil todo
- [ ] More detail needed on the above including why etc...  currnently lives in other README files so maybe just linking to those is ok.
- [ ] Re-create past history of nhm bundle builds, by putting old p2 bundles into repo, tagging each one as a specific version, you could then just build the ide at that point?


# License
[Open Government License] (http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/)

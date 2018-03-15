# Synopsis
The repository holds the repositories required to build the core components for the National Household Model (NHM), the components include projects to define the properties and actions a Survey Case has within the model, projects to compile and execute scenarios as a simulation, projects that define the actions and measures than can can be applied within a simulation and code to import a housing stock to run simulations on.

Full documentation on what the NHM is and how it can be used can be found here: [Documentation Repository](https://github.com/cse-bristol/national-household-model-documentation/releases/tag/Current).

If you would like to run a compiled version of the National Household Model, the latest application can be found here [Standalone releases](https://github.com/cse-bristol/national-household-model-standalone/releases/tag/Current)

The project has been written using Java 1.7, will shortly be java 1.8.

# Installation
This is a bit basic, we will fill in later, the project can be built end-to-end on a linux machine.

## Dependencies
- A JDK for Java 7 or above
- The bash shell
- Maven
- Gradle
- xmlstarlet
- webfsd
- R and packages

You can assemble all the dependencies you need using nixpkgs.
Install this, and run the build inside a shell produced with nix-shell user-environment.nix.

## Building

A complete build can be run using the script build.sh in the top-level directory.
Invoke ./build --help for more on how to use it.

The script uses other build tools to run various build stages. A
summary follows but the script itself should be good documentation, so
read that if you want to know what's going on.

## Building the core model

This is all in core-projects and is built using gradle. This is
sufficient to produce a working copy of the model usable on the
command line

## Generating the documentation

This is in nhm-documentation, and depends on the core model being
built first as this generates some of the documentation text. It's
built with maven, using a docbook plugin.

## Building the IDE

This is in nhm-ide; it has a couple of steps:

1. Collect up all the model versions into the p2/inputs folder.
   Any newly built jars are taken from the binaries/ folder and put in here.
2. Use the maven tycho p2 extras plugin to assemble a p2 repository for these jars.
3. Run webfsd to serve the p2 repository locally via http on port 8000
4. Use maven tycho to compile the NHM application 'product'

## Running the system tests

These are in system-tests; they involve running a bunch of scenarios
against the model CLI and then verifying the results with some simple R scripts.

## Packaging a result for distribution

The build script can locate all the things you need to run the
application, including copies of the model for use on a back-end
server if you have one.

## Doing a release

We try and derive version numbers from git where possible, but this is
not easy for the maven parts of the project. The release script should
help you bump all the version numbers and end up with something usable.

It is run through build.sh so don't run it on its own.

# License
[Open Government License] (http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/)

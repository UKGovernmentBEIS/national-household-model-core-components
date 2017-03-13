nhm-health
==================
This module is designed to calculate the health (QALYs) and cost impacts of applying retrofits to English homes. It will form part of the National Household Model (NHM) as an interface but will still be able to run as a stand alone application.

The code produces 4 csv files in the build directory: exposures.csv, qalys.cav, morbqalys.csv and costs.csv

Dependencies
==================
gradle 2.3

Get the code
==================
git clone https://psymo87@bitbucket.org/cse-bristol/nhm-health.git

Build and run the code
==================
cd nhm-health
./gradlew execute

You will need to change build.gradle is you want to change any of the input/output arguments


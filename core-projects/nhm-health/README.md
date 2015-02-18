nhm-health
==================
This module is designed to calculate the health (QALYs) and cost impacts of applying retrofits to English homes. It will eventually be part of the National Household Modle (NHM) as an interface but should still be able to run as a stand alone application.

Dependencies
==================
apache-maven

Get the code
==================
git clone https://github.com/cse-bristol/nhm-health

Build and run the code
==================
cd nhm-health

mvn clean install

mvn exec:java -Dexec.mainClass="uk.ac.ucl.hideem.Main" -Dexec.args="people.csv houses.csv"

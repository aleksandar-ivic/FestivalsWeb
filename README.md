FestivalsWeb
============

1.About the project
===================

The idea of this project is to create an application for etracting metadata about music festivals in Europe. The metadata is extracted from the website [last.fm](http://www.last.fm/), where you can find basic information about a festival and it's location, date, time, artists and genres they play. Metadata is inserted in site's webpages in a structured format using Microdata standard. After the metadata is extracted, it is transformed to RDF format and stored into RDF repository. Access to the extracted data is enabled through RESTful services.

Application workflow consists of the following phases

1. XML parser get's festival IDs from Audio Scrobbler's web service.
2. For each festival ID last.fm API get's data about festival, it's location, interval and lineup.
3. That data is transformed into RDF triplets based on DC, MO, FOAF, GEO, TL, EVENT and TIME vocabularies.
4. Data is persisted into an RDF repository
5. Access to the data is enabled through RESTful services


2. Domain model
===============
![domain_model](

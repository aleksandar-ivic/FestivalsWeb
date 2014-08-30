Festivals
=========

1. About the project
===================

The main idea of this application is to get data about music festivals in Europe from [last.fm](http://www.last.fm/) and then show that data on map. The data is collected using [last.fm API](http://www.last.fm/api) to find festivals by its ID, which are provided by web service [AudioScrobbler WebService](http://www.audioscrobbler.net/). After the data is collected, it is transformed to RDF format and stored into RDF repository. Access to the collected data is enabled through RESTful services.

Application workflow has 5 phases:

1. XML parser get's festival IDs from web service [AudioScrobbler WebService](http://www.audioscrobbler.net/).
2. For each festival ID [last.fm API](http://www.last.fm/api) get's data about festival, it's location, interval and lineup.
3. That data is transformed into RDF triplets based on [Dublin Core](http://purl.org/dc/elements/1.1/), [Music Ontology](http://purl.org/ontology/mo/), [Friend of a Friend](http://xmlns.com/foaf/0.1/), [Basic Geo](http://www.w3.org/2003/01/geo/wgs84_pos#), [Timeline](http://purl.org/NET/c4dm/timeline.owl#), [EVENT](http://purl.org/NET/c4dm/event.owl#) and [TIME](http://www.w3.org/2006/time#) vocabularies.
4. Data is persisted into an RDF repository
5. Access to the data is enabled through RESTful services


2. Domain model
===============
Webpages of festivals from the [last.fm](http://www.last.fm/) website are analyzed in order to determine which classes and properties form the [Dublin Core](http://purl.org/dc/elements/1.1/), [Music Ontology](http://purl.org/ontology/mo/), [Friend of a Friend](http://xmlns.com/foaf/0.1/), [Basic Geo](http://www.w3.org/2003/01/geo/wgs84_pos#), [Timeline](http://purl.org/NET/c4dm/timeline.owl#), [EVENT](http://purl.org/NET/c4dm/event.owl#) and [TIME](http://www.w3.org/2006/time#) vocabularies are supported. Based on that information, domain model is created (Picture 1).

![domain](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/domain_model.png)

Picture 1 - Domain model

Class Festival contains basic information about festival such as festival name and it has references to its lineup (class MusicArtist), its location (class Location), its genres (class Genre) and the date of festival (class Interval).

Class MusicArtist contains basic information of a artist, such as artist's name and it has reference to its subjects(genres) (Class Genre).

Class Interval contains start and end date of the festival.

Class Location contains location name, latitude and longitude of a location.

Class Genre contains name of genre.

3. The solution
===============

First of all, a few words about [last.fm](http://www.last.fm/). [Last.fm](http://www.last.fm/) is a music recommendation service. You use [Last.fm](http://www.last.fm/) by [signing up](https://secure.last.fm/join) and downloading [The Scrobbler](http://www.last.fm/download), which helps you discover more music based on the songs you play. [Last.fm](http://www.last.fm/) has its own [API](http://www.last.fm/api) for collecting data.

This application collects data about festivals from the [last.fm](http://www.last.fm/). The data is collected by the [last.fm API](http://www.last.fm/api). But, before using services of [last.fm API](http://www.last.fm/api), XML Parser collects id of the festivals in Europe from [AudioScrobbler WebService](http://www.audioscrobbler.net/). Than those IDs are passed as parameters to [last.fm API](http://www.last.fm/api), which returns a festival. Data about festival is used to create domain objects of the application that are persisted into an RDF repository The application allows access to that data via RESTful services.

The applications contains two REST services.

**GET /api/festivals** - returns data about festival. Service's parameter is:
 - genre - specifies the genre of returned festivals
 - date - specifies the start date of festival
  
An example of this service call:  

  *GET/ festivals?genre=rock&date=*  
  
  *GET/ festivals?genre=&date=08/30/2014*

**GET /api/genres** - returns data about all genres. It has no parameters.

An example of this service call:
  *GET/genres*

4. Technical realisation
========================

This application is written in programming language Java.

This application uses [last.fm API](http://www.last.fm/api) for collecting all relevant data about festivals based on festival ID from [AudioScrobbler WebService](http://www.audioscrobbler.net/).

Application uses [Jenabean](https://code.google.com/p/jenabean/) library for mapping Java objects into RDF triplets using annotations. Jenabean provides explicit binding between an object property and a particular RDF property.

[Jena TDB](http://jena.apache.org/documentation/tdb/) library is used for data storage in the RDF repository. TDB is a component of Jena for RDF storage and query. It support the full range of Jena APIs.

Implementation of the RESTful web service is supported by [Jersey](https://jersey.java.net/) framework. Jersey is the open source JAX-RS Reference Implementation for building RESTful Web services. It uses annotations which define type of the HTTP requests (GET, POST ...) and also the path to the requested resource.


5. Acknowledgements
===================

This application has been developed as a part of the project assignment for the subject [Intelligent Systems](http://is.fon.rs/) at the Faculty of Organization Sciences, University of Belgrade, Serbia.

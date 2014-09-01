Festivals
=========

1. About the project
===================

The main idea of this application is to retrieve data about music festivals in Europe from [last.fm](http://www.last.fm/) and display that data on a map. The data is collected using [last.fm API](http://www.last.fm/api) to find festivals by its ID, which are provided by web service [AudioScrobbler](http://www.audioscrobbler.net/). After the data is collected, it is transformed to RDF format and stored into RDF repository. Access to the collected data is enabled through RESTful services.

Application workflow has 5 phases:

1. XML parser get's festival IDs from web service [AudioScrobbler](http://www.audioscrobbler.net/).
2. Details about each festival is retrieved via [last.fm API](http://www.last.fm/api), including it's title, location, date interval and lineup.
3. That data is further transformed into RDF triplets based on the following vocabularies: [Dublin Core](http://purl.org/dc/elements/1.1/), [Music Ontology](http://purl.org/ontology/mo/), [Friend of a Friend - FOAF](http://xmlns.com/foaf/0.1/), [ Geo](http://www.w3.org/2003/01/geo/wgs84_pos#), [Timeline Ontology](http://purl.org/NET/c4dm/timeline.owl#), [Event Ontology](http://purl.org/NET/c4dm/event.owl#) and [Time Ontology](http://www.w3.org/2006/time#).
4. Data is persisted into an RDF repository
5. Access to the data is enabled through RESTful services


2. Domain model
===============
Data about festivals from the [last.fm](http://www.last.fm/) website is analyzed in order to determine which classes and properties from the [Dublin Core](http://purl.org/dc/elements/1.1/), [Music Ontology](http://purl.org/ontology/mo/), [FOAF](http://xmlns.com/foaf/0.1/), [Geo](http://www.w3.org/2003/01/geo/wgs84_pos#), [Timeline Ontology](http://purl.org/NET/c4dm/timeline.owl#), [Event Ontology](http://purl.org/NET/c4dm/event.owl#) and [Time Ontology](http://www.w3.org/2006/time#) vocabularies are supported. Based on that information, domain model is created (Picture 1).

![domain](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/domain_model.png)

Picture 1 - Domain model

Class Festival contains basic information about festival such as festival name, location (class Location), genres (class Genre), the date of festival (class Interval) and it references its lineup (class MusicArtist).

Class MusicArtist contains basic information of an artist, such as artist's name and it  references its genres (Class Genre).

Class Interval contains start and end date of a festival.

Class Location contains name, latitude and longitude of a location.

Class Genre contains name of a genre.

3. The solution
===============

[Last.fm](http://www.last.fm/) is a music recommendation service. In order to use it, it is necessary to [sign up](https://secure.last.fm/join) for an account and to download [The Scrobbler](http://www.last.fm/download) which helps user to discover more music based on the songs they play. Last.fm has its own [API](http://www.last.fm/api) for accessing it's data. Only registered last.fm user can use services of the API.

This application retrieves data about music festivals in Europe from the last.fm via its API. First, festival IDs are fetched from the AudioScrobbler WebService. Than those IDs are passed to last.fm API, which returns data about those festivals. This data is further transformed into RDF triplets based on [Dublin Core](http://purl.org/dc/elements/1.1/), [Music Ontology](http://purl.org/ontology/mo/), [FOAF](http://xmlns.com/foaf/0.1/), [Geo](http://www.w3.org/2003/01/geo/wgs84_pos#), [Timeline Ontology](http://purl.org/NET/c4dm/timeline.owl#), [Event Ontology](http://purl.org/NET/c4dm/event.owl#) and [Time Ontology](http://www.w3.org/2006/time#) vocabularies, that are persisted into an RDF repository. 

The application allows access to that data through RESTful services:

**GET /api/festivals** - returns data about a festival. Parameters supported are:
 - genre - specifies a genre of festivals
 - date - specifies a start date of festivals
  
An example call of this service looks like following:  

  *GET/ festivals?genre=rock&date=date=08/30/2014*  
    
    ![festivals json](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/festsJson.png)  
    
    Picture 2. Example of JSON response for getting festival.

**GET /api/genres** - returns data about all genres. It has no parameters.  

An example of this service call:

  *GET/genres*    
    
    ![genres json](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/genresJson.png)  
    
    Picture 3. Example of JSON respones for getting all genres.
  
GET request to one of these two service triggers the [SPARQL](http://www.w3.org/TR/rdf-sparql-query/) query and the application returns JSON with query results. This services are used to show data about festivals and mark those festivals on the map.

4. Technical realisation
========================

This application is written in Java programming language.

This application uses [last.fm API](http://www.last.fm/api) for collecting all relevant data about festivals based on festival ID from [AudioScrobbler WebService](http://www.audioscrobbler.net/).

Application uses [Jenabean](https://code.google.com/p/jenabean/) library for mapping Java objects into RDF triplets using annotations. Jenabean provides explicit binding between an object property and a particular RDF property.

[Jena TDB](http://jena.apache.org/documentation/tdb/) library is used for data storage in the RDF repository. TDB is a component of Jena for RDF storage and query. It support the full range of Jena APIs.

Implementation of the RESTful web service is supported by [Jersey](https://jersey.java.net/) framework. Jersey is the open source JAX-RS Reference Implementation for building RESTful Web services. It uses annotations which define type of the HTTP requests (GET, POST ...) and also the path to the requested resource.

5. Example of use
=================
  
To use this application one has to do the following steps:

**Step 1**: To collect data about festivals, run project as Java console application. (Picture 5.)  
![step1](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/step1.png)  
Picture 3. Run project as Java console application.

**Step 2**: Go to *<yourServerUrl>/index.html* and click "Let's rock!" (Picture 4.)
![step2](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/step2.png)  
Picture 4. Let's rock!
  
**Step 3**: Choose genre or choose start date of the festival to mark festivals on the map (Picture 5.)
![step3](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/step3.png)  
Picture 5. Choose genre or date to mark festivals on the map.


**Step 4**: When you have found the festival you like, click on the marker to get information about that festival (Picture 6. and Picture 7.)
![step4](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/step4.png)  
Picture 6. Click on the marker to get information about the chosen festival.  

![step5](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/step5.png)
Picture 7. Example of information about one festival.

6. Acknowledgements
===================

This application has been developed as a part of the project assignment for the subject [Intelligent Systems](http://is.fon.rs/) at the Faculty of Organization Sciences, University of Belgrade, Serbia.

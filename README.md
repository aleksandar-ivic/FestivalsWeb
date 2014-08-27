FestivalsWeb
============

1. About the project
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
Webpages of festivals from the [last.fm](http://www.last.fm/) website are analyzed in order to determine which classes and properties form the DC, MO, FOAF, GEO, TL, EVENT and TIME vocabularies are supported. Based on that analysis, domain model is created and it is depicted in Picture 1.

![domain](https://github.com/TheCoa/FestivalsWeb/blob/master/docs/image/domain_model.png)

Picture 1 - Domain model

Class Festival contains basic information about festival such as festival name and it has references to its lineup (class MusicArtist), its location (class Location), its genres (class Genre) and the date of festival (class Interval).

Class MusicArtist contains basic information of a artist, such as artist's name and it has reference to its subjects(genres) (Class Genre).

Class Interval contains start and end date of the festival.

Class Location contains location name, latitude and longitude of a location.

Class Genre contains name of genre.

3. The solution
===============

This application collects metadata about movies from the webpage [last.fm](http://www.last.fm/). The data is extracted by the last.fm API and is used to create domain objects of the application that are persisted into an RDF repository. The application allows access to that data via RESTful services.

The applications contains two REST services.

**GET /api/festivals** - returns data about festival. Service's parameter is:
 - genre - specifies the genre of returned festivals
</br>An example of this service call:
  *GET/ festivals?genre=rock*

**GET /api/genres** - returns data about all genres. It has no parameters.

An example of this service call:
  *GET/genres*

# Joojle Search Engine

## Introduction

The Joojle Search Engine is a project that aims to build a search engine for a specific website. The search engine will
accept search queries and provide a list of links to pages containing the search terms. The system consists of three
main components: the crawling and link discovery subsystem, the search engine for executing queries, and the ranking
subsystem to assign importance to pages.

![umlPic.jpg](doc%2FumlPic.jpg)

## High-Level Requirements

The system has the following high-level requirements:

- Crawling and link discovery subsystem: This component is responsible for crawling a website and discovering links
  between pages.
- Search engine: The search engine executes search queries and returns a list of relevant pages.
- Ranking subsystem: This component assigns importance to pages based on certain criteria.

## Crawling

The crawling process involves systematically visiting pages on the website and extracting links for further exploration.
The main features of the crawling subsystem are as follows:

- The system starts the crawling process from a specified start URL in the configuration file.
- The crawling can be performed in either a breadth-first or depth-first manner, depending on the mode set in the
  configuration.
- A maximum depth can be set to limit the depth of crawling. If a maximum depth is defined, the crawling will not go
  beyond that level.
- The system can also be configured with a maximum number of pages to be visited.
- The crawling process will ensure that each link is visited only once to avoid redundancy.
- The system exposes a search RESTful API that allows users to execute search queries and obtain statistics about the
  queries.

## Search Engine

The search engine component processes search queries and returns a list of relevant pages. The main features of the
search engine are as follows:

- The search engine is accessible through a RESTful API.
- Search queries are case-insensitive.
- Searching for one positive term will return a list of pages containing that term.
- Searching for multiple positive terms will return a list of pages containing all of the terms.
- Search queries can include negative terms, which exclude pages containing those terms.
- Queries with only negative terms are not allowed.

## Ranking Subsystem

The ranking subsystem assigns importance to pages based on specific criteria. The main criteria for ranking pages are as
follows:

- Page importance is determined by the number of times the search terms appear on the page.
- The search results are sorted in descending order of importance.

## Implementation Hints

The recommended implementation approach for the Joojle Search Engine project is as follows:

- Use the Maven reactor model for managing the project dependencies.
- Implement the project using the Spring Boot framework.
- Utilize any necessary external dependencies for fetching, parsing, and processing HTML pages and links.
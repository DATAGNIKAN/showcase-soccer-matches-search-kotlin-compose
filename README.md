<h1>Search Soccer Matches, powered by Typesense & Kotlin</h1>

This search experience is powered by [Typesense](https://typesense.org) which is a blazing-fast, [open source](https://github.com/typesense/typesense) typo-tolerant search-engine. It is an open source alternative to Algolia and an easier-to-use alternative to ElasticSearch.

## Getting started

### Setting Up a the Typesense server

```
cd typesense
docker compose up
```

### Setting Up a Project

- Update the Typesense client [here](/app/src/main/java/com/example/showcase_soccer_matches_search_kotlin_compose/utils/typesense.kt#L17) with your IPV4
- Then run the app
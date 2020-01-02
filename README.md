# embulk-input-firestore

[![Gem Version](https://badge.fury.io/rb/embulk-input-firestore.svg)](https://badge.fury.io/rb/embulk-input-firestore)

[Google Cloud Firestore](https://firebase.google.com/docs/firestore) input plugin for Embulk. 

You can use filter plugins such as [embulk-filter-expand_json](https://github.com/civitaspo/embulk-filter-expand_json) or [embulk-filter-add_time](https://github.com/treasure-data/embulk-filter-add_time) to convert the json column to typed columns. [Rename filter](http://www.embulk.org/docs/built-in.html#rename-filter-plugin) is also useful to rename the typed columns.

## Overview

* **Plugin type**: input
* **Guess supported**: no

## Configuration

- **project_id**: GCP project_id (string, required)
- **json_keyfile**: path to GCP credential json file (string, required)
- **sql**: A query string to fetch documents from Firestore (string, required)
- **json_column_name**: column name used in outputs (string, optional, default: "record")

### NOTE

- **sql** parameter accepts only limited syntax; see also https://github.com/syucream/FireSQL

## Example

```yaml 
in:
  type: firestore
  project_id: "your-project-id"
  json_keyfile: path/to/credential.json
  sql: "SELECT id, name, created_at FROM users"

out:
  type: stdout
```

## Development

## Build

You need to build https://github.com/syucream/FireSQL your own.

```shell script
$ ./gradlew gem
```

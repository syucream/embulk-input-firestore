# Datastore input plugin for Embulk

[![Gem Version](https://badge.fury.io/rb/embulk-input-datastore.svg)](https://badge.fury.io/rb/embulk-input-datastore)

A embulk input plugin fetches Cloud Datastore entities.

## Overview

* **Plugin type**: input
* **Resume supported**: no
* **Cleanup supported**: no
* **Guess supported**: no

## Configuration

- **project_id**: your GCP project_id. (string, required)
- **json_keyfile**: A path to JSON keyfile. (string, required)
- **json_column_name**: description (string, default: `"record"`)

## Example

```yaml
in:
  type: datastore
  project_id: "your-gcppj-123"
  json_keyfile: credential.json
  gql: "SELECT * FROM myKind"
```


## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```

## NOTE

- Currently this plugin has below limitations:
  - Aggregate fetched properties to 1 'json' type column.


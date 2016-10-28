# SubreadSet (aka *.subreadset.xml) Metrics

Exports all the commonly used SubreadSet metrics from the PacBio
supported pipeline. It can be a slow and tedious to sort out where 
metrics exist and how they were calculated. This project aims to solve
that with docs, tests and exports to JSON and CSV. Use the data!

Key goals of this project.

- Export data from SubreadSet (*.subreadset.xml) files to convenient to use CSV or JSON
- Support all known versions. Don't assume the latest.
- Scala API for quick access to typed data or raw values from the input files
- Documentation
  - Keep a version history for sts.xml schema changes
  - Explain what each metric means in [easy-to-use documentation](#metrics)

See the overall PacBio Metrics project if you want to aggregate metrics
from more than just SubreadSet files.

## Usage

Export metrics by passing one or more files and directories that contain sts.xml files. First build a JAR.

```
# Option 1: Build a JAR for standalone use (example below)
sbt run
```

Exporting aligned movies is the most common use case. See [ITG-190](https://jira.pacificbiosciences.com/browse/ITG-190) for an example.

```
# Export all the data to a CSV named ITG-190.csv
sbt "run m54088_160923_213709.subreadset.xml m54088_160923_213709.csv"

# Another option is to export to a JSON
sbt "run m54088_160923_213709.subreadset.xml m54088_160923_213709.json"
```

## Metrics

The full list of metrics is listed on [itg/metrics/docs.html under the namespace SS](http://itg/metrics/docs.html?q=SS),
which is derived from the [Scala code in this project](src/main/scala/com/pacb/itg/metrics/subreadset/SubreadSet_3_0_1.scala).
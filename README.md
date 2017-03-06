# SubreadSet (aka *.subreadset.xml) Metrics

Exports all the commonly used values from PacBio `subreadset.xml` files. This project also tracks use cases and versions
of the file.

Contact jfalkner@pacb.com for more information or with any questions.

## Usage

Use the `scripts/run_subreadset.sh` helper script exists to convert a single `subreadset.xml` file to JSON.

```
# Example use of helper script
./scripts/run_subreadset.sh m54088_160923_213709.subreadset.xml m54088_160923_213709.json
```

## Development

This is a Scala/SBT project. You'll need Java 1.8+, Scala and SBT installed to do development. The tests are a great way
to see how things are supposed to work. You can also run the test suite from command-lind with the following.
as follows:

```bash
sbt clean coverage test coverageReport
...
[info] All done. Coverage was [100.00%]
```

Most of the on-going maintenance is in making sure that updates to the `subreadset.xml` format are correctly versioned
and supported by the code. A changelog of the XML updates is not currently produced. Usually, you'll notice that the
"Version" attribute in the XML's root element changes and it may or may not correspond to changes in the document.

Another complication is that several formal and informal strategies to store data are not well-captured anywhere. For 
example, different `AutomationParameter` or `ReagentTube` entries may be used depending on the group or when the file
was made. An informal, meta-data example is when different groups stash and use info in the name of a sample.

The strategy used is to put a copy of each different version of `subreadset.xml` formatting in ITG's test-data directory
`/pbi/dept/itg/test-data/subreadset`. The logic in `SubreadSet.apply` captures how to detect and correctly pick the 
version to parse and related logic lives in dedicated `SubreadSet_v...` classes. Unit tests are then built to confirm
each known version and use case works.

## Metrics

The full list of metrics is listed on [itg/metrics/docs.html under the namespace SS](http://itg/metrics/docs.html?q=SS).
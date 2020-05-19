# HTTP Ontology

This is a description in OWL2 of the HTTP interactions.

This is inspired by the [HTTP RDF
vocabulary](https://www.w3.org/TR/HTTP-in-RDF10/).

## Prerequisite

The documentation generation and ontology checking requires The
[Leiningen](https://leiningen.org/) build tool.

## Ontology profiling

Verification of the ontology consistency can be done with

```shell
$ lein run -- check
```

## Documentation

Documentation can be generated using:

```shell
$ lein run -- gendoc
```

You can then see the result by running a local Web server with:

```shell
$ lein serve
```

<!--
Local Variables:
mode: markdown
coding: utf-8
ispell-local-dictionary: "english"
End:
-->

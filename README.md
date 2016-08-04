# Jasmin

A Spoofax editor for Jasmin, an assembler for the Java Virtual Machine.

# Building

Jasmin is built using Maven. First follow the guides on [setting up Maven](https://github.com/metaborg/doc/blob/master/releng/setting-up-maven.md), and [using MetaBorg Maven artifacts](https://github.com/metaborg/doc/blob/master/releng/artifacts.md).

To build Jasmin, including an Eclipse update site, run `mvn verify`.
The update site will be located at `jasmin.eclipse.updatesite/target/site`.

# Documentation

Some parts of this project have high-level documentation:

* [Syntax definition](jasmin/syntax/README.md)
* [Name analysis](jasmin/trans/analysis/names/README.md)
* [Type analysis](jasmin/trans/analysis/types/README.md)
* [Code generation](jasmin/trans/translator/README.md)

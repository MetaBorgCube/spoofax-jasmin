# JasminXT

Eclipse editor for JasminXT, an assembler for the Java Virtual Machine. 

# Building

JasminXT is built using Maven. First follow the guides on [setting up Maven](https://github.com/metaborg/doc/blob/master/releng/setting-up-maven.md), and [using MetaBorg Maven artifacts](https://github.com/metaborg/doc/blob/master/releng/artifacts.md).

To build JasminXT, including an Eclipse update site, run `mvn clean verify`. The update site will be located at `org.spoofax.lang.jasmin.updatesite/target/site`.

# Documentation

Some parts of this project have high-level documentation:
* [Syntax definition](org.spoofax.lang.jasmin/syntax/README.md)
* [Name analysis](org.spoofax.lang.jasmin/trans/analysis/names/README.md)
* [Type analysis](org.spoofax.lang.jasmin/trans/analysis/types/README.md)
* [Code generation](org.spoofax.lang.jasmin/trans/translator/README.md)

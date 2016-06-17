# libisabelle

 A Scala library which talks to Isabelle

| Service                   | Status |
| ------------------------- | ------ |
| Travis (Linux/Mac CI)     | [![Build Status](https://travis-ci.org/larsrh/libisabelle.svg?branch=master)](https://travis-ci.org/larsrh/libisabelle) |
| AppVeyor (Windows CI)     | [![Build status](https://ci.appveyor.com/api/projects/status/uuafgv21ragvoqei/branch/master?svg=true)](https://ci.appveyor.com/project/larsrh/libisabelle/branch/master) |
| Maven Central             | [![Maven Central](https://img.shields.io/maven-central/v/info.hupel/libisabelle_2.11.svg?label=latest%20release%20for%202.11)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22info.hupel%22%20AND%20a%3A%22libisabelle_2.11%22) |
| Scaladoc                  | [![Scaladoc](http://javadoc-badge.appspot.com/info.hupel/libisabelle-docs_2.11.svg?label=scaladoc)](http://javadoc-badge.appspot.com/info.hupel/libisabelle-docs_2.11) |
| Zenodo (DOI)              | [![DOI](https://zenodo.org/badge/19880/larsrh/libisabelle.svg)](https://zenodo.org/badge/latestdoi/19880/larsrh/libisabelle) |
| Gitter (Chat)             | [![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/larsrh/libisabelle) |


## Setup

`libisabelle` is a Scala library which talks to Isabelle.
It currently works with Isabelle2015 and Isabelle2016.

To get started, follow these steps:

1. Make sure you have checked out and/or updated all submodules.
   This can be done easily by running `git submodule update --init --recursive`.
2. Run the `sbt` script to fetch all required Scala dependencies.
   After this is done, you are in the SBT shell.
3. Compile the sources with `compile`.
4. If you have used an arbitrary snapshot of the sources (e.g. via `git clone`), run `publishLocal`.
5. Bootstrap an Isabelle installation using `cli/run --version 2016 build`, which will download and extract the latest supported Isabelle version for you.

On some systems, you might need to install Perl, Python, and/or some additional libraries.

Note to proficient Isabelle users:
`libisabelle` does not respect `ISABELLE_HOME`.
Bootstrapping will create a new installation in your home folder (Linux: `~/.local/share`, Windows: `%LOCALAPPDATA%`, OS X: `~/Library/Preferences`).


## Operating system support

`libisabelle` works on all platforms supported by Isabelle (Mac OS X, Linux, Windows).
It can either use an existing installation or bootstrap one (by downloading the appropriate archive from the Isabelle website).
Bootstrapping is automatically tested on all three platforms.

### Note on Windows support

AppVeyor builds `libisabelle` on Windows, but only bootstraps an Isabelle installation.
It does not run the integration tests (for now).


## Running the tests

Run the `sbt` script again, then, in the SBT shell, type `test`.
This requires the environment variable `ISABELLE_VERSION` to be set.
Another option is to pass the version to the test framework directly.

Example:

```
$ cd libisabelle
$ ./sbt
...
> testOnly * -- isabelle.version 2016
```

Make sure to have bootstrapped the installation as described above for the appropriate Isabelle version, otherwise the tests will fail.


## Command line interface

The `cli` application is able to launch an Isabelle/jEdit instance with a specified logic.

```
$ cd libisabelle
$ ./sbt
...
> cli/run --version 2016 --session HOL-Probability jedit
```


## Including libisabelle into your project

`libisabelle` is cross-built for Scala 2.10.x and 2.11.x.
Drop the following lines into your `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "info.hupel" %% "libisabelle" % "0.3.4",
  "info.hupel" %% "libisabelle-setup" % "0.3.4"
)
```


## Participation

This project supports the [Typelevel][typelevel] [code of conduct][codeofconduct] and wants all of its channels (at the moment: GitHub and Gitter) to be welcoming environments for everyone.

[typelevel]: http://typelevel.org/
[codeofconduct]: http://typelevel.org/conduct.html

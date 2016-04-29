# Using Maven to build OpenCV

This page describes the how to build OpenCV using Maven. Specifically the Maven build is aimed at creating Java OSGi-compatible libraries and native support however there is the possibility it can be extended to support other build artifacts. The main purpose of creating a Maven build was to allow the build to be carried out on RaspberryPi (ARM) architecture however there is nothing preventing using the POM for x86 Linux. The following assumes building on the Linux platform.

## Overview
The Maven build process aims to:
  1. Provide a simpler route to build OpenCV and Java libraries.
  2. Automatically checks the required native dependencies to build Java libraries.
  3. Make the Java libraries OSGi compatible.
  4. Include the native OpenCV native library inside the Java library.
  5. Allow the build to function on x86, x86_64 or amd architectures, Linux platform.

### Starting the build
#### Environment variables
**Applicability:** All processors.

   The following environment variables must be set otherwise the build will fail and halt:

   * `$JAVA_HOME` (the absolute path to the JDK root directory)
   * `$ANT_HOME` (the absolute path to the Ant root directory)

It is recommended that advantage is taken of multiple processor cores for the build to reduce build time. This can be done by a MAKEFLAGS environment specifying the number of parallel builds e.g.:

   * `$MAKEFLAGS="-j8"`

However if this flag is not set the build will NOT fail. On a RaspberryPi 2 typical build times are 5 hours with `-j1` and a little over 2 hours with `-j4`.

All of the above environment variables can be set on an ad-hoc basis using 'export'.
#### Build Directory
**Applicability:** All processors

By default the following build directories are created.

`<OpenCV_root_dir>/build`

`<OpenCV_root_dir>/build/target`

Under `build` can be found the standard OpenCV artifacts of the build. Under `build/target` can be found the OSGi compatible Java library
#### x86 or x86_64 Architecture:
Generally all that is required is the standard Maven command:

`mvn clean install`

One of the first things the build will do is check the required native dependencies. The Maven indicates the status of the required dependencies and will fail at this point if any are missing. Install using the package manager e.g. aptitude or apt-get.

Once the build succesfully completes the artifacts are available as described above in 'Build Directory'.

#### ARM 32-bit Architecture - Raspbian Distribution
Similar to the x86 architecture the native dependencies are first checked so install any that are missing, however currently there are no official `libtbb2` and `libtbb-dev` packages in the Raspbian. Version 4.4.3 of Intel's Thread Building Blocks library are available [here](http://http://www.javatechnics.com/thread-building-blocks-tbb-4-4-3-for-raspbian) as a Raspbian-compatible Debian packages.

**PLEASE NOTE THESE ARE NOT OFFICIAL RASPBIAN PACKAGES. INSTALL AT YOUR OWN RISK.**

OpenCV is built using CMake and the Maven build process uses the the [cmake-maven plugin](https://github.com/cmake-maven-project/cmake-maven-project). However the cmake-maven plugin by default downloads CMake at build time but unfortunately there is no binary for ARM architecture currently available. As a work around it is possible to use the native CMake (which is checked for availability in the above dependency checks). Assume all native dependencies are available the build can be started with the following command:

`mvn clean install -Duse.native.cmake=true`

Upon a successful build the libraries will be available as described above in 'Build Directory'.

#### cmake-mave-plugin 3.4.1-b2-SNAPSHOT
Should this plugin not be available in Maven central, the source can be found at my GitHub page [here](https://github.com/jtkb/cmake-maven-project), checkout the `raspberrypi` branch and install. On x86 it is a standard Maven build and install command. If building on Raspbian you also need to supply the `-Duse.native.cmake=true` command-line option.

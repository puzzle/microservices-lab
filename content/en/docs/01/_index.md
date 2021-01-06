---
title: "1. Getting Started"
weight: 1
sectionnumber: 1
---

## Introduction


### Requirements

Following software must be installed:

* Java 11
* Docker and Docker-Compose supporting at least version 3 of the compose format[^1].
  * Docker and Docker-Compose must be able to create volumes and mount local files
* Integrated Development Environment IDE. We do recommend [IntelliJ IDEA](https://www.jetbrains.com/idea/)

The following access must be available:

* Access to Docker Registries [docker.io](https://docker.io) and [quay.io](https://quay.io)
* Access to Source Repositories [github.com](https://github.com)


### Source-Code for Labs

Since writing the labs from scratch would take too much time we'll provide source-code for each lab to start with.

* Lab Source Git Repository: {{% param "lab_git_repo" %}}
* Lab Base Directory: {{% param "lab_code_basedir" %}}

We expect you to checkout this repository and switch to the corresponding lab source-code. The exact name of the source-code is provided in the Getting Started of each lab.

If you like to commit your changes feel free to fork this repository in your own space. If so, remember to use your own copy instead of the referenced repository whenever we link to it.


### Solution Repository

We also provide solution code for all labs.

* Solution Git Repository: {{% param "solution_git_repo" %}}
* Solution Base Directory: {{% param "solution_code_basedir" %}}

[^1]: Compose file: https://docs.docker.com/compose/compose-file/

---
title: "1. Getting Started"
weight: 1
sectionnumber: 1
---

## Introduction


### Requirements

Following Software is required to run the labs:

* Java 11
* Docker and Docker-Compose supporting at least version 3 of the compose format[^1].
* Integrated Development Environment IDE. We do recommend [IntelliJ IDEA](https://www.jetbrains.com/idea/).
  * Or any other editor you are comfortable with for editing Java files.

The following access must be available:

* Lab Pages: https://microservices-lab.k8s.puzzle.ch/
* Docker Registries: [docker.io](https://docker.io) and [quay.io](https://quay.io)
* Source Repositories: [github.com](https://github.com)


### Source-Code for Labs

Writing the labs from scratch would take too much time. We provide source-code to start with for the labs:

* Git Repository: {{% param "lab_git_repo" %}}
* Base Directory: {{% param "lab_code_basedir" %}}

You have to checkout this repository and switch to the corresponding lab source-code folder. You can open the `{{% param "lab_code_basedir" %}}` in your IDE.


### Solution Repository

Complete code for all labs can be found here:

* Git Repository: {{% param "solution_git_repo" %}}
* Base Directory: {{% param "solution_code_basedir" %}}

[^1]: Compose file: https://docs.docker.com/compose/compose-file/

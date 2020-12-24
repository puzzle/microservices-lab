---
title: "5. Debezium with Transactional Outbox Pattern"
weight: 1
sectionnumber: 5
---

## Introduction

In this lab we will use a different integration pattern. The pervious lab using kafka messaging has a certain design flaw. The microservices are writing to the database using a local transaction. however, this transaction does not span the writing to the kafka topic. if a rollback occures, only the data written to the database is rolled back. Handling such cases would need to be handled explicitely in the application code.

In this lab we will use the transactional outbox pattern[^1] to get rid of this flaw.

{{% alert title="Outbox Pattern" color="primary" %}} The outbox pattern is a way to safely and reliably exchange data between multiple (micro) services. An outbox pattern implementation avoids inconsistencies between a service’s internal state (as typically persisted in its database) and state in events consumed by services that need the same data. - [debezium.io](https://debezium.io/documentation/reference/configuration/outbox-event-router.html) {{% /alert %}}

For this to achieve we will use Kafka Connect with a debezium PostgreSQL source connector.


### What is Kafka Connect and Debezium?

{{% alert title="Kafka Connect" color="primary" %}} Kafka Connect is a tool for scalably and reliably streaming data between Apache Kafka and other systems. It makes it simple to quickly define connectors that move large collections of data into and out of Kafka. - [kafka.apache.org](https://kafka.apache.org/documentation/#connect) {{% /alert %}}


{{% alert title="Debezium PostgreSQL" color="primary" %}} Debezium’s PostgreSQL connector captures row-level changes in the schemas of a PostgreSQL database. [...] The connector produces a change event for every row-level insert, update, and delete operation that was captured and sends change event records for each table in a separate Kafka topic. Client applications read the Kafka topics that correspond to the database tables of interest, and can react to every row-level event they receive from those topics. - [debezium.io](https://debezium.io/documentation/reference/connectors/postgresql.html) {{% /alert %}}

Kafka Connect also provides the functionality to use single message transformations (SMT) to modify a message before it is sent to the Kafka Topic. The debezium connector also includes an Outbox Event Router[^2].


### How does the transactional outbox pattern work?

![Environment](outbox-pattern.png)


## Lab Artifacts

The source code for this lab is available here

kafka messaging as our communication channel.


### Overview

![Environment](debezium.png)


[^1]: Transactional Outbox Pattern: https://microservices.io/patterns/data/transactional-outbox.html
[^2]: Outbox Event Router: https://debezium.io/documentation/reference/configuration/outbox-event-router.htm

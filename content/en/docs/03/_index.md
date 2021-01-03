---
title: "3. Microservices Rest"
weight: 1
sectionnumber: 3
---

## Introduction

In the previous lab we worked on a monolithic application. In this lab we will distribute the monolith in two independent applications.
Will split up this monolith in an `order` and `stock` microservice.

Both microservices will use RESTful APIs to communicate with each other. We will see how we can build the `order` microservice
and why data consistency may be violated.

In a last step we will have a look at the Microprofile Long Running Actions (LRA)[^1] and how this could help for data consistency.
At the end we will see how a SAGA orchestration approach could be built using LRA.


## Splitting the Monolith

We will split our monolith in the two contexts `order` and `stock`. The `order` microservice will manage the orders. The `stock` microservice will manage the article stock and will decrement the available stock if there is a new order.

We expect the stock count of an article to be consistent with its order count.

![Monolith Split](split.png)


Can you imagine another context which exists in the monolith?

{{% details title="Hint" %}}
Think of a rich article catalog with pictures, thumbnails and a detailed description. There is a high chance that this would be managed in another independent microservice. For simplicity our articles are fully managed by the `stock` microservice.
{{% /details %}}


## Lab Overview

The first version we will only use the two microservice `order` and `stock`. In a second version we will introduce the `lra-coordinator` and our environment will look like this.

![Environment](rest-lra.png)

[^1]: Eclipse MicroProfile LRA: https://github.com/eclipse/microprofile-lra/blob/master/spec/src/main/asciidoc/microprofile-lra-spec.adoc

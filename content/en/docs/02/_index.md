---
title: "2. Mini-Monolith"
weight: 1
sectionnumber: 2
---

## Introduction
In this lab we will have a look at our shop application. The implementation consists of a monolith with its own
postgresql database. 

The application is a small shop and stock service. It provides an RESTful interface which can be used to order articles.
The internal stock count is managed by decreasing the article count whenever it is needed. If an order includes an 
article which is not or not completely in stock the order will be rejected.

## Lab Overview

![Environment](monolith.png)

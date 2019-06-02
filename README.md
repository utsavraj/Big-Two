Bigtwo
======

A card game called Big Two made in Java with multiplayer support on a network. Game rules are explained below.

Learnings
======
* OOP(Object Oriented Programming) approach to conveniently handle complex game mechanics and rules
* Java GUI for the client
* Proper use of exceptions for error handling
* Multi-threaded approach to handle networking
* Java Sockets to handle network multiplayer
* Proper comments and documentation has been made for all classes and methods.

Game rules
======

The basis of Big Two is a race to get rid of your cards. It supports 2, 3 or 4 players with one deck, and up to 8 players with two decks shuffled together. You will be dealt 13 cards in each game, and you can play these cards in four different ways:


|   Type of Hand                                                  | No. of Cards   |
|:----------------------------------------------------------------|:--------------:|
| Flush, Full House, Straight Flush, Four of a Kind*              | 5              |
| Straight                                                        | 4              |
| Three of a Kind                                                 | 3              |
| Pair                                                            | 2              |
| Single                                                          | 1              |

* = You must play a fifth card with four of a kind to make a legitimate five card poker hand.

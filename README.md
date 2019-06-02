Bigtwo
======

A card game called Big Two made in Java with multiplayer support on a network. Game rules are explained below.

Learnings
-----
* OOP(Object Oriented Programming) approach to conveniently handle complex game mechanics and rules
* Java GUI for the client
* Proper use of exceptions for error handling
* Multi-threaded approach to handle networking
* Java Sockets to handle network multiplayer
* Proper comments and documentation has been made for all classes and methods.

Game rules
-----

The basis of Big Two is a race to get rid of your cards. It supports 2, 3 or 4 players with one deck, and up to 8 players with two decks shuffled together. You will be dealt 13 cards in each game, and you can play these cards in four different ways:


|   Type of Hand                                                  | No. of Cards   |
|:----------------------------------------------------------------|:--------------:|
| Flush, Full House, Straight Flush, Four of a Kind*              | 5              |
| Straight                                                        | 4              |
| Three of a Kind                                                 | 3              |
| Pair                                                            | 2              |
| Single                                                          | 1              |

'*' = You must play a fifth card with four of a kind to make a legitimate five card poker hand.

**The game consists of a number of hands, each consisting of a number of rounds. Each hand begins by the players being dealt 13 cards (deal passes to the left after each hand).**

* The player who is dealt the 3 of Diamonds starts each hand (see below for how to discover this), and must make a play involving this card to begin the first round. For instance, they could just play the 3 of Diamonds as a single, or if they had a straight involving this 3, they could lead with that.

**NOTE**: (If no-one has the 3 of Diamonds, the player with the next lowest card leads with that instead).

* Whichever type of play is led, the next player clockwise around the table must play a higher card (or combination of cards) of the same type – for instance, if the player with the 3 of Diamonds plays a pair of 3’s to start the round, the next player must play a pair of a higher value.

* Players can choose to pass if they don’t want to play, and must pass if they cannot play. When all other players have passed, the last player to successfully make a play has possession and can begin a new round with whatever play they wish.

* Whatever type of play begins a round, all subsequent plays must be of the same type – for instance, if a player begins a round with a straight, the next player must play a higher straight or a better poker hand (e.g. a flush, full house, four of a kind or a straight flush).

* The hand ends when someone successfully plays their last card. 

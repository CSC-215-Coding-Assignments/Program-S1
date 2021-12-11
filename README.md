# Program-S1
A group project to solve the water pouring coding challenge.

Authors: Thomas Kwashnak, Emily Balboni, Priscilla Esteves

Project is hosted on a shared repository: [GitHub Link](https://github.com/CSC-215-Coding-Assignments/Program-S1)


## The Challenge
The challenge goes as this: You are given a list of jug sizes. Each jug is empty, except for the first one, which is filled. You are to find the fastest way to obtain a target pint T, while only doing complete pours (until either the pouring cup is empty, or the receiving cup is full).

The provided example is as follows: Given Jug sizes: 8,5,3, and target size 4: 

| Starting State |  Pour  | Ending State |
|:--------------:|:------:|:------------:|
|     8,0,0      | 0 -> 1 |    3,5,0     |
|     3,5,0      | 1 -> 2 |    3,2,3     |
|     3,2,3      | 2 -> 0 |    6,2,0     |
|     6,2,0      | 1 -> 2 |    6,0,2     |
|     6,0,2      | 0 -> 1 |    1,5,2     |
|     1,5,2      | 1 -> 2 |  1,**4**,3   |

Which we finally are able to get a pint size of 4. 

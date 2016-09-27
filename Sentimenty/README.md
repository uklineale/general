# Sentimenty
> Very Beta, be advised

Sentimenty is the fruit of a semester long Senior Design capstone project. It was built in for, and in partnership with, Deutsche Bank Global Technology. The webapp is designed to monitor Deutsche Bank in the news and on social media, and display that information in an easy to use dashboard.
>Note: As this was built for a school partnership in conjuction with DBGT, the app is currently hardcoded to pull, analyze, and display information about Deutsche Bank. If you would like to clone and have the app work for your company, change the string on line 46 of StockSchedule.java as well as the strings on line 41 and 133 of PressSchedule.java.

Some features include:
  - Real time and historical stock graphs in D3
  - Sentiment analysis using IBM Watson
  - A rearrangable interface



### Installation

Sentimenty requires only a few things to run.
  -  Java8
  -  Gradle
  -  MySQL


Install the dependencies and start the server.

```sh
$ cd Sentimenty
$ gradle build
$ gradle run
```


### Docker
Sentimenty is very easy to install and deploy in a Docker container.

```sh
cd Sentimenty
bash start.sh
```
Verify Sentimenty is running

```sh
127.0.0.1:6060
```

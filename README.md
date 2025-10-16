# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
## Phase 2 Sequence Diagram
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-Ds0KPMB4lUEiMAIEJ4oYoJwkEkSYCkm+hi7jS+4MkyU76XOnl3kuwowGKEpujKcplu8SqYCqwYagAchAUXarq+r6TeAUOkmvrOl2MA9n227uRZ-pJW6UYxnGhRaTl8DIKmMDpk4MDDDA2b8nm8zQUWJbdslbypXqUVGlaNouA29GZbygUWVZ4Vbm5grDvy9SHnIKDPvE56XteK2LpUy4BmuAa7YteXtjppYOeKGSqABmBXSB1S6YR5afIs3wUVR9afZpL3wsmDXYTAuEtW14Hvd1YxfZeP3IX9jYMZ43h+P4XgoOgMRxIkGNYw5vhYKJgqgfUDTSBG-ERu0EbdD0cmqApwzfYh6DofClRXS8Yws9RtSmbCGFzS61lCYT9li6eTlqC5xXLbeq0wDUSAAGaWFtO0vto-kzdlh3BaFT5nfIsryrzbNxeqp3DZASFGBAahoAA5MwxxgCA8TTQuAp1W53a9v2S2+wD9QAEIhltlUoLGCns3Volpk4ACMrVoO1Yw5qoXUFmMvXQPUPjTJe0BIAAXigux0U2+0+8L+Vba+F0ebr9JGCg3DHpeGIN9rXv7vewXSO3TKGD38hy3VXMCZLYB3Q9T3BxJYH-UvmHA2AOF4anIxV8jTFoyi67+Ng4oavxaIwAA4kqGjE6VpYNJf1N0-YSrM3DrM1QD2lma95sIwLL8JM8r1HPjka+OZpY5AnlSBWrdlZqw1hRPacCfb63qIba2V5tCmywfDWKqoNRbRgLbLGHAHaqGdq7OQHs+6zVyp2f2RUg73zDhHS8UcY7xlAvHdeicU4Qwzp1fMPViz5wVEXPUJdy6VyRnQ7Kdd6hj2ADAmQqDvJgAgWoDEOtvYDwweKdcWiPQ12eowq+SpG6dkur-a6aItGqDnggQCtjF41BeMsV+OYCwNHGF4lAABJaQBYk7hGCIEEEmx4i6hQG6TkexvjJFAGqOJkFPogn8QlJUf0YCdBXomIGJQN6gzwlmTxN8fF+KVEEkJYSInLCiTE1JH0YYgiSSAFJRFobfEydkmGFxcm70YqjAIHAADsbgnAoBav4CMwQ4BcQAGzwAnIYLRMAijr2AUvMmrQOgvzfpIyin8sy9LmPkygH5bHc3-gsDJSosktIbAvBhlkRZwFWVoqBWAg6wKyvAygqt1Zd2Ubo-uQUDESmUbgra+DLZEMvCQz+9tHYuxgG7Whpi3FWUKoHJurCYDhw4JHaM0dqpx0KY1dMAi04dVzCIwsYjSyFwotIiuk1q6oLMW8+uxsVG-LUf80cMBGSaKVBiM5XJ5EHSFJCoxlicGRUlQQ+KFi5hDX1P4gqFYTRmgtNaHItouXYpFsY861i3H+kDHqsMSFOHktqpSkG6ZMyjCEfS7qjK+pqsMLq4M5oYBjUNRyhiAqa5rU+eKyVYLAroOOvKuYJjjWKJ9VYnlNjBalg+UeFAXz-zOMerY7lr1-E1PqKE8JFzAaVATiU-CbrS3BPLXUoZKNmL+EsO3GymxsZIASGATtfYIA9oAFIQHFD6mI7S1SbKKds9xjQmjMhkj0fx794InNGNgBAwBO1QDgBAGyUA1iNqrT-TNNyP58zWOsHde6D1Hr2AAdRYAEmmPRQ78QUHAAA0j06pTaYAVsCIA8yryrJjvFHmwkMtVHhqVoCxBXdkG9yxXGzB0LIr-xVVbYhpC07kNRdQ92nssUptxcMCeBKiUkqqrHHhTrinUtTunTO2dRHepZcXKAZd2VDLI+BkWuK4PqJFUyLREqHlKhjXrWVIVDGpsVfKZV8KYBJRFRWHUw0tVBOlbXQT+VhMsNeWVCAWj7X0e-rwopidwa0vdVnBlucmX9Q0+qrTmqKxBMDQasAqgJr8eTQZ+oZrx5htE9kW16AMSheAMSDZqmH3QErHqkh4ZCgCY7DykLCqwv4pM6Wa1-rLBRbQBZ7hVnGOJ1dXSxznrnPepAIe5LfrqxpbthOvU2Q3PzCGeFoV9QABW460ASclapO9lAktQBkzKo6zJsBaHRIpk2HmYDbt3VN5rUA9PcqsrFqjnNrkwEg6NpUTiXGZuLcvdma8bN1qzFcORba0ZeF3b2-tb35SIGDLAYA2Bt2EDyAUDZd8CtkwplTGmdNjDbzPVci97VQNC2CzAEA3A8A6JEwNtHGOoALXkFjtDsqh4d0MAT-lTc-kt2Fej37yiifGrjaTkeWC02Dmp97NaePYuM6FfomQw9lsHeMw8RHO9C1XctTdx1Na+F1rh3IoAA


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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+iMykoKp+h-Ds0KPMB4lUEiMAIEJ4oYoJwkEkSYCkm+hi7jS+4MkyU76XOnl3kuwowGKEpujKcplu8SqYCqwYagAchAUXarq+r6TeAUOkmvrOl2G7ulubkgdU-pJW6UYxnGhRaTl8DIKmMDpk4MDDDA2b8nm8zQUWJbdslbypXqUVGlaNouA29Hbu5VK3vy9SHnIKDPvE56Xtew7ZZUy4BmuAbrUVeXtjppYOeKGSqABmAnSVElgYR5afIs3wUVR9bPZppXwsmDXYTAuEtW14GPd1YwvZeb3IR9jYMZ43h+P4XgoOgMRxIkSMow5vhYKJgqgfUDTSBG-ERu0EbdD0cmqApwyvYh6DofClQnS8Yx09RtSmbCGEWVZNn2Nj9lCdjTlqC502Cpt9IwDUSAAGaWCta0vto-m8oF23BaFT4HfIsryuzDNxeq+3DZASFGBAahoAA5MwxxgCA8SZer2W8y6ptXodna3TU9QAEIhitlUoLGCmM3Volpk4ACMrVoO1Yw5qoXUFmMvXQPUPjTJe0BIAAXigux0U2UsCkF9TSCg3Dop7r5HXVLMCcLp4XVdN11fj7WfXdmG-WAOF4fHIwl7DTEIyi67+Ng4oavxaIwAA4kqGi4xZkmL6TFP2EqtMQ-TNVfdpZm6Wz+8c1zX543l9Tzzky85qLOQSx5rvS7LCtKxR17GxqK0wObFGHAraqFtvbOQTsXYLnLu7fKK164+07l9AOQdLwhzDvGUCkd+7RzjkDJOnV8w9WLJnBUOc9R50LsXGGL9ZpZWloyMAD81AYjVtA+8WtxTrmYR6MuvsrI8O9pZd8zMT6nTRMw1QbcECATEUgu6Lxlg7xzAWBo4xlEoAAJLSALDHcIwRAggk2PEXUKA3Scj2N8ZIoA1TmMgs9EEGiEpKg+jAToPdEw-RKAPf6eEsxKJXqo9RSptG6P0YY5YxjTF2KemDEE1iQC2KIqDb4TiXFgwuG40ejF4YBA4AAdjcE4FALV-ARmCHALiAA2eAE5DDMJgEUfu18FGNFaB0beu9yGUQPlmNJcwPGUA-GI1mhsoaOKVM42JDYO65U7PUOAdTmFPywMVV+0DvJMKVBifpXIoH7g4fUbWS8lQRXlLs2KqoNQNJ1MNDRMAjQmjNBaa0ORbRzUXLA+ogj5Av3XqWQMzywxIXQdVRmfdvHR0zKMAhuYiGFhIaWBpTzgzmhgGNN5k0mxrLoW-UcMBFromYTsyZSo2EHIrrtbhpytx8PkcI-KPzgB-NEdzUsiyjwoGWf+GR10xG+1Pho0J9Q9EGMGd9SoUdfH4RhUKnRIrwnZLhsxfwlhq42U2KjJACQwBqr7BATVAApCA4oTlzBiAktUTTvEtL9m05kMkegaL3vBXpoxsAIGAGqqAcAIA2SgGsOV4rj5stGefd6ax1ieu9b6-1ewADqLBNFkx6P7fiCg4AAGlUkhPlTAUVgRL7mTmQy+oxrxTcsJGLWhMgPn4o-orS8ysvbyHJRrIURyuF120PrY04bCi-09gAg+ltrZ2xgA7SBdKvndt+Ws-5KCODB2jKHMFWCvGNXTHghOHU4XdQRX1MhFFKFFyxQxHFtb6H4sYcS3ZbatodpCl2plvaLmDqSjAe5tz9T3O0fswKM6mUso7HapKzDQXh1qhuv6zV46J2TqnYhh60DJS-WlT9FZtHoteWAVQE1skXrLvUAAViatAt7SUqRgB6r1lBY3QHvYuTW9QMghmo9GujfroCNMHfR2AKLqwAPDIUadJaBE0rnQ3BdAYqyhmExBzBR9sGQqahmAiCH4Xp0RQtLj-HZM1mEzAU1epsgYc+Nk0TIHxNzAQQy46IyYDlvI0qaRsi2UCrAsG5TjUAZZiuDQ5VCMvBeq1Tq4L8pEDBn49gD1hA8gFEaWvEtkkiYkzJhTYww8vOsq-C8ItPMxMexANwPArCa1EYJSVqA4VtBlbpcxquNdDA1ck4g3FGzKtRfgbVxj5dmMyGrkyQw3XWt2fWV5TreAmV1brYcgbTWzUoFs4OYZobu58vc-S3SXnoM+IBllmhQA

# LorcaHub Cardmarket URL Updater

Small Java utility to automate Cardmarket URL generation and synchronization for LorcaHub cards.

The script:

* Fetches cards from the LorcaHub API
* Generates normalized Cardmarket URLs automatically
* Handles duplicate names and alternate versions (`V1`, `V2`, etc.)
* Skips cards that already have a `cardMarketUrl`
* Updates missing URLs through the LorcaHub API

---

# Features

## Automatic URL generation

Generates Cardmarket URLs based on card names.

Example:

```text
Buzz Lightyear - Jungle Ranger
```

becomes:

```text
https://www.cardmarket.com/es/Lorcana/Products/Singles/Wilds-Unknown/Buzz-Lightyear-Jungle-Ranger
```

---

## Duplicate handling

Cards with identical names are automatically versioned:

```text
Card Name-V1
Card Name-V2
Card Name-V3
```

---

## Name normalization

The script automatically:

* Removes unsupported special characters
* Normalizes spaces
* Removes duplicated dashes
* Handles Cardmarket formatting differences

---

## API synchronization

Missing `cardMarketUrl` values can be updated automatically using the LorcaHub API.

---

# Project Structure

```text
src/main/java/org/updateUrls
│
├── GenerateCardmarketUrls.java
├── config
├── model
├── service
└── util
```

---

# Configuration

Edit:

```text
AppConfig.java
```

Available configuration:

```java
SET_CODE
SET_NAME
UPDATE_API_ENABLED
AUTH_TOKEN
```

---

# Running the project

## Build

```bash
mvn clean install
```

## Run

```bash
mvn exec:java
```

or directly from IntelliJ.

---

# Requirements

* Java 21+
* Maven
* Valid LorcaHub API token

---

# Notes

The LorcaHub API currently applies a daily update limit for Cardmarket URL changes.

Example API response after reaching the limit:

```text
429 - Daily CardMarket URL change limit reached
```

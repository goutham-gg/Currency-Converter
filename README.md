# Currency Converter Application 

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/maven-plugin/build-image.html)

### Maven Parent Overrides
Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

---

## Endpoints

The application provides the following REST endpoints:

### 1. Fetch Exchange Rates
#### **GET /api/rates?base=USD**
Fetches the exchange rates for the given base currency. If no base is provided, it defaults to USD.

#### **Request Example:**
```
GET /api/rates?base=USD
```

#### **Response Example:**
```json
{
  "base": "USD",
  "rates": {
    "EUR": 0.945,
    "GBP": 0.78,
    "INR": 82.5
  }
}
```

### 2. Convert Currency
#### **POST /api/convert**
Converts an amount from one currency to another using the fetched exchange rates.

#### **Request Body Example:**
```json
{
  "from": "USD",
  "to": "EUR",
  "amount": 100
}
```

#### **Response Example:**
```json
{
  "from": "USD",
  "to": "EUR",
  "amount": 100,
  "convertedAmount": 94.5
}
```

---

## Error Handling
If an error occurs while fetching exchange rates, the application returns a structured error response.

#### **Example Error Response:**
```json
{
  "error": true,
  "message": "Failed to fetch exchange rates: API access restricted.",
  "status": 400
}
```

Ensure that you have the correct API key and subscription plan for accessing base currency conversion.


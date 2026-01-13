selenium-framework/
│
├── pom.xml
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── base
│   │   │   │   └── BaseTest.java
│   │   │   │   └── DriverFactory.java
│   │   │
│   │   │   ├── pages
│   │   │   │   └── LoginPage.java
│   │   │
│   │   │   ├── utils
│   │   │   │   └── CSVUtils.java
│   │   │   │   └── ConfigReader.java
│   │   │
│   │   │   └── listeners
│   │   │       └── TestListener.java
│   │
│   │   └── resources
│   │       ├── log4j2.xml
│   │       ├── config.properties
│   │
│   └── test
│       ├── java
│       │   └── tests
│       │       └── LoginTest.java
│       │
│       └── resources
│           └── testdata
│               └── loginData.csv
│
└── testng.xml

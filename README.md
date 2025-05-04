# Maniflow
Application for personal financial management. 
The application integrates managing your debts, incomes, expenses and your stock portfolio.

## Installation
Clone the repo: `git clone https://github.com/TrueBubo/Maniflow`  
Go to the project directory: `cd Maniflow`  
Enter your Polygon API key in `api.properties`: `vim api.properties`
Start the MongoDB database: `sudo docker-compose up -d`  
Build app: `./mvnw clean package`

## Running the app
Example arguments for the application can be found below  
`java -jar target/Maniflow.jar <Application arguments>`

## Example usage
```shell
java -jar target/Maniflow.jar --add-income 2000 --currency USD
java -jar target/Maniflow.jar --buy-stock AAPL-1.3
java -jar target/Maniflow.jar --stats
```

To see the all the commands available you can run  
`java -jar target/Maniflow.jar --help`

## Documentation
Documentation is located in [here](docs/apidocs/index.html)  
Documentation can be generated with  
`./mvnw javadoc:javadoc`
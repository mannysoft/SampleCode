# Node Js sample code for Visa API calls

## Installation

Install the package using 
	
	$ npm install

## Usage

Update the `configuration.json` file under the `config` folder. For more information on `configuration.json` refer :
	 
* [Manual](https://github.com/visa/SampleCode/wiki/Manual)


Run Visa API calls using the command below:

	$ npm test

You would need to generate a Call Id for calling Visa Checkout. The documentation for generating Call Id can be found at :

* [Visa Checkout Guide](https://github.com/visa/SampleCode/wiki/Visa-Checkout)

Auto-population of test data is available only for Visa Transaction Alerts for now. We are working on this and will try to further improve your experience by generating it for other products as well.

The sample code provided reads the credentials from configuration file as plain text. As a best practice we recommend you to store the credentials in an encrypted form and decrypt while using them.
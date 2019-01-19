# PHP Sample Code for Visa API calls

## Installation

To install the dependencies run the following command :

	$ composer install

## Usage

We use phpunit to run our sample calls

Update the `configuration.ini` file under the `root` folder. For more information on `configuration.ini` refer :
	 
* [Manual](https://github.com/visa/SampleCode/wiki/Manual)

Then you can run the API calls using the command below:
    
    $ ./vendor/bin/phpunit

To run an individual test file :

	$ ./vendor/bin/phpunit tests/atmlocator/LocateAtmApiTest.php

You would need to generate a Call Id for calling Visa Checkout. The documentation for generating Call Id can be found at :

* [Visa Checkout Guide](https://github.com/visa/SampleCode/wiki/Visa-Checkout)

The sample code provided reads the credentials from configuration file as plain text. As a best practice we recommend you to store the credentials in an encrypted form and decrypt while using them.

# Scala sample code for Visa API calls

## Prerequisites

### [Scala](https://www.scala-lang.org/download/install.html)
### [Scala Build Tool - sbt](http://www.scala-sbt.org/0.13/docs/Setup.html)

## Installation

Install the dependencies using 
	
	$ sbt compile

## Usage

* Update the `app.conf` file under the `config` folder.

* Run Visa API calls using the command below:

	$ sbt test

You would need to get credentials of following vdp applications in order to run the tests.

* [Visa Direct](https://developer.visa.com/products/visa_direct/reference)
* [CyberSource Payments](https://developer.visa.com/products/cybersource/reference)

For more information about get started with the APIs check the following guide.

* [Getting Started with Visa Developer](https://developer.visa.com/vdpguide#get-started-overview)

The sample code provided reads the credentials from configuration file as plain text. As a best practice we recommend you to store the credentials in an encrypted form and decrypt while using them.

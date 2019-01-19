# Java sample code for Visa API calls


## Installation

You must have Maven installed. Install the dependencies using :

    $ mvn install
	 
## Usage

For generating you jks using the steps below :

 Place your private key file (e.g. privateKey.pem) and your certificate file from VDP (e.g. cert.pem) in the same directory. Generate a keystore (e.g. myapp_keyAndCertBundle.p12) file as shown below.

	openssl pkcs12 -export -in cert.pem -inkey "privateKey.pem" -certfile cert.pem -out myapp_keyAndCertBundle.p12

 To get the Java Key Store, run the following Java keytool command to convert your P12 file to JKS file

	keytool -importkeystore -srckeystore myapp_keyAndCertBundle.p12 -srcstoretype PKCS12 -destkeystore myapp_keyAndCertBundle.jks

 Run the following Java keytool command to validate the contents of your new JKS file.

	keytool -list -v -keystore myapp_keyAndCertBundle.jks

 Run the following command to add the root certificate to your JKS file.

	keytool -import -alias ejbca -keystore myapp_keyAndCertBundle.jks -file VDPCA-SBX.pem -storepass <password>

Fill up the required values in the file `configuration.properties` located under the folder `resources`. To find more more information on `configuration.properties` refer :

* [Manual](https://github.com/visa/SampleCode/wiki/Manual)

To run these API calls give the command as shown below :
    
    $ mvn test

The tests are written using TestNG. You can also run tests for specific products by passing `-Dgroups=Group#name`. For example :

	$ mvn test -Dgroups=mlc


You would need to generate a Call Id for calling Visa Checkout. The documentation for generating Call Id can be found at :

* [Visa Checkout Guide](https://github.com/visa/SampleCode/wiki/Visa-Checkout)

The sample code provided reads the credentials from configuration file as plain text. As a best practice we recommend you to store the credentials in an encrypted form and decrypt while using them.

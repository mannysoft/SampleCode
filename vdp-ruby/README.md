# Ruby Sample Code for Visa API calls

## Installation

Install it yourself as:

    $ bundle install
    
## Usage

Get bundler before making the API calls.

	$ gem install bundler

Update the `configuration.yml` file under the `root` folder. For more information on `configuration.yml` refer :
	 
* [Manual](https://github.com/visa/SampleCode/wiki/Manual)

Run VDP API calls by using the command below: 
	
	 $ bundle exec rake

Run individual tests using the command below:

	 $ bundle exec ruby -Itest test/vctc/consumer_rules_test_new.rb

You would need to generate a Call Id for calling Visa Checkout. The documentation for generating Call Id can be found at :

* [Visa Checkout Guide](https://github.com/visa/SampleCode/wiki/Visa-Checkout)

The sample code provided reads the credentials from configuration file as plain text. As a best practice we recommend you to store the credentials in an encrypted form and decrypt while using them.

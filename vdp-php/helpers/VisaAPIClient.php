<?php

namespace Vdp;

class VisaAPIClient {
	
	public function __construct() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->timeout = 80;
		$this->connectTimeout = 30;
	}
	
	private function loggingHelper( $response, $curl, $testInfo, $requestBody ) {
		printf("%s\n",$testInfo);
		if(!$response) {
			printf ('Error: "' . curl_error($curl) . '" - Code: ' . curl_errno($curl));
		} else {
			if (empty($requestBody) == false && $requestBody != '') {
				$json = json_decode($requestBody);
				$json = json_encode($json, JSON_PRETTY_PRINT);
				printf("Request Body : %s\n", $json);
			}
			$header_size = curl_getinfo($curl, CURLINFO_HEADER_SIZE);
			$header = substr($response, 0, $header_size);
			$body = substr($response, $header_size);
			printf ("Response Status: %s\n",curl_getinfo($curl, CURLINFO_HTTP_CODE));
			printf($header);
			if (empty($body) == false && $body != '') {
				$json = json_decode($body);
				$json = json_encode($json, JSON_PRETTY_PRINT);
				printf("Response Body : %s\n", $json);
			}
			
		}
	}
	
	/* Correlation Id ( ex-correlation-id ) is an optional header while making an API call. You can skip passing the header while calling the API's. */
	private function getCorrelationId() {
		$seed = str_split('abcdefghijklmnopqrstuvwxyz'
                 .'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
                 .'0123456789');
		shuffle($seed);
		$rand = '';
		foreach (array_rand($seed, 12) as $k) $rand .= $seed[$k];
		return $rand."_SC";
	}
	
	private function getBasicAuthHeader($userId, $password) {
		$authString = $userId.":".$password;
		$authStringBytes = utf8_encode($authString);
		$authloginString = base64_encode($authStringBytes);
		return "Authorization:Basic ".$authloginString;
	}
	
	public function doMutualAuthCall($method, $path, $testInfo, $requestBodyString, $inputHeaders = array()) {
		$curl = curl_init ();
		$method = strtolower ( $method );
		$certificatePath = $this->conf ['VDP'] ['cert'];
		$privateKey = $this->conf ['VDP'] ['key'];
		$userId = $this->conf ['VDP'] ['userId'];
		$password = $this->conf ['VDP'] ['password'];
		$absUrl = $this->conf ['VDP'] ['visaUrl'].$path;
		$authHeader = $this->getBasicAuthHeader($userId, $password);
		
		$headers = (array("Accept: application/json", $authHeader, "ex-correlation-id: ".$this->getCorrelationId()));
		if (count($inputHeaders) > 0) {
			foreach ($inputHeaders as &$header) {
				array_push($headers, $header);
			}
		}
		$opts = array ();
		if ($method == 'get') {
			$opts [CURLOPT_HTTPGET] = 1;
		} elseif ($method == 'post') {
			array_push($headers, "Content-Type: application/json");
			$opts [CURLOPT_POST] = 1;
			$opts [CURLOPT_POSTFIELDS] = $requestBodyString;
		}
		
		$opts [CURLOPT_URL] = $absUrl;
		$opts [CURLOPT_RETURNTRANSFER] = true;
		$opts [CURLOPT_CONNECTTIMEOUT] = $this->connectTimeout;
		$opts [CURLOPT_TIMEOUT] = $this->timeout;
		$opts [CURLOPT_HTTPHEADER] = $headers;
		$opts [CURLOPT_HEADER] = 1;
		$opts [CURLOPT_SSLCERT] = $certificatePath;
		$opts [CURLOPT_SSLKEY] = $privateKey;
		
		curl_setopt_array ( $curl, $opts );
		$response = curl_exec ( $curl );
		$this->loggingHelper( $response, $curl, $testInfo, $requestBodyString );
		$statusCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
		curl_close ( $curl );
		return $statusCode;
	}
	
	public function doXPayTokenCall($method, $baseUrl, $resource_path, $query_string, $testInfo, $requestBodyString, $inputHeaders = array()) {
		$curl = curl_init ();
		$method = strtolower ( $method );
		$sharedSecret = $this->conf ['VDP'] ['sharedSecret'];
		$apiKey = $this->conf ['VDP'] ['apiKey'];
		$time = time(); 
		$preHashString = $time.$resource_path.$query_string.$requestBodyString; 
		$xPayToken = "xv2:".$time.":".hash_hmac('sha256', $preHashString, $sharedSecret);
		$headers = (array("Accept: application/json", "X-PAY-TOKEN: ".$xPayToken, "ex-correlation-id: ".$this->getCorrelationId()));
		$absUrl = $this->conf ['VDP'] ['visaUrl'].$baseUrl.$resource_path.'?'.$query_string;
		if (count($inputHeaders) > 0) {
			foreach ($inputHeaders as &$header) {
				array_push($headers, $header);
			}
		}
		$opts = array ();
		if ($method == 'get') {
			$opts [CURLOPT_HTTPGET] = 1;
		} elseif ($method == 'post') {
			array_push($headers, "Content-Type: application/json");
			$opts [CURLOPT_POST] = 1;
			$opts [CURLOPT_POSTFIELDS] = $requestBodyString;
		} elseif ($method == 'put') {
			array_push($headers, "Content-Type: application/json");
			$opts [CURLOPT_CUSTOMREQUEST] = "PUT";
			$opts [CURLOPT_POSTFIELDS] = $requestBodyString;
		}
	
		$opts [CURLOPT_URL] = $absUrl;
		$opts [CURLOPT_RETURNTRANSFER] = true;
		$opts [CURLOPT_CONNECTTIMEOUT] = $this->connectTimeout;
		$opts [CURLOPT_TIMEOUT] = $this->timeout;
		$opts [CURLOPT_HTTPHEADER] = $headers;
		$opts [CURLOPT_HEADER] = 1;
		
		curl_setopt_array ( $curl, $opts );
		$response = curl_exec ( $curl );
		$this->loggingHelper( $response, $curl, $testInfo, $requestBodyString );
		$statusCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
		curl_close ( $curl );
		return $statusCode;
	}
}

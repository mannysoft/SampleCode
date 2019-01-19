require 'rest-client'
require 'yaml'

class VisaAPIClient
  
  def initialize
    @config = YAML.load_file('configuration.yml')
  end
  
  def getCorrelationId()
    # Passing correlation id header (ex-correlation-id) is optional while making API calls.
    correlation_id = (0...12).map { (48 + rand(10)).chr }.join
    return correlation_id
  end
  
  def logRequest(test_info, url, request_body)
    puts test_info
    puts "URL : #{url}"
    if (request_body != nil && request_body != '')
      puts "Request Body : "
      puts request_body
    end
  end
  
  def logResponse(response)
    puts "Response Status : #{response.code.to_s}"
    puts "Response Headers : " 
    for header,value in response.headers
       puts "#{header.to_s} : #{value.to_s}"
    end
    puts "Response Body : " + JSON.pretty_generate(JSON.parse(response.body))
  end
  
  def get_xpay_token(resource_path, query_string, request_body)
    timestamp = Time.now.utc.to_i
    config = YAML.load_file('configuration.yml')
    shared_secret = config['sharedSecret']
    hash_input = "#{timestamp}#{resource_path}#{query_string}#{request_body}"
    hash_output = OpenSSL::HMAC.hexdigest(OpenSSL::Digest.new('sha256'), shared_secret, hash_input)
    "xv2:#{timestamp}:#{hash_output}"
  end
  
  def doMutualAuthRequest(path, test_info, method_type, request_body, headers={})
    url = "#{@config['visaUrl']}#{path}"
    user_id = @config['userId']
    password = @config['password']
    key_path = @config['key']
    cert_path = @config['cert']
    correlation_id = getCorrelationId()
    logRequest(test_info, url, request_body)
    if method_type == 'post' || method_type == 'put'
      headers['Content-type'] = 'application/json'
    end
    headers['accept'] = 'application/json'
    headers['ex-correlation-id'] = "#{correlation_id}_SC" 
    begin
      response = RestClient::Request.execute(
      :method => method_type,
      :url => url,
      :headers => headers,
      :payload => request_body,
      :user => user_id,
      :password => password,
      :ssl_client_key => OpenSSL::PKey::RSA.new(File.read(key_path)),
      :ssl_client_cert =>  OpenSSL::X509::Certificate.new(File.read(cert_path))
      )
      logResponse(response)
      return response.code.to_s
    rescue RestClient::ExceptionWithResponse => e
      logResponse(e.response)
      return e.response.code.to_s
    end
  end
  
  def doXPayTokenRequest(base_uri, resource_path, query_string, test_info, method_type, request_body, headers={})
      api_key = @config['apiKey']
      shared_secret = @config['sharedSecret']
      url = "#{@config['visaUrl']}#{base_uri}#{resource_path}?#{query_string}"
      correlation_id = getCorrelationId()
      logRequest(test_info, url, request_body)
      xpay_token = get_xpay_token(resource_path, query_string, request_body)
      if method_type == 'post' || method_type == 'put'
          headers['Content-type'] = 'application/json'
      end
      headers['accept'] = 'application/json'
      headers['ex-correlation-id'] = "#{correlation_id}_SC"
      headers['x-pay-token'] = xpay_token 
      begin
        response = RestClient::Request.execute(
        :method => method_type,
        :url => url,
        :headers => headers,
        :payload => request_body
        )
        logResponse(response)
        return response.code.to_s
      rescue RestClient::ExceptionWithResponse => e
        logResponse(e.response)
        return e.response.code.to_s
      end
    end

end

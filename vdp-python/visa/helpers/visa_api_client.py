import requests
import os
import sys
import logging
import json
import string
import random
if sys.version_info < (3, 0):
    import ConfigParser as parser
else:
    import configparser as parser

import hmac
import datetime
from hashlib import sha256
import calendar
'''
@author: visa
'''

class VisaAPIClient:
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'configuration.ini'))
    config.read(config_path)
    
    logging.getLogger('').addHandler(logging.StreamHandler())
    log = logging.getLogger('VisaAPIClient')

    def _get_x_pay_session(self, shared_secret, resource_path, query_string, body):
        return XSession(shared_secret, resource_path, query_string, body)

    def _get_mutual_auth_session(self, user_name, password, cert, key):
        return MSession(user_name, password, cert, key)
    
    """
       Correlation Id ( ex-correlation-id ) is an optional header while making an API call. You can skip passing the header while calling the API's.
    """
    def _get_correlation_id(self):
        size = 12 
        chars = string.digits
        correlationId = ''.join(random.choice(chars) for _ in range(size)) + '_SC'
        return correlationId
    
    def _logging_helper(self, url, response, test_info, body):
        
        self.log.info(test_info)
        self.log.info(url)
        if body !='':
            self.log.info(json.dumps(body, indent=4, sort_keys=True))
        self.log.info("Response Code : " + str(response.status_code))
        self.log.info("Response Headers : ")
        for header in response.headers:
            self.log.info(header +":"+ response.headers[header])
        self.log.info("Response Body : ")
        if response.text != '':
            self.log.info(json.dumps(json.loads(response.text), indent=4, sort_keys=True))
        
    def do_mutual_auth_request(self, path, body, test_info, method_type, input_headers={}):
        user_name = self.config.get('VDP','userId')
        password= self.config.get('VDP','password')
        cert = self.config.get('VDP','cert')
        key = self.config.get('VDP','key')
        end_point = self.config.get('VDP','visaUrl')
        url = end_point + path
        response = {}
        self.session = self._get_mutual_auth_session(user_name, password, cert, key)
        if input_headers:
            for key in input_headers.keys():
                self.session.headers[key] = input_headers[key]
        if method_type == 'post' or method_type == 'put':
            self.session.headers.update({'content-type': 'application/json',
                         'accept': 'application/json',
                         'x-correlation-id' : self._get_correlation_id()})
            if method_type == 'post' :
                response = self.session.post(url, json = body, timeout = 10)
            if method_type == 'put' :
                response = self.session.put(url, json = body, timeout = 10)
            self._logging_helper(url, response, test_info, body)
        elif method_type == 'get':
            self.session.headers.update({'accept': 'application/json','ex-correlation-id' : self._get_correlation_id(),
                                         'content-type': 'application/json'})
            response = self.session.get(url, timeout = 10)
            self._logging_helper(url, response, test_info, '')
        return response
        
    def do_x_pay_request(self, base_uri, resource_path, query_string, body, test_info, method_type, input_headers={}):
        shared_secret = self.config.get('VDP','sharedSecret')
        end_point = self.config.get('VDP','visaUrl')
        url = end_point +  base_uri + resource_path + '?'+ query_string
        self.log.info(url)
        response = {}
        if method_type == 'get' :
            self.session = self._get_x_pay_session(shared_secret, resource_path, query_string, '')
        else :
            self.session = self._get_x_pay_session(shared_secret, resource_path, query_string, json.dumps(body))
        if input_headers:
            self.log.info(input_headers)
            for key in input_headers.keys():
                self.session.headers[key] = input_headers[key]
        if method_type == 'post' or method_type == 'put':
            self.session.headers.update({'content-type': 'application/json',
                                'accept': 'application/json',
                                'x-pay-token' : self.session.x_pay_token,
                                'ex-correlation-id' : self._get_correlation_id()})
            if method_type == 'post' :
                response = self.session.post(url, json = body, timeout = 10)
            if method_type == 'put' :
                response = self.session.put(url, json = body, timeout = 10)
            self._logging_helper(url, response, test_info, body)        
        elif method_type == 'get':
            self.session.headers.update({
                                'x-pay-token' : self.session.x_pay_token,
                                'x-correlation-id' : self._get_correlation_id()})
            response = self.session.get(url, timeout = 10)
            self._logging_helper(url, response, test_info, '')
        return response
        
class XSession(requests.Session):
    """ Requests Session for xpaytoken apis
        Construct as XSession(apikey, shared_secret), usage same as
        requests.Session
    """    
    def _get_timestamp(self):
        d = datetime.datetime.utcnow()
        timestamp = calendar.timegm(d.timetuple())
        return str(timestamp)

    def _get_x_pay_token(self, shared_secret, resource_path, query_string, body):
        timestamp = self._get_timestamp()
        pre_hash_string = timestamp + resource_path + query_string + body
        if sys.version_info < (3, 0):
            hash_string = hmac.new(shared_secret, msg=pre_hash_string.rstrip(), digestmod=sha256).hexdigest()
        else:
            hash_string = hmac.new(str.encode(shared_secret), msg=pre_hash_string.rstrip().encode('utf-8'), digestmod=sha256).hexdigest()
        return 'xv2:' + timestamp + ':' + hash_string

    def __init__(self, shared_secret, resource_path, query_string, body):
        super(XSession, self).__init__()
        self.x_pay_token = self._get_x_pay_token(shared_secret, resource_path, query_string, body)

class MSession(requests.Session):
    """Requests Session for mutualauth(2-way SSL) apis
    username, password: App credentials
    cert: path to app's client certificate
    key: path to app's private key
    """

    def __init__(self, username, password, cert, key):
        super(MSession, self).__init__()
        self.cert = (cert, key)
        self.auth = (username, password)        

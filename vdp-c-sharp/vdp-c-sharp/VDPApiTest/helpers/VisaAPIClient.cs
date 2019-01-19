using System;
using System.IO;
using System.Net;
using System.Text;
using System.Configuration;
using System.Security.Cryptography.X509Certificates;
using System.Security.Cryptography;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Vdp
{
    public class VisaAPIClient
    {
        private void LogRequest(string url, string requestBody)
        {
            Debug.WriteLine(url);
            Debug.WriteLine(requestBody);
        }

        private void LogResponse(string info, HttpWebResponse response)
        {
            string responseBody;
            Debug.WriteLine(info);
            Debug.WriteLine("Response Status: \n" + response.StatusCode);
            Debug.WriteLine("Response Headers: \n" + response.Headers.ToString());
            
            using (var reader = new StreamReader(response.GetResponseStream(), ASCIIEncoding.ASCII))
            {
                responseBody = reader.ReadToEnd();
            }

            Debug.WriteLine("Response Body: \n" + responseBody);
        }

        //Correlation Id ( ex-correlation-id ) is an optional header while making an API call. You can skip passing the header while calling the API's.
        private string GetCorrelationId()
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            var random = new Random();
            return new string(Enumerable.Repeat(chars, 12).Select(s => s[random.Next(s.Length)]).ToArray()) + "_SC";

        }

        private string GetBasicAuthHeader(string userId, string password)
        {
            string authString = userId + ":" + password;
            var authStringBytes = Encoding.UTF8.GetBytes(authString);
            string authHeaderString = Convert.ToBase64String(authStringBytes);
            return "Basic " + authHeaderString;
        }

        public string DoMutualAuthCall(string path, string method, string testInfo, string requestBodyString, Dictionary<string, string> headers = null)
        {
            string requestURL = ConfigurationManager.AppSettings["visaUrl"] + path;
            string userId = ConfigurationManager.AppSettings["userId"];
            string password = ConfigurationManager.AppSettings["password"];
            string certificatePath = ConfigurationManager.AppSettings["cert"];
            string certificatePassword = ConfigurationManager.AppSettings["certPassword"];
            string statusCode = "";
            LogRequest(requestURL, requestBodyString);
            // Create the POST request object 
            HttpWebRequest request = WebRequest.Create(requestURL) as HttpWebRequest;
            request.Method = method;
            if (method.Equals("POST") || method.Equals("PUT"))
            {
                request.ContentType = "application/json";
                request.Accept = "application/json";
                // Load the body for the post request
                var requestStringBytes = Encoding.UTF8.GetBytes(requestBodyString);
                request.GetRequestStream().Write(requestStringBytes, 0, requestStringBytes.Length);
            }

            if (headers != null)
            {
                foreach (KeyValuePair<string, string> header in headers)
                {
                    request.Headers[header.Key] = header.Value;
                }
            }
            
            // Add headers
            request.Headers["Authorization"] = GetBasicAuthHeader(userId, password);
            request.Headers["ex-correlation-id"] = GetCorrelationId();
            // Add certificate
            var certificate = new X509Certificate2(certificatePath, certificatePassword);
            request.ClientCertificates.Add(certificate);

            try
            {
                // Make the call
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    LogResponse(testInfo, response);
                    statusCode = response.StatusCode.ToString();
                }
            }
            catch (WebException e)
            {
                if (e.Response is HttpWebResponse)
                {
                    HttpWebResponse response = (HttpWebResponse)e.Response;
                    LogResponse(testInfo, response);
                    statusCode = response.StatusCode.ToString();
                }
            }
            return statusCode;
        }

        public string DoXPayTokenCall(string baseUri, string resourcePath, string queryString, string method, string testInfo, string requestBodyString)
        {
            string requestURL = ConfigurationManager.AppSettings["visaUrl"] + baseUri + resourcePath + "?" + queryString;
            string apikey = ConfigurationManager.AppSettings["apiKey"];
            LogRequest(requestURL, requestBodyString);
            // Create the POST request object 
            string statusCode = "";
            HttpWebRequest request = WebRequest.Create(requestURL) as HttpWebRequest;
            request.Method = method;
            if (method.Equals("POST") || method.Equals("PUT"))
            {
                request.ContentType = "application/json";
                request.Accept = "application/json";
                // Load the body for the post request
                var requestStringBytes = Encoding.UTF8.GetBytes(requestBodyString);
                request.GetRequestStream().Write(requestStringBytes, 0, requestStringBytes.Length);
            }

            string xPayToken = GetXPayToken(resourcePath, "apikey=" + apikey, requestBodyString);
            request.Headers["x-pay-token"] = xPayToken;
            request.Headers["ex-correlation-id"] = GetCorrelationId();

            try
            {
                // Make the call
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    LogResponse(testInfo, response);
                    statusCode = response.StatusCode.ToString();
                }
            }
            catch (WebException e)
            {
                if (e.Response is HttpWebResponse)
                {
                    HttpWebResponse response = (HttpWebResponse)e.Response;
                    LogResponse(testInfo, response);
                    statusCode = response.StatusCode.ToString();
                }
            }
            return statusCode;
        }

        private static string GetXPayToken(string apiNameURI, string queryString, string requestBody)
        {
            string timestamp = GetTimestamp();
            string sourceString = timestamp + apiNameURI + queryString + requestBody;
            string hash = GetHash(sourceString);
            string token = "xv2:" + timestamp + ":" + hash;
            return token;
        }

        private static string GetTimestamp()
        {
            long timeStamp = ((long)DateTime.UtcNow.Subtract(new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc)).TotalMilliseconds) / 1000;
            return timeStamp.ToString();
        }

        private static string GetHash(string data)
        {
            string sharedSecret = ConfigurationManager.AppSettings["sharedSecret"];
            var hashString = new HMACSHA256(Encoding.ASCII.GetBytes(sharedSecret));
            var hashbytes = hashString.ComputeHash(Encoding.ASCII.GetBytes(data));
            string digest = String.Empty;

            foreach (byte b in hashbytes)
            {
                digest += b.ToString("x2");
            }

            return digest;
        }
    }
}

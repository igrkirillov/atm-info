package ru.alfabattle.alfa;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.alfabattle.alfa.pojo.AlfaAtms;
import ru.alfabattle.alfa.pojo.DataDto;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

@Service
public class AlfaApiImpl implements AlfaApi, InitializingBean {

    @Value("${ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${ssl.key-store-type}")
    private String keyStoreType;
    @Value("${ssl.key-store}")
    private Resource resource;

    private RestTemplate restTemplate;

    @Override
    public DataDto getAtms() {
        return getForObject("https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms/status", DataDto.class);
    }

    private <T> T getForObject(String url, ParameterizedTypeReference<T> responseType) {
        try {
            RequestEntity<Void> request = RequestEntity.get(new URL(url).toURI())
                    .accept(MediaType.APPLICATION_JSON).build();
            return restTemplate.exchange(request, responseType).getBody();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private <T> T getForObject(String url, Class<T> responseType) {
        try {
            RequestEntity<Void> request = RequestEntity.get(new URL(url).toURI())
                    .accept(MediaType.APPLICATION_JSON)
                    .header("X-IBM-Client-Id", "389d8168-c365-4e68-96eb-1ec6025dd619")
                    .header("x-client-certificate", "-----BEGIN CERTIFICATE-----" +
                            "MIIFIzCCAwugAwIBAgICEBkwDQYJKoZIhvcNAQELBQAwgakxCzAJBgNVBAYTAlJV" +
                            "MQ8wDQYDVQQIDAbQnNCh0JoxFTATBgNVBAcMDNCc0L7RgdC60LLQsDEcMBoGA1UE" +
                            "CgwT0JDQu9GM0YTQsCDQkdCw0L3QujEOMAwGA1UEAwwFYXBpY2ExJTAjBgkqhkiG" +
                            "9w0BCQEWFmFwaXN1cHBvcnRAYWxmYWJhbmsucnUxDzANBggqhQMDgQMBARIBMDEM" +
                            "MAoGBSqFA2QDEgEwMB4XDTE4MDQyMzA2NDIwN1oXDTMxMTIzMTA2NDIwN1owgaAx" +
                            "CzAJBgNVBAYTAlJVMQwwCgYDVQQIDANNU0sxEjAQBgNVBAoMCUFsZmEgQmFuazEW" +
                            "MBQGA1UEAwwNYXBpZGV2ZWxvcGVyczElMCMGCSqGSIb3DQEJARYWYXBpc3VwcG9y" +
                            "dEBhbGZhYmFuay5ydTEPMA0GCCqFAwOBAwEBEgEwMQwwCgYFKoUDZAMSATAxETAP" +
                            "BgorBgEEAYGCaWQBDAEwMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA" +
                            "6+wUdr+1nyb6gxg7E6HzzR48rE25js5/fpM5GOoGVFfgT502XdSHXdYGDT3OPsNi" +
                            "x2nBfPOppzDZdOJQbk+XPQ6Bj8u8FRkkd6gRSLQQbLLDe/C2IDhGxuFHeQWVvb7P" +
                            "L/w7srAxH0SOVEJgn/8tD5D9FaoFN6NaUk34eb/tojZJpbydhx+eWFtBXtrxEtES" +
                            "bGyTm2X7Q2VG36PqCwQdgdNwf6JUN8dIYotG+4rEJp1xsDqf7U8I5VoT1sE7rAY6" +
                            "fEHuThHtENCd5JLqRiFqVSbSsxXhO5COofkUeXBfnUxD9/auSdwqX+6DdhS6HWcN" +
                            "3P4nBLjlVM0M7P6t8fIQGwIDAQABo1wwWjBABgNVHR8EOTA3MDWgM6Axhi9odHRw" +
                            "Oi8vYXBpY2EubW9zY293LmFsZmFpbnRyYS5uZXQvYXBpY2EuY3JsLnBlbTAJBgNV" +
                            "HRMEAjAAMAsGA1UdDwQEAwIF4DANBgkqhkiG9w0BAQsFAAOCAgEANbGOlIxDFxfq" +
                            "igiqWuRpnsg7vgqRbCSy1HpkTs8y2XNdG6jsxA3a42vQCWy74cXmEXrf7m9BQBh3" +
                            "HIck4Ag5azo1+svwJmExhVx3P7RdmP4DuqO1XsWLPJmaeMWFDm71PO2N1vLOtsym" +
                            "np71JPBV+x6mY9S0ecW37ZdrCUjKgWnxIijneLTF3NIkaNoB6WPdlasPF+KmATv8" +
                            "eMuTZj27e9xLVikTC+5mBtM8mQiWYP4dStcOI7TO810/6PJkekjPYNV5ldzSif+E" +
                            "R3y9U0x/sdaRmnR9vCw7geEKtum3JMv6uqYumaqMeB8ZEUEkZdgLFgGzDc10VuRO" +
                            "wqN89qA8wQV7gN2nlQ7NdcrLcyq1NLH5EYP9AjgR47Ure93rOJvrr8w7c+WpYIGM" +
                            "EjtMySrIiRtYtDrxTH/jZPUFoKRphyQ3s4Ja/86L8ONSBFm/F9TAGqrzaMrhHM1n" +
                            "lX8fNl6BLBVFN7iDjvAzTCrIrG/fOh6MI1R1icbjiREWBzcij7lyENqLzQUZQiLx" +
                            "SQLQ7dKd6YkjNmMl+TL4Z7HAyiaJzOuypKX5g6q+KoH10LXUB5pq+8tGI7J3K06l" +
                            "rF28VZd8TPzOgll/N/Gz9Ce8eOoQG+dOS0Omv5Bz1cstue4I0+NuE+vVcQIkQPQT" +
                            "YzGbty5dALrDx1II4VsZnzjmJaqzq98=" +
                            "-----END CERTIFICATE-----")
                    .build();
            return restTemplate.exchange(request, responseType).getBody();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private CloseableHttpClient httpClient() throws Exception {

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        KeyStore trustStore = KeyStore.getInstance(keyStoreType);

        InputStream inputStream = resource.getInputStream();

        try {
            if (inputStream != null) {
                trustStore.load(inputStream, keyStorePassword.toCharArray());
                keyManagerFactory.init(trustStore, keyStorePassword.toCharArray());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        sslcontext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1.2"}, null, new NoopHostnameVerifier());

        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restTemplate = new RestTemplate(clientHttpRequestFactory());
    }
}

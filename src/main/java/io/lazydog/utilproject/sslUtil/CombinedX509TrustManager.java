package io.lazydog.utilproject.sslUtil;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombinedX509TrustManager implements X509TrustManager {

    private final List<X509TrustManager> trustManagers;

    public CombinedX509TrustManager(TrustManager[]... trustManagers) {
        this.trustManagers = Arrays.stream(trustManagers)
                .flatMap(Arrays::stream)
                .filter(tm -> tm instanceof X509TrustManager)
                .map(tm -> (X509TrustManager) tm)
                .collect(Collectors.toList());
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509TrustManager tm : trustManagers) {
            try {
                tm.checkClientTrusted(chain, authType);
                return;
            } catch (CertificateException e) {
                // ignore
            }
        }
        throw new CertificateException("None of the TrustManagers trust this certificate chain");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509TrustManager tm : trustManagers) {
            try {
                tm.checkServerTrusted(chain, authType);
                return;
            } catch (CertificateException e) {
                // ignore
            }
        }
        throw new CertificateException("None of the TrustManagers trust this certificate chain");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        List<X509Certificate> certificates = new ArrayList<>();
        for (X509TrustManager tm : trustManagers) {
            certificates.addAll(Arrays.asList(tm.getAcceptedIssuers()));
        }
        return certificates.toArray(new X509Certificate[0]);
    }
}
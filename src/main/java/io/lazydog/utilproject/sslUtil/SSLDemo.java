package io.lazydog.utilproject.sslUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.DomainLoadStoreParameter;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

public class SSLDemo {

    public static void main(String[] args) throws Exception {
        // 指定jks文件的路径和密码
        String keystorePath = "C:\\Users\\lazydog\\IdeaSnapshots\\SpringPlain\\src\\main\\resources\\keystore\\domain.jks";
        String keystorePassword = "123456";

        // 创建一个KeyStore对象，加载jks文件
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());


        // jdk 自带
        KeyStore cacerts = KeyStore.getInstance(KeyStore.getDefaultType());

        FileInputStream cacertsIn = new FileInputStream(new File(System.getProperty("java.home") + "/lib/security/cacerts"));
        cacerts.load(cacertsIn, "changeit".toCharArray());
        cacertsIn.close();
//
//        TrustManagerFactory cacertsTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        cacertsTmf.init(cacerts);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        DomainLoadStoreParameter dlsParam = new DomainLoadStoreParameter(null, Map.of("truststore", trustStore, "keystore", keyStore));
        tmf.init(dlsParam.);
//        final KeyStore[] keyStores = {cacerts, keyStore};
//        tmf.init(keyStores);

        // 创建一个SSLContext对象，指定协议为TLS
        SSLContext sslContext = SSLContext.getInstance("TLS");


//        final TrustManager[] trustManagers = ArrayUtil.addAll(instance.getTrustManagers(),cacertsTmf.getTrustManagers());

        // 创建一个合并了两个trustmanager的trustmanager
        TrustManager[] combinedTrustManagers = new TrustManager[]{
                new CombinedX509TrustManager(instance.getTrustManagers(), cacertsTmf.getTrustManagers())
        };
        // 初始化SSLContext对象，传入KeyManager数组，TrustManager数组和SecureRandom对象
        sslContext.init(null, combinedTrustManagers, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        final URL url1 = new URL("https://www.baidu.com/");

        url1.openConnection().connect();

//        final URL url = new URL("https://tom.cat:8443/hello");
//
//        url.openConnection().connect();


    }
}
package io.lazydog.utilproject.sslUtil;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLDemo {

    public static void main(String[] args) throws Exception {
        // 指定jks文件的路径和密码
        String keystorePath = "chain.jks";
        String keystorePassword = "123456";

        // 创建一个KeyStore对象，加载jks文件
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

        // 创建一个KeyManagerFactory对象，初始化它
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

        // 创建一个SSLContext对象，指定协议为TLS
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // 初始化SSLContext对象，传入KeyManager数组，TrustManager数组和SecureRandom对象
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // 从SSLContext对象中获取SSLSocketFactory对象
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // 使用SSLSocketFactory对象创建SSLSocket对象，连接到指定的服务器和端口
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("www.example.com", 443);

        // 进行通信...
    }
}
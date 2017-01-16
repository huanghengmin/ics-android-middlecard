package com.fri.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.fri.ZDTokenApi;
import com.zd.vpn.util.Sender;

import org.spongycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class ZDTokenUtils {

    ZDTokenClient mZDTokenClient;

    ZDTokenApi mZDTokenApi;


    private byte[] getCert(Context context) {
        if (mZDTokenApi == null)
            mZDTokenApi = new ZDTokenApi();
        if (mZDTokenClient == null)
            mZDTokenClient = new ZDTokenClient(context);
        try {
            mZDTokenClient.bindTokenService();
            mZDTokenApi.Initialize();
            byte[] cert = mZDTokenApi.LoadCert();
            return cert;
        } catch (Exception e) {
            return null;
        } finally {
            mZDTokenApi.UnInitialize();
            mZDTokenClient.unbindTokenService();
        }
    }


    public void loadCrt(Context context) {
        SharedPreferences sPreferences = context.getSharedPreferences("com.zd.vpn", Context.MODE_PRIVATE);
        String pin = sPreferences.getString("vpn.pin", "");
        String strContainer = sPreferences.getString("vpn.certContainerName", "");
        final Sender sender = new Sender(context);
        if (pin.equals("")) {
            sender.sendTF("PIN码未设置,请先配置PIN码!");
        }
        if (strContainer.equals("")) {
            sender.sendTF("证书容器未设置,请先配置证书容器!");
        }
        try {
            byte[] Cert = getCert(context);
            CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
            X509Certificate x509certificate = (X509Certificate) certificate_factory.generateCertificate(new ByteArrayInputStream(Cert));
            String serialNum = x509certificate.getSerialNumber().toString(16).toUpperCase();
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString("vpn.serialNumber", serialNum);
            editor.putBoolean("vpn.read", true);
            editor.putString("vpn.subject", x509certificate.getSubjectDN().toString());
            editor.putString("vpn.notBefore", x509certificate.getNotBefore().toString());
            editor.putString("vpn.notAfter", x509certificate.getNotAfter().toString());
            editor.putString("vpn.issue", x509certificate.getIssuerDN().toString());
            editor.putString("vpn.cert", new String(Base64.encode(x509certificate.getEncoded())));
            editor.commit();
        } catch (Exception e) {

        }

    }

    public String getCrt(Context context) {
        SharedPreferences sPreferences = context.getSharedPreferences("com.zd.vpn", Context.MODE_PRIVATE);
        boolean read = sPreferences.getBoolean("vpn.read", false);
        final Sender sender = new Sender(context);
        if (!read) {
            sender.sendTF("请先初始化基本配置！");
            return null;
        } else {
            String cert = sPreferences.getString("vpn.cert", null);
            return cert;
        }
    }

}



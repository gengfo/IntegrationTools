package com.oocl.inittools.perfrpt.test.gmail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.imap.IMAPFolder;

public class GoogleMailTest {

    public static void main(String args[]) throws Exception {

        // Properties props = System.getProperties();
        // props.setProperty("mail.imaps.host", "imap.gmail.com");
        // props.setProperty("mail.imaps.port", "993");
        // props.setProperty("mail.imaps.connectiontimeout", "5000");
        // props.setProperty("mail.imaps.timeout", "5000");
        //
        // try {
        // Session session = Session.getDefaultInstance(props, null);
        // URLName urlName = new URLName("imap://gengfo@gmail.com:bzmtck1204119@,#@imap.gmail.com");
        // Store store = session.getStore(urlName);
        // if (!store.isConnected()) {
        // store.connect();
        // }
        // } catch (NoSuchProviderException e) {
        // e.printStackTrace();
        // System.exit(1);
        // } catch (MessagingException e) {
        // e.printStackTrace();
        // System.exit(2);
        // }

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.connectiontimeout", "5000");
        props.setProperty("mail.imaps.timeout", "5000");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "gengfo@gmail.com", "bzmtck1204119@,#");
            
            IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
            if (folder == null) {
                throw new Exception("No named inbox");
            }
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                long uid = folder.getUID(messages[i]);
                System.out.println(uid);
            }

            System.out.println("Done");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }

    }

}

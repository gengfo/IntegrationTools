package com.oocl.inittools.perfrpt.test.gmail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Test1 {
    
    public static void main(String args[]) throws MessagingException, IOException{
        
        String server = "192.168.10.205";
        String username = "test";
        String password = "test";

        Properties properties = System.getProperties();

        Session session = Session.getDefaultInstance(properties, null);

        Store store = session.getStore("pop3");
        store.connect(server, username, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);


        Message[] messages = folder.getMessages();

        for (int i = 0; i < messages.length; i++) {
            System.out.println("Date : " + messages[i].getSentDate());
            System.out.println("From : " + messages[i].getFrom()[0]);
            System.out.println("Subject : " + messages[i].getSubject());
            System.out.println("Content : " + messages[i].getContent());
        }
    }
        
    

}

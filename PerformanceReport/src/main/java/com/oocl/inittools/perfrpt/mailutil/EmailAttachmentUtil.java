package com.oocl.inittools.perfrpt.mailutil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.imap.IMAPFolder;

public class EmailAttachmentUtil {

    public static void collectMailAttachment(MailAccount ma, String mailFolderName, String attachFolderRoot,
            String subject, String sendDateFrom, String sendDateTo, DateFormat formatter) {

        IMAPFolder folder = getMailFolder(ma, mailFolderName);

        try {
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();

            List<String> mailList = new ArrayList<String>();
            for (int i = 0; i < messages.length; i++) {

                long uid = folder.getUID(messages[i]);
                mailList.add(Long.toString(uid));

                for (int j = 0; j < mailList.size(); j++) {
                    if (String.valueOf(uid).equals(mailList.get(j))) {

                        Message message = messages[i];
                        Object content = message.getContent();
                        if (content instanceof Multipart) {
                            Multipart mPart = (Multipart) content;

                            String mailSubject = folder.getMessage(i + 1).getSubject();
                            System.out.println("mailSubject -> " + mailSubject);

                            System.out.println("Sent Date -> " + folder.getMessage(i + 1).getSentDate());

                            Date mailDate = folder.getMessage(i + 1).getSentDate();

                            String dateStr = formatter.format(folder.getMessage(i + 1).getSentDate());

                            if (!needCheckMail(mailSubject, mailDate, formatter, subject, sendDateFrom, sendDateTo)) {

                                continue;
                            }

                            try {
                                String saveFileName = mPart.getBodyPart(1).getFileName();
                                saveFileName = saveFileName.substring(0, saveFileName.length() - 3);
                                // System.out.println(saveFileName);
                                saveFileName = saveFileName + dateStr;
                                // System.out.println(saveFileName);
                                saveFileName = saveFileName + ".xls";
                                // System.out.println(saveFileName);

                                InputStream in = mPart.getBodyPart(1).getInputStream();

                                OutputStream out = new BufferedOutputStream(new FileOutputStream(attachFolderRoot
                                        + File.separator + saveFileName));
                                int len = -1;
                                while ((len = in.read()) != -1) {
                                    out.write(len);
                                }
                                out.close();
                                in.close();
                            } catch (Exception ex) {

                            }
                        } else {
                            // none attachment
                        }
                    }
                }

            }

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean needCheckMail(String mailSubject, Date mailDate, DateFormat formatter, String subject,
            String sendDateFrom, String sendDateTo) {
        // DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateFrom = null;
        Date dateTo = null;

        boolean flag = false;

        try {
            dateFrom = formatter.parse(sendDateFrom);
            dateTo = formatter.parse(sendDateTo);
            Calendar calender = Calendar.getInstance();
            calender.setTime(dateTo);
            calender.add(Calendar.DATE, 1);
            Date dateToAddOneDay = calender.getTime();

            flag = (dateFrom.before(dateToAddOneDay) || dateFrom.equals(dateToAddOneDay))
                    && (mailDate.after(dateFrom) || mailDate.equals(dateFrom))
                    && (mailDate.before(dateToAddOneDay) || mailDate.equals(dateToAddOneDay))
                    && mailSubject.contains(subject);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        // sendDateFrom

        // return mailSubject.contains(subject) &&

        return flag;

    }

    public static IMAPFolder getMailFolder(MailAccount ma, String folderName) {

        System.setProperty("javax.net.ssl.trustStore", "D:\\ToDel\\mailSec\\non-prd\\jssecacerts");

        Session session = Session.getInstance(System.getProperties(), null);
        session.setDebug(false);

        Store store = null;
        IMAPFolder folder = null;
        try {
            store = session.getStore(ma.getReceiveProtocol());
            store.connect(ma.getReceiveHost(), ma.getPort(), ma.getUsername(), ma.getPassword());
            folder = (IMAPFolder) store.getFolder(folderName);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return folder;

    }

    public static void main() {

    }

}

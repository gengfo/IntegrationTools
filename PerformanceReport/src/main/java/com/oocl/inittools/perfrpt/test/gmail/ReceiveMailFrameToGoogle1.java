package com.oocl.inittools.perfrpt.test.gmail;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.mail.imap.IMAPFolder;

public class ReceiveMailFrameToGoogle1 extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextArea ta_receive;

    protected Session session;

    protected Store store;

    private String receiveHost = "imap.gmail.com";

    private String receiveProtocol = "imap";

    private String username = "gengfo@gmail.com";

    private String password = "bzmtck1204119@,#";

    private Vector<String> readedEmailUIDVector = new Vector<String>();

    private ObjectOutputStream objectOut = null;

    private ObjectInputStream objectIn = null;

    private int ReadedNums = 0;

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ReceiveMailFrameToGoogle1 frame = new ReceiveMailFrameToGoogle1();
                    frame.init();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ReceiveMailFrameToGoogle1() {
        super();
        getContentPane().setLayout(new BorderLayout());
        setTitle("Receive Email");
        setBounds(100, 100, 386, 314);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        ta_receive = new JTextArea();
        scrollPane.setViewportView(ta_receive);

        final JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    receiveMessage();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        button.setText("Recieve Email and Download");
        panel.add(button);

        final JButton button_1 = new JButton();
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        button_1.setText("Exit");
        panel.add(button_1);
    }

    public void init() throws Exception {
        // Properties props = new Properties();

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imaps.socketFactory.fallback", "false");
        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.connectiontimeout", "5000");
        props.setProperty("mail.imaps.timeout", "5000");

        props.put("mail.store.protocol", receiveProtocol);
        props.put("mail.imaps.class", "com.sun.mail.imap.IMAPStore");
        
        
        session = Session.getDefaultInstance(props);
        store = session.getStore(receiveProtocol);
        store.connect(receiveHost, username, password);
    }

    public void receiveMessage() throws Exception {
        IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
        if (folder == null) {
            throw new Exception("No named inbox");
        }
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            long uid = folder.getUID(messages[i]);
            try {
                objectIn = new ObjectInputStream(new FileInputStream("c:/readedEmail.txt"));
                Object readObj = objectIn.readObject();
                if (readObj == null) {
                    JOptionPane.showMessageDialog(null, "No read email");
                    return;
                } else {
                    readedEmailUIDVector = (Vector<String>) readObj;
                    for (int j = 0; j < readedEmailUIDVector.size(); j++) {
                        if (String.valueOf(uid).equals(readedEmailUIDVector.get(j))) {
                            ReadedNums++;
                            readMessage(messages[i], folder, i);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        ta_receive.append("Received" + folder.getMessageCount() + "\n");
        if (ReadedNums > 0) {
            if (ReadedNums == folder.getMessageCount()) {
                ta_receive.append("All read\n\n");
            } else {
                ta_receive.append("Some of" + ReadedNums + " read\n\n");
            }
        } else {
            ta_receive.append("No read\n\n");
        }
        folder.close(false);
        store.close();
        if (objectOut != null)
            objectOut.close();
        if (objectIn != null)
            objectIn.close();
    }

    public void readMessage(Message message, IMAPFolder folder, int i) throws Exception {
        Object content = message.getContent();
        if (content instanceof Multipart) {
            ta_receive.append("----��" + (i + 1) + "���ʼ�----\n");
            ta_receive.append("���⣺" + folder.getMessage(i + 1).getSubject() + "\n");// ����
            Multipart mPart = (Multipart) content;// ���Multipart����
            ta_receive.append("���ģ�" + mPart.getBodyPart(0).getContent() + "\n");
            ta_receive.append("�������ڣ�" + folder.getMessage(i + 1).getSentDate() + "\n");// ��������
            Address[] ias = folder.getMessage(i + 1).getFrom();// �����˵�ַ
            ta_receive.append("�����ˣ�" + ias[0] + "\n");
            Address[] iasTo = folder.getMessage(i + 1).getAllRecipients();// �ռ��˵�ַ
            ta_receive.append("�ռ��ˣ�" + iasTo[0] + "\n\n");
            try {
                String fileName = mPart.getBodyPart(1).getFileName();// ����ļ���
                ta_receive.append("���յ�һ����Ϊ��" + MimeUtility.decodeText(fileName) + "���ĸ���\n\n");
                InputStream in = mPart.getBodyPart(1).getInputStream();// �������������
                FileDialog dialog = new FileDialog(ReceiveMailFrameToGoogle1.this, "����");// �����Ի���
                dialog.setMode(FileDialog.SAVE);// ���öԻ���Ϊ����Ի���
                dialog.setFile(MimeUtility.decodeText(fileName));
                dialog.setVisible(true);// ��ʾ����Ի���
                String path = dialog.getDirectory();// ����ļ��ı���·��
                String saveFileName = dialog.getFile();// ��ñ�����ļ���
                if (path == null || saveFileName == null) {
                    return;
                }
                OutputStream out = new BufferedOutputStream(new FileOutputStream(path + "/" + saveFileName));// �������������
                int len = -1;
                while ((len = in.read()) != -1) {// ��ȡ����������Ϣ
                    out.write(len);// �������д�븽����Ϣ
                }
                out.close();
                in.close();
            } catch (Exception ex) {

            }
        } else {// û�и���ִ�еĴ���
            ta_receive.append("----��" + (i + 1) + "���ʼ�----\n");
            ta_receive.append("���⣺" + folder.getMessage(i + 1).getSubject() + "\n");// ����
            ta_receive.append("���ģ�" + folder.getMessage(i + 1).getContent() + "\n");// ����
            ta_receive.append("�������ڣ�" + folder.getMessage(i + 1).getSentDate() + "\n");// ��������
            Address[] ias = folder.getMessage(i + 1).getFrom();// �����˵�ַ
            ta_receive.append("�����ˣ�" + ias[0] + "\n");
            Address[] iasTo = folder.getMessage(i + 1).getAllRecipients();// �ռ��˵�ַ
            ta_receive.append("�ռ��ˣ�" + iasTo[0] + "\n\n");
        }
    }
}

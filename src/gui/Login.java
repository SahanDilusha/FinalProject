/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import model.MySQL;
import model.SendEmail;
import java.util.logging.*;
import javax.swing.SwingUtilities;
import model.LoggerSetup;

/**
 *
 * @author sahan
 */
public class Login extends javax.swing.JFrame {

    private int at = 0;
    private int at2 = 0;
    private boolean st = true;
    private String email;
    private String name;
    private int tp;

    private static final Logger logger = LoggerSetup.getLogger();

    /**
     * Creates new form Login
     */
    private void switchTheme(boolean isDarkTheme) {

        if (isDarkTheme == true) {
            FlatLaf.setup(new FlatLightLaf());
        } else {
            FlatLaf.setup(new FlatDarkLaf());
        }

        // Update the UI to apply the new look and feel
        SwingUtilities.updateComponentTreeUI(this);
    }

    private final String generateOTP() {
        // Generate a random 6-digit number
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999

        // Format the OTP as a string
        String otpString = String.valueOf(otp);

        return otpString;
    }

    private final void ShowOTPPanal() {
        main.removeAll();
        main.add(jPanel3);
        main.repaint();
        main.revalidate();
    }

    private final void ShowCashierDashbord() {
        SendLoginMail(name, email);
        this.dispose();
        CashierDashbord ca = new CashierDashbord();
        ca.setVisible(true);
        ca.setExtendedState(MAXIMIZED_BOTH);

    }

    private final void ShowAccountDashbord() {
        SendLoginMail(name, email);
        this.dispose();
        AccountDashbord ad = new AccountDashbord();
        ad.setVisible(true);
    }

    private final void ShowAdminDashbord() {
        SendLoginMail(name, email);
        this.dispose();
        AdminDashbord ad = new AdminDashbord();
        ad.setVisible(true);
        ad.setExtendedState(MAXIMIZED_BOTH);
    }

    private final String SendLoginMail(String name, String email) {

        return SendEmail.send(email, "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Login Notification</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            background-color: #f4f4f4;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 0 auto;\n"
                + "            padding: 20px;\n"
                + "            background-color: #ffffff;\n"
                + "            border-radius: 8px;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #4CAF50;\n"
                + "            color: #ffffff;\n"
                + "            padding: 10px;\n"
                + "            text-align: center;\n"
                + "            border-radius: 8px 8px 0 0;\n"
                + "        }\n"
                + "        .header h1 {\n"
                + "            margin: 0;\n"
                + "            font-size: 24px;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .content h2 {\n"
                + "            font-size: 20px;\n"
                + "            color: #333333;\n"
                + "        }\n"
                + "        .content p {\n"
                + "            font-size: 16px;\n"
                + "            color: #666666;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            padding: 10px;\n"
                + "            font-size: 14px;\n"
                + "            color: #999999;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>Login Notification</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h2>Hello,</h2>\n"
                + "            <p>We wanted to inform you just logged into the system.</p>\n"
                + "            <p><strong>Details:</strong></p>\n"
                + "            <ul>\n"
                + "                <li><strong>Name:</strong> " + name + "</li>\n"
                + "                <li><strong>Login Time:</strong> " + new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa").format(new Date()) + "</li>\n"
                + "            </ul>\n"
                + "            <p>If this was not you, please contact the system administrator immediately.</p>\n"
                + "            <p>Thank you.</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>&copy; " + Year.now().getValue() + " Supermarket Management System. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>", "Login Notification");
    }

    private final String SendOtpMail(String email, String otp) {

        return SendEmail.send(email, "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Login OTP</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            background-color: #f4f4f4;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 0 auto;\n"
                + "            padding: 20px;\n"
                + "            background-color: #ffffff;\n"
                + "            border-radius: 8px;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #4CAF50;\n"
                + "            color: #ffffff;\n"
                + "            padding: 10px;\n"
                + "            text-align: center;\n"
                + "            border-radius: 8px 8px 0 0;\n"
                + "        }\n"
                + "        .header h1 {\n"
                + "            margin: 0;\n"
                + "            font-size: 24px;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .content h2 {\n"
                + "            font-size: 20px;\n"
                + "            color: #333333;\n"
                + "        }\n"
                + "        .content p {\n"
                + "            font-size: 16px;\n"
                + "            color: #666666;\n"
                + "        }\n"
                + "        .otp {\n"
                + "            font-size: 24px;\n"
                + "            font-weight: bold;\n"
                + "            color: #4CAF50;\n"
                + "            margin: 20px 0;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            text-align: center;\n"
                + "            padding: 10px;\n"
                + "            font-size: 14px;\n"
                + "            color: #999999;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <h1>One-Time Password (OTP)</h1>\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h2>Hello,</h2>\n"
                + "            <p>To complete your login, please use the following one-time password (OTP). This OTP is valid for the next 10 minutes.</p>\n"
                + "            <div class=\"otp\">" + otp + "</div>\n"
                + "            <p>If you did not request this OTP, please ignore this email or contact support.</p>\n"
                + "            <p>Thank you,</p>\n"
                + "            <p>The Supermarket Management System Team</p>\n"
                + "        </div>\n"
                + "        <div class=\"footer\">\n"
                + "            <p>&copy; " + Year.now().getValue() + " Supermarket Management System. All rights reserved.</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>", "Login OTP");

    }

    public Login() {
        initComponents();
        jLabel9.setText("codely-group©" + Year.now().toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        main = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/main-lg-icon.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Adobe Arabic", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Food City");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Welcome!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(jLabel3)))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(66, 66, 66))
        );

        main.setLayout(new java.awt.CardLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel5.setText("Login");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Username");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Passoword");

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox1.setText("Show Password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Enter your username and password login to the system.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("jLabel9");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton2.setText("Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close-icon.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jTextField1)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(244, 244, 244))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(198, 198, 198))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jCheckBox1)
                .addGap(24, 24, 24)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(22, 22, 22))
        );

        main.add(jPanel2, "card2");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel10.setText("Login");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close-icon.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setOpaque(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("OTP Code");

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton4.setText("Verify ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 102));
        jButton5.setText("Resend code ?");
        jButton5.setContentAreaFilled(false);
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Please enter your OTP Code to proceed.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(237, 237, 237))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel10)
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addGap(61, 61, 61)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        main.add(jPanel3, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {

            jPasswordField1.setEchoChar((char) 0);

        } else {
            jPasswordField1.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (jTextField1.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "please enter your username!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(jPasswordField1.getPassword()).isBlank()) {
            JOptionPane.showMessageDialog(this, "please enter your password!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (!String.valueOf(jPasswordField1.getPassword()).matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
            JOptionPane.showMessageDialog(this, "invalid password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        } else {

            try {

                if (at != 3) {

                    ResultSet result = MySQL.execute("SELECT * FROM `users` INNER JOIN `branch` ON `users`.`us_branch` = `branch`.`b_id`  INNER JOIN `users_type` ON `users`.`us_type` =  `users_type`.`ut_id` WHERE `users`.`us_password` = '" + String.valueOf(jPasswordField1.getPassword()) + "' AND `users`.`us_username` = '" + jTextField1.getText() + "';");

                    if (result.next()) {

                        if (result.getInt("us_status") != 1) {
                            at += 1;
                            JOptionPane.showMessageDialog(this, "Your account is currently disabled!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                        } else {

                            Preferences preferences = Preferences.userRoot();

                            String filePath = "src/image/dp_image.png";

                            // Get the image bytes from the database
                            byte[] imageBytes = null;
                            String encodedImage = result.getString("us_dp");
                            if (encodedImage != null && !encodedImage.isEmpty()) {
                                try {
                                    imageBytes = Base64.getDecoder().decode(encodedImage);
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                    JOptionPane.showMessageDialog(this, "Error decoding image! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            // Save the retrieved image to local path
                            if (imageBytes != null) {

                                try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
                                    fos.write(imageBytes);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    JOptionPane.showMessageDialog(this, "Error saving image to local path!", "Error", JOptionPane.ERROR_MESSAGE);
                                    logger.log(Level.WARNING, "An error occurred", e);
                                }
                            }

                            preferences.put("id", result.getString("us_id"));
                            preferences.put("username", result.getString("us_username"));
                            preferences.put("first_name", result.getString("us_fname"));
                            preferences.put("last_name", result.getString("us_lname"));
                            preferences.put("email", result.getString("us_email"));
                            preferences.put("mobile", result.getString("us_mobile"));
                            preferences.put("branch", result.getString("b_name"));
                            preferences.put("user_type_id", result.getString("ut_id"));
                            preferences.put("user_type_name", result.getString("ut_name"));
                            preferences.put("branch_id", result.getString("b_id"));
                            preferences.put("two_facter", result.getString("us_two_facter"));
                            preferences.put("print", result.getString("us_print"));
                            preferences.put("theme", result.getString("us_theme"));
                            preferences.put("dp", filePath);

                            if (result.getString("us_theme").equals("1")) {
                                switchTheme(true);
                            } else {
                                switchTheme(false);
                            }

                            email = result.getString("us_email");
                            name = result.getString("us_fname") + " " + result.getString("us_lname");

                            if (result.getString("us_two_facter").equals("1")) {
                                st = false;
                            } else {
                                st = true;
                            }

                            if (result.getInt("us_type") == 1) {

                                if (st == true) {
                                    ShowCashierDashbord();

                                } else {
                                    tp = result.getInt("us_type");
                                    ShowOTPPanal();
                                    SendOtpMail(result.getString("us_email"), result.getString("us_otp"));
                                }

                            } else if (result.getInt("us_type") == 5) {
                                if (st == true) {
                                    ShowAccountDashbord();
                                } else {
                                    tp = result.getInt("us_type");
                                    ShowOTPPanal();
                                    SendOtpMail(result.getString("us_email"), result.getString("us_otp"));
                                }
                            } else if (result.getInt("us_type") == 6) {
                                if (st == true) {
                                    ShowAdminDashbord();
                                } else {
                                    tp = result.getInt("us_type");
                                    ShowOTPPanal();
                                    SendOtpMail(result.getString("us_email"), result.getString("us_otp"));
                                }
                            } else {
                                at += 1;
                                JOptionPane.showMessageDialog(this, "invalid username or password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                                JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);

                            }
                        }

                    } else {
                        at += 1;
                        JOptionPane.showMessageDialog(this, "invalid username or password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);

                    }

                } else {

                    JOptionPane.showMessageDialog(this, "attempt are over!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

                    ResultSet result = MySQL.execute("SELECT * FROM `users` WHERE `users`.`us_username` = '" + jTextField1.getText() + "' AND `users`.`us_status`='1'");

                    if (result.next()) {
                        MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_username` = '" + jTextField1.getText() + "';");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "An error occurred", e);
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (at2 == 3) {
            JOptionPane.showMessageDialog(this, "attempt are over!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            try {

                ResultSet result = MySQL.execute("SELECT * FROM `users` WHERE `users`.`us_username` = '" + jTextField1.getText() + "' AND `users`.`us_status`='1'");

                if (result.next()) {
                    MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_username` = '" + jTextField1.getText() + "';");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }
        } else if (jTextField2.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "please enter OTP code!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                ResultSet result = MySQL.execute("SELECT `us_type` FROM `users` WHERE  `users`.`us_username` = '" + jTextField1.getText() + "' AND `users`.`us_otp` = '" + jTextField2.getText() + "';");

                if (result.next()) {

                    MySQL.execute("UPDATE `users` SET `users`.`us_otp` = '" + generateOTP() + "' WHERE `users`.`us_username` = '" + jTextField1.getText() + "';");

                    if (result.getInt("us_type") == 1) {
                        ShowCashierDashbord();
                    } else if (result.getInt("us_type") == 5) {
                        ShowAccountDashbord();
                    } else if (result.getInt("us_type") == 6) {
                        ShowAdminDashbord();
                    }

                } else {

                    if (at2 == 3) {
                        JOptionPane.showMessageDialog(this, "attempt are over!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                        try {

                            ResultSet result1 = MySQL.execute("SELECT * FROM `users` WHERE `users`.`us_username` = '" + jTextField1.getText() + "' AND `users`.`us_status`='1'");

                            if (result1.next()) {
                                MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_username` = '" + jTextField1.getText() + "';");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {

                        at2++;
                        JOptionPane.showMessageDialog(this, "please check your OTP code!", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at2) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String otp = generateOTP();

        try {

            MySQL.execute("UPDATE `users` SET `users`.`us_otp` = '" + otp + "' WHERE `users`.`us_username` = '" + jTextField1.getText() + "';");
            SendOtpMail(email, otp);
            JOptionPane.showMessageDialog(this, "OTP Code is send!", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "An error occurred", e);
            JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel main;
    // End of variables declaration//GEN-END:variables
}

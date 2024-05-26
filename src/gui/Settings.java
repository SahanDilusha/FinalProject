/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.time.Year;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.MySQL;
import model.SendEmail;

/**
 *
 * @author sahan
 */
public class Settings extends javax.swing.JDialog {

    Preferences preferences = Preferences.userRoot();

    private int at1 = 0;
    private int at2 = 0;
    private int at3 = 0;

    /**
     * Creates new form Settings
     */
    private final String generateOTP() {
        // Generate a random 6-digit number
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999

        // Format the OTP as a string
        String otpString = String.valueOf(otp);

        return otpString;
    }

    private final void ShowLogin() {
        this.dispose();
        new Login().setVisible(true);
    }

    private final void SendOTP(String subject, String text, String email) {

        String otp = generateOTP();

        try {
            MySQL.execute("UPDATE `users` SET `us_otp` = '" + otp + "' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");
            SendEmail.send(email, "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <title>" + subject + "</title>\n"
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
                    + "            <h1>" + subject + "</h1>\n"
                    + "        </div>\n"
                    + "        <div class=\"content\">\n"
                    + "            <h2>Hello,</h2>\n"
                    + "            <p>We received a request to " + text + ". To proceed, please use the following one-time password (OTP). This OTP is valid for the next 10 minutes.</p>\n"
                    + "            <div class=\"otp\">" + otp + "</div>\n"
                    + "            <p>If you did not request this change, please ignore this email or contact support immediately.</p>\n"
                    + "            <p>Thank you,</p>\n"
                    + "            <p>The Supermarket Management System Team</p>\n"
                    + "        </div>\n"
                    + "        <div class=\"footer\">\n"
                    + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</body>\n"
                    + "</html>", subject);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

        }

    }

    public Settings(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        jTextField1.setText(preferences.get("branch", "default_value"));
        jTextField7.setText(preferences.get("user_type_name", "default_value"));
        jTextField6.setText(preferences.get("username", "default_value"));
        jTextField2.setText(preferences.get("first_name", "default_value"));
        jTextField3.setText(preferences.get("first_last", "default_value"));
        jTextField4.setText(preferences.get("mobile", "default_value"));
        jTextField5.setText(preferences.get("email", "default_value"));
        jTextField8.setText(preferences.get("email", "default_value"));

        if (preferences.get("two_facter", "default_value").equals("1")) {
            jLabel18.setText(" Two factor authentication is enabled!");
            jLabel18.setBackground(new Color(153, 255, 204));
            jButton7.setText("Disabled");
            jButton7.setBackground(new Color(255, 102, 102));
        } else {
            jLabel18.setText(" Two factor authentication is disabled!");
            jLabel18.setBackground(new Color(255, 102, 102));
            jButton7.setText("Enable");
            jButton7.setBackground(new Color(0, 255, 153));
        }

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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField10 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTextField11 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username ");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("First Name");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Last Name");

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mobile");

        jTextField5.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");

        jButton1.setBackground(new java.awt.Color(0, 255, 153));
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Branch Name :");

        jTextField6.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("User Type :");

        jTextField7.setEditable(false);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("  ");
        jLabel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BOTTOM));

        jButton8.setText("Update Profile Image");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Accsept: jpg,jpeg,png and 2mb size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jTextField5)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField4))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField3))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField2))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Account Details", jPanel2);

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Old password");

        jPasswordField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("New password");

        jPasswordField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Confirm new password");

        jButton2.setBackground(new java.awt.Color(0, 255, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Update Password");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setText("Make sure it's at least 15 characters OR at least 8 characters including a number, a special character and a lowercase letter.");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox1.setText("Show Password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField3, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                            .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jButton2))
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(12, 12, 12)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Change password", jPanel5);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Old Email");

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("New Email -");

        jButton4.setText("Send OTP Code");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Enter OTP -");

        jButton5.setBackground(new java.awt.Color(0, 255, 153));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setText("Update Email");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Change Email", jPanel6);

        jLabel18.setBackground(new java.awt.Color(153, 255, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText(" Two factor authentication is enabled!");
        jLabel18.setOpaque(true);

        jButton6.setText("Send OTP Code");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Enter OTP");

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setText("Disable");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextField11)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Two factor authentication", jPanel7);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Security", jPanel3);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Theme:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dark Theme", "Light Theme", " " }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(443, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap(300, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Genaral", jPanel8);

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jButton3.setBackground(new java.awt.Color(204, 0, 51));
        jButton3.setText("X");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Food City");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon-sm.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Settings");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTextField4.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Please Enter Customer Mobile", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!jTextField4.getText().matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid Customer Mobile", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField4.getText().equals(preferences.get("mobile", "default_value"))) {
            JOptionPane.showMessageDialog(this, "Same data entry!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            int option = JOptionPane.showConfirmDialog(this, "Update your mobile number?", "WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    MySQL.execute("UPDATE `users` SET `us_mobile` = '" + jTextField4.getText() + "' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");
                    preferences.put("mobile", jTextField4.getText());
                    jTextField4.setText(preferences.get("mobile", "default_value"));
                    SendEmail.send(preferences.get("email", "default_value"), "<!DOCTYPE html>\n"
                            + "<html lang=\"en\">\n"
                            + "<head>\n"
                            + "    <meta charset=\"UTF-8\">\n"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    <title>Mobile Number Change Notification</title>\n"
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
                            + "        .new-number {\n"
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
                            + "            <h1>Mobile Number Change Notification</h1>\n"
                            + "        </div>\n"
                            + "        <div class=\"content\">\n"
                            + "            <h2>Hello,</h2>\n"
                            + "            <p>We wanted to let you know that your mobile number has been successfully updated in our system. Your new mobile number is:</p>\n"
                            + "            <div class=\"new-number\">" + preferences.get("mobile", "default_value") + "</div>\n"
                            + "            <p>If you did not request this change, please contact our support team immediately.</p>\n"
                            + "            <p>Thank you,</p>\n"
                            + "            <p>The Supermarket Management System Team</p>\n"
                            + "        </div>\n"
                            + "        <div class=\"footer\">\n"
                            + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                            + "        </div>\n"
                            + "    </div>\n"
                            + "</body>\n"
                            + "</html>", "Mobile Number Change Notification");
                    JOptionPane.showMessageDialog(this, "Your mobile number is updated!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (at3 == 3) {

            JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

            try {
                MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }

            ShowLogin();

        } else if (String.valueOf(jPasswordField1.getPassword()).isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter your old password!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!String.valueOf(jPasswordField1.getPassword()).matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
            JOptionPane.showMessageDialog(this, "invalid password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(jPasswordField2.getPassword()).isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter your new password!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!String.valueOf(jPasswordField2.getPassword()).matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
            JOptionPane.showMessageDialog(this, "invalid password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(jPasswordField3.getPassword()).isBlank()) {
            JOptionPane.showMessageDialog(this, "Please confirm your new password!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!String.valueOf(jPasswordField3.getPassword()).matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,45}$")) {
            JOptionPane.showMessageDialog(this, "invalid password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        } else if (!String.valueOf(jPasswordField3.getPassword()).equals(String.valueOf(jPasswordField2.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Please check your new password!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            int option = JOptionPane.showConfirmDialog(this, "Update your password?", "WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                try {

                    ResultSet resultset = MySQL.execute("SELECT `us_id` FROM `users` WHERE `us_password` = '" + String.valueOf(jPasswordField1.getPassword()) + "' AND `us_id` = '" + preferences.get("id", "default_value") + "'");

                    if (resultset.next()) {
                        MySQL.execute("UPDATE `users` SET `us_password` = '" + String.valueOf(jPasswordField3.getPassword()) + "' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");
                        JOptionPane.showMessageDialog(this, "Your password is updated! Please relogin to your account!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                        SendEmail.send(preferences.get("email", "default_value"), "<!DOCTYPE html>\n"
                                + "<html lang=\"en\">\n"
                                + "<head>\n"
                                + "    <meta charset=\"UTF-8\">\n"
                                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                + "    <title>Password Change Notification</title>\n"
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
                                + "        .new-password {\n"
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
                                + "            <h1>Password Change Notification</h1>\n"
                                + "        </div>\n"
                                + "        <div class=\"content\">\n"
                                + "            <h2>Hello,</h2>\n"
                                + "            <p>We wanted to let you know that your password has been successfully changed. If you did not make this change, please contact our support team immediately.</p>\n"
                                + "            <p>Thank you,</p>\n"
                                + "            <p>The Supermarket Management System Team</p>\n"
                                + "        </div>\n"
                                + "        <div class=\"footer\">\n"
                                + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                                + "        </div>\n"
                                + "    </div>\n"
                                + "</body>\n"
                                + "</html>", "Password Change Notification");
                        this.dispose();
                        new Login().setVisible(true);
                    } else {

                        if (at3 == 3) {

                            JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

                            MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");

                            ShowLogin();

                        } else {
                            at3++;
                            JOptionPane.showMessageDialog(this, "invalid your old password!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at3) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

                }
            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {

            jPasswordField1.setEchoChar((char) 0);
            jPasswordField2.setEchoChar((char) 0);
            jPasswordField3.setEchoChar((char) 0);

        } else {
            jPasswordField1.setEchoChar('\u25cf');
            jPasswordField2.setEchoChar('\u25cf');
            jPasswordField3.setEchoChar('\u25cf');
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jTextField9.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Pease enter your new email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!jTextField9.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Invalid Customer Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField9.getText().equals(preferences.get("email", "default_value"))) {
            JOptionPane.showMessageDialog(this, "This email is already used!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultset = MySQL.execute("SELECT `us_id` FROM `users` WHERE `us_email` = '" + jTextField9.getText() + "'");

                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, "This email is already used!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    SendOTP("Email Change OTP", "change your email address", jTextField9.getText());
                    JOptionPane.showMessageDialog(this, "Your OTP Code has been sent!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (at1 == 3) {

            JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

            try {
                MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }

            ShowLogin();

        } else {

            if (jTextField9.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Pease enter your new email", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!jTextField9.getText().matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                    + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
                JOptionPane.showMessageDialog(this, "Invalid Customer Email", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (jTextField9.getText().equals(preferences.get("email", "default_value"))) {
                JOptionPane.showMessageDialog(this, "This email is already used!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (jTextField10.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Pease Enter your OTP Code!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    ResultSet resultset = MySQL.execute("SELECT `us_id` FROM `users` WHERE `us_email` = '" + jTextField9.getText() + "'");

                    if (resultset.next()) {
                        JOptionPane.showMessageDialog(this, "This email is already used!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        ResultSet resultset2 = MySQL.execute("SELECT `us_id` FROM `users` WHERE `us_id` = '" + preferences.get("id", "default_value") + "' AND `us_otp` = '" + jTextField10.getText() + "'");

                        if (resultset2.next()) {
                            MySQL.execute("UPDATE `users` SET `us_email` = '" + jTextField9.getText() + "', `us_otp` = '" + generateOTP() + "' WHERE `us_id` = '" + preferences.get("id", "default_value") + "'");
                            preferences.put("email", jTextField9.getText());
                            jTextField8.setText(preferences.get("email", "default_value"));
                            jTextField5.setText(preferences.get("email", "default_value"));
                            jTextField9.setText("");
                            jTextField10.setText("");
                            SendEmail.send(preferences.get("email", "default_value"), "<!DOCTYPE html>\n"
                                    + "<html lang=\"en\">\n"
                                    + "<head>\n"
                                    + "    <meta charset=\"UTF-8\">\n"
                                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                    + "    <title>Email Address Updated</title>\n"
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
                                    + "            <h1>Email Address Updated</h1>\n"
                                    + "        </div>\n"
                                    + "        <div class=\"content\">\n"
                                    + "            <h2>Hello,</h2>\n"
                                    + "            <p>Your email address has been successfully updated to <strong>" + preferences.get("email", "default_value") + "</strong>.</p>\n"
                                    + "            <p>If you did not make this change, please contact support immediately.</p>\n"
                                    + "            <p>Thank you for using our services.</p>\n"
                                    + "        </div>\n"
                                    + "        <div class=\"footer\">\n"
                                    + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                                    + "        </div>\n"
                                    + "    </div>\n"
                                    + "</body>\n"
                                    + "</html>", "Email Address Updated");
                            JOptionPane.showMessageDialog(this, "Your email is updated!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                        } else {

                            if (at1 == 3) {

                                JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

                                MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");

                                ShowLogin();

                            } else {
                                at1++;
                                JOptionPane.showMessageDialog(this, "Invalid OTP!", "Warning", JOptionPane.WARNING_MESSAGE);
                                JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at1) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

                }

            }

        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        SendOTP("Two-Factor Authentication Update", "update Two-factor authentication settings", preferences.get("email", "default_value"));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (at2 == 3) {

            JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);

            try {
                MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }

            ShowLogin();

        } else if (jTextField11.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Pease Enter your OTP Code!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultset = MySQL.execute("SELECT `us_id` FROM `users` WHERE `us_id` = '" + preferences.get("id", "default_value") + "' AND `us_otp` = '" + jTextField11.getText() + "'");

                if (resultset.next()) {

                    MySQL.execute("UPDATE `users` SET `us_otp` = '" + generateOTP() + "' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");

                    if (preferences.get("two_facter", "default_value").equals("2")) {
                        MySQL.execute("UPDATE `users` SET `us_two_facter` = '1' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");
                        preferences.put("two_facter", "1");
                        jLabel18.setText(" Two factor authentication is enabled!");
                        jLabel18.setBackground(new Color(153, 255, 204));
                        jButton7.setText("Disabled");
                        jButton7.setBackground(new Color(255, 102, 102));
                        SendEmail.send(preferences.get("email", "default_value"), "<!DOCTYPE html>\n"
                                + "<html lang=\"en\">\n"
                                + "<head>\n"
                                + "    <meta charset=\"UTF-8\">\n"
                                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                + "    <title>Two-Factor Authentication Enabled</title>\n"
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
                                + "            <h1>Two-Factor Authentication Enabled</h1>\n"
                                + "        </div>\n"
                                + "        <div class=\"content\">\n"
                                + "            <h2>Hello,</h2>\n"
                                + "            <p>Your Two-Factor Authentication (2FA) has been successfully enabled.</p>\n"
                                + "            <p>This extra layer of security will help protect your account.</p>\n"
                                + "            <p>If you did not enable 2FA, please contact support immediately.</p>\n"
                                + "            <p>Thank you for enhancing your account security.</p>\n"
                                + "        </div>\n"
                                + "        <div class=\"footer\">\n"
                                + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                                + "        </div>\n"
                                + "    </div>\n"
                                + "</body>\n"
                                + "</html>", "Two-Factor Authentication Enabled");
                    } else {
                        MySQL.execute("UPDATE `users` SET `us_two_facter` = '2' WHERE `us_id` = '" + preferences.get("id", "default_value") + "';");
                        preferences.put("two_facter", "2");
                        jLabel18.setText(" Two factor authentication is disabled!");
                        jLabel18.setBackground(new Color(255, 102, 102));
                        jButton7.setText("Enable");
                        jButton7.setBackground(new Color(0, 255, 153));
                        SendEmail.send(preferences.get("email", "default_value"), "<!DOCTYPE html>\n"
                                + "<html lang=\"en\">\n"
                                + "<head>\n"
                                + "    <meta charset=\"UTF-8\">\n"
                                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                + "    <title>Two-Factor Authentication Disabled</title>\n"
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
                                + "            <h1>Two-Factor Authentication Disabled</h1>\n"
                                + "        </div>\n"
                                + "        <div class=\"content\">\n"
                                + "            <h2>Hello,</h2>\n"
                                + "            <p>Your Two-Factor Authentication has been successfully disabled.</p>\n"
                                + "            <p>If you did not make this change, please contact support immediately.</p>\n"
                                + "            <p>Thank you for using our services.</p>\n"
                                + "        </div>\n"
                                + "        <div class=\"footer\">\n"
                                + "            <p>&copy; " + Year.now() + " Supermarket Management System. All rights reserved.</p>\n"
                                + "        </div>\n"
                                + "    </div>\n"
                                + "</body>\n"
                                + "</html>", "Two-Factor Authentication Disabled");
                    }

                    jTextField11.setText("");

                } else {
                    if (at2 == 3) {

                        JOptionPane.showMessageDialog(this, "attempt are over! Your account is locked!", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                        MySQL.execute("UPDATE `users` SET `users`.`us_status` = '2' WHERE `users`.`us_id` = '" + preferences.get("id", "default_value") + "';");

                        ShowLogin();

                    } else {
                        at2++;
                        JOptionPane.showMessageDialog(this, "Invalid OTP!", "Warning", JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(this, "You only have " + String.valueOf(3 - at2) + " attempts left", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Please Check your connection and try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            }

        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();

            long fileSize = selectedFile.length();
            long maxSize = 2 * 1024 * 1024;

            if (fileSize <= maxSize) {

                byte[] resizedImageBytes;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    int width = image.getWidth();
                    int height = image.getHeight();

                    if (width == 188 && height == 198) {
                        ImageIO.write(image, "png", baos);
                        resizedImageBytes = baos.toByteArray();
                    } else {

                        Image resizedImage = image.getScaledInstance(188, 198, Image.SCALE_DEFAULT);

                        BufferedImage resizedBufferedImage = new BufferedImage(188, 198, BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = resizedBufferedImage.createGraphics();
                        g2d.drawImage(resizedImage, 0, 0, null);
                        g2d.dispose();

                        ImageIO.write(resizedBufferedImage, "png", baos);
                        resizedImageBytes = baos.toByteArray();
                    }
                    
                    

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Selected file exceeds the maximum allowed size of 2MB.", "WARNING", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Settings dialog = new Settings(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}

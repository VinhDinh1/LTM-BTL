/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class SendFile extends javax.swing.JFrame {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String myusername;
    private String host;
    private int port;
    private StringTokenizer st;
    private String sendTo;
    private String file;
    private MainForm main;
    private Object chooser;
    
    public boolean prepare(String u, String h, int p, MainForm m){
        this.host = h;
        this.myusername = u;
        this.port = p;
        this.main = m;
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            //  Format: CMD_SHARINGSOCKET [sender]
            String format = "CMD_SHARINGSOCKET "+ myusername;
            dos.writeUTF(format);
            System.out.println(format);
            
            new Thread(new SendFileThread(this)).start();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    public SendFile() {
        initComponents();
        MyInit();
        progressbar.setVisible(false);
    }
    
    void MyInit(){
         setLocationRelativeTo(null);
     }
    
    class SendFileThread implements Runnable{
        private SendFile form;
        public SendFileThread(SendFile form){
            this.form = form;
        }
        
        private void closeMe(){
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("[closeMe]: "+e.getMessage());
            }
            dispose();
        }
        
        @Override
        public void run() {
            try {
                while(!Thread.currentThread().isInterrupted()){
                    String data = dis.readUTF();  // ?????c n???i dung c???a d??? li???u ???????c nh???n t??? Server
                    st = new StringTokenizer(data);
                    String cmd = st.nextToken();  //  L???y ch??? ?????u ti??n t??? d??? li???u
                    switch(cmd){
                        case "CMD_RECEIVE_FILE_ERROR":  // ?????nh d???ng: CMD_RECEIVE_FILE_ERROR [Message]
                            String msg = "";
                            while(st.hasMoreTokens()){
                                msg = msg+" "+st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(SendFile.this, msg, "L???i", JOptionPane.ERROR_MESSAGE);
                            this.closeMe();
                            break;
                            
                        case "CMD_RECEIVE_FILE_ACCEPT":  // ?????nh d???ng: CMD_RECEIVE_FILE_ACCEPT [Message]
                            /*  B???t ?????u kh???i ?????ng thread File ????nh k??m   */

                            new Thread(new SendingFileThread(socket, file, sendTo, myusername, SendFile.this)).start();
                            break;
                            
                        case "CMD_SENDFILEERROR":
                            String emsg = "";
                            while(st.hasMoreTokens()){
                                emsg = emsg +" "+ st.nextToken();
                            }                                                     
                            System.out.println(emsg);                            
                            JOptionPane.showMessageDialog(SendFile.this, emsg,"L???i", JOptionPane.ERROR_MESSAGE);
                            form.updateAttachment(false);
                            form.disableGUI(false);
                            form.updateBtn("G???i File");
                            break;
                        
                        case "CMD_SENDFILERESPONSE":
                            /*
                            Format: CMD_SENDFILERESPONSE [username] [Message]
                            */
                            String rReceiver = st.nextToken();
                            String rMsg = "";
                            while(st.hasMoreTokens()){
                                rMsg = rMsg+" "+st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(SendFile.this, rMsg, "L???i", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        progressbar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Ch???n file:");

        jLabel2.setText("G???i ?????n:");

        jButton1.setText("Ch???n");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("G???i");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        progressbar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                            .addComponent(jTextField1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        showOpenDialog();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        sendTo = jTextField2.getText();
        file = jTextField1.getText();

        if((sendTo.length() > 0) && (file.length() > 0)){
            try {
                // Format: CMD_SEND_FILE_XD [sender] [receiver] [filename]
                jTextField1.setText("");
                String fname = getThisFilename(file);
                String format = "CMD_SEND_FILE_XD "+myusername+" "+sendTo+" "+fname;
                dos.writeUTF(format);
                System.out.println(format);
                updateBtn("??ang g???i ??i...");
                jButton2.setEnabled(false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Kh??ng ????? tr???ng.!","L???i", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    public void showOpenDialog(){
        JFileChooser chooser = new JFileChooser();
        int intval = chooser.showOpenDialog(this);
        if(intval == chooser.APPROVE_OPTION){
            jTextField1.setText(chooser.getSelectedFile().toString());
        }else{
            jTextField1.setText("");
        }
    }
    
    
    /*   Ph????ng th???c n??y s??? disable/enabled t???t c??? GUI   */
    public void disableGUI(boolean d){
        if(d){ // Disable
           jTextField2.setEditable(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jTextField1.setEditable(false);
            progressbar.setVisible(true);
        } else { // Enable
           jTextField2.setEditable(true);
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);
            jTextField1.setEditable(true);
            progressbar.setVisible(false);
        }
    }
    
    
    /*  Nh???n Form Title   */
    public void setMyTitle(String s){
        setTitle(s);
    }
    
    /*   H??m n??y s??? ????ng Form  */
    protected void closeThis(){
        dispose();
    }
    
    /*  H??m n??y s??? nh???n Filename */
    public String getThisFilename(String path){
        File p = new File(path);
        String fname = p.getName();
        return fname.replace(" ", "_");
    }
    
    /*  C???p nh???t file ????nh k??m   */
    public void updateAttachment(boolean b){
        main.updateAttachment(b);
    }
    
    /*  C???p nh???t n??t btnSendFile text  */
    public void updateBtn(String str){
        jButton2.setText(str);
    }
    
    /**
     * Update progress bar
     * @param val 
     */
    public void updateProgress(int val){
        progressbar.setValue(val);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SendFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SendFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SendFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SendFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SendFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JProgressBar progressbar;
    // End of variables declaration//GEN-END:variables
}

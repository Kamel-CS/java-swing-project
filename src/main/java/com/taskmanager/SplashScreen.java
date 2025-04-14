package com.taskmanager;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SplashScreen extends JWindow {
    private static final int DISPLAY_TIME = 3000; // 3 seconds
    private Image splashImage;
    
    public SplashScreen() {
        try {
            splashImage = ImageIO.read(getClass().getResource("/images/task_manager_splash.png"));
            // Set window size to match image size
            setSize(800, 600);
            setLocationRelativeTo(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Create content panel that just shows the image
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (splashImage != null) {
                    g.drawImage(splashImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        setContentPane(content);
    }
    
    public void showSplash() {
        setVisible(true);
        
        // Close splash after delay
        Timer timer = new Timer(DISPLAY_TIME, e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            });
        });
        timer.setRepeats(false);
        timer.start();
    }
} 
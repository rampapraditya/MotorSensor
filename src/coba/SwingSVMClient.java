/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coba;

/**
 *
 * @author RAMPA
 */
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingSVMClient {
    private static JTextField[] featureFields;
    private static JLabel resultLabel;

    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("SVM Prediction Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2));

        // Membuat teks bidang untuk fitur
        featureFields = new JTextField[4];
        for (int i = 0; i < featureFields.length; i++) {
            frame.add(new JLabel("Feature " + (i + 1) + ":"));
            featureFields[i] = new JTextField();
            frame.add(featureFields[i]);
        }

        // Tombol prediksi
        JButton predictButton = new JButton("Predict");
        frame.add(predictButton);

        // Label untuk hasil
        resultLabel = new JLabel("Prediction result will appear here");
        frame.add(resultLabel);

        // Action listener untuk tombol prediksi
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] features = new double[4];
                for (int i = 0; i < featureFields.length; i++) {
                    features[i] = Double.parseDouble(featureFields[i].getText());
                }
                String prediction = sendPredictionRequest(features);
                resultLabel.setText("Prediction: " + prediction);
            }
        });

        // Menampilkan frame
        frame.setVisible(true);
    }

    private static String sendPredictionRequest(double[] features) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:5000/predict");
            JSONObject json = new JSONObject();
            json.put("features", features);
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONObject responseObject = new JSONObject(jsonResponse);
            return responseObject.getString("prediction");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

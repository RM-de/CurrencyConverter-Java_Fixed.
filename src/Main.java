import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import com.google.gson.Gson;
import org.w3c.dom.css.RGBColor;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class Main {

    static Map<String, Double> wechselkurse = new HashMap<>();
    private static boolean istLive = false;
    private static String zeitstempel = " 00.00.0000 - 00.00 ";
    static String[] Währungen = {" EUR", "USD", "AUD", "BRL", "CAD", "CHF","CNY", "CZK", "DKK", "GBP", "HKD", "HUF", "IDR", "ILS",
                 "INR", "ISK", "JPY", "KRW", "MXN", "MYR", "NOk", "NZD", "PHP", "PLN", "RON", "SEK", "SGD", "THB", "TRY", "ZAR" };
    static JFrame frame;
    static JLabel text;
    static JTextField betragField;
    static JTextField ergebnisField;
    static JTextField bemerkungsField;
    static JButton button;


    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialisiereKurs();
        openUI();
    }

    public static void initialisiereKurs() {
        try {
            URL url = new URL("https://api.frankfurter.app/latest?from=EUR");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject rates = jsonObject.getAsJsonObject("rates");

            for (String key : rates.keySet()) {
                double kurs = rates.get(key).getAsDouble();
                wechselkurse.put("EUR_" + key, kurs);
                wechselkurse.put(key + "_EUR", 1.0 / kurs);
            }

            istLive = true;
            java.time.LocalDateTime jetzt = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formater = java.time.format.DateTimeFormatter.ofPattern(" dd.MM.yyyy - HH:mm ");
            zeitstempel = jetzt.format(formater);
            speichereKurseLokal();
            System.out.println("[System] Verbindung erfolgreich: " + wechselkurse.size() / 2  + " Handelspaare geladen.");
        } catch (Exception e) {

            istLive = false;
            ladeLokalenCache();
            if (wechselkurse.isEmpty()) {
                wechselkurse.put("EUR_USD", 1.08);
                wechselkurse.put("USD_EUR", 0.92);
            }
        }
    }

    public static void openUI() {

        frame = new JFrame("Währungsrechner!");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 40, 5, 40);
        Dimension feldGroße = new Dimension(450, 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text = new JLabel("Währung Rechnen");
        text.setForeground(Color.WHITE);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setFont(new Font("Arial", Font.BOLD, 20));
        text.setPreferredSize(feldGroße);
        Color denimBlue = new Color(5, 45, 60);
        frame.getContentPane().setBackground(denimBlue);
        ((JComponent)frame.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.WHITE,1));

        JComboBox<String> box1 = new JComboBox<>(Währungen);
        box1.setPreferredSize(feldGroße);
        box1.setMaximumRowCount(20);
        box1.setFont(new Font("Arial" , Font.PLAIN,14));

        betragField = new JTextField();
        betragField.setPreferredSize(feldGroße);
        betragField.setFont(new Font("Arial", Font.PLAIN, 16));
        Border linie = BorderFactory.createLineBorder(Color.WHITE,1);
        Border abstand = BorderFactory.createEmptyBorder(0,7,0,0);
        betragField.setBorder(BorderFactory.createCompoundBorder(linie, abstand));

        JComboBox<String> box2 = new JComboBox<>(Währungen);
        box2.setPreferredSize(feldGroße);
        box2.setMaximumRowCount(20);
        box2.setFont(new Font("Arial" , Font.PLAIN,14));
        box2.setBorder(null);

        JTextField ergebnisField = new JTextField(" Ergebnis:");
        ergebnisField.setFont(new Font("Arial", Font.PLAIN, 16));
        ergebnisField.setPreferredSize(feldGroße);
        ergebnisField.setEditable(false);
        ergebnisField.setBackground(Color.WHITE);

        bemerkungsField = new JTextField(" Bemerkungen:");
        bemerkungsField.setFont(new Font("Arial", Font.PLAIN, 15));
        bemerkungsField.setPreferredSize(feldGroße);
        bemerkungsField.setEditable(false);
        bemerkungsField.setBackground(Color.WHITE);

        button = new JButton("Klicken");
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(feldGroße);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = betragField.getText();
                    double zahl = Double.parseDouble(input);

                    String von = (String) box1.getSelectedItem();
                    String zu = (String) box2.getSelectedItem();

                    double kurs = getWechselkurs(von, zu);
                    double ergebnis = zahl * kurs;
                    betragField.requestFocusInWindow();
                    String klickZeit = new java.text.SimpleDateFormat(" dd.MM.yyyy - HH:mm ").format(new java.util.Date());
                    String statusAnzeige = istLive ? " ( Live ) " : " ( nicht aktuell ) ";

                    if (istLive) {
                        bemerkungsField.setText(" Stand: " + klickZeit + " Uhr ");
                    } else {
                        bemerkungsField.setText(" Stand " +  zeitstempel + " Uhr ( Du bist offline )");
                    }
                    ergebnisField.setText(String.format(" %.2f ", ergebnis) + " " + zu + " " + statusAnzeige);
                    System.out.println(" Klick um : " + klickZeit + " Ergebnis berechnet!");
                    Color anzeigeFarbe = istLive ? new Color(0, 150, 0) : Color.RED;
                    ergebnisField.setForeground(anzeigeFarbe);
                    bemerkungsField.setForeground(anzeigeFarbe);

                } catch (NumberFormatException ex) {

                    ergebnisField.setText(" Bitte geben Sie nur Zahlen ein! ");
                    betragField.setText("");
                    betragField.requestFocusInWindow();
                    ergebnisField.setForeground(Color.RED);

                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 45, 5, 45);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(text, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        text.setHorizontalAlignment(JLabel.CENTER);
        gbc.insets = new Insets(5, 45, 5, 45);
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(box1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(betragField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(box2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(ergebnisField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(bemerkungsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 45, 60, 45);
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        frame.add(button, gbc);

        frame.pack();
        frame.setLocationRelativeTo(null);
        betragField.requestFocusInWindow();
        frame.setVisible(true);
    }

    public static double getWechselkurs(String von, String zu) {
        if (von.equals(zu))
            return 1.0;
        String direktkey = von + "_" + zu;
        if (wechselkurse.containsKey(direktkey)) {
            return wechselkurse.get(direktkey);
        }
        String vonZuEur = von + "_EUR";
        String eurZuZiel = "EUR_" + zu;
        if (wechselkurse.containsKey(vonZuEur) &&
                wechselkurse.containsKey(eurZuZiel)) {
          return  wechselkurse.get(vonZuEur) * wechselkurse.get(eurZuZiel);
        }
        return 1.0;
    }

    private static void speichereKurseLokal() {

        try (java.io.FileWriter writer = new java.io.FileWriter(" kurse_cache.json ")) {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            com.google.gson.JsonObject data = new com.google.gson.JsonObject();
            data.addProperty(" zeit ", zeitstempel);
            data.add(" kurse ", gson.toJsonTree(wechselkurse));
            gson.toJson(data, writer);

        } catch (Exception e) {

            System.out.println(" Fehler beim Speichern: " + e.getMessage());
        }
    }
    private static void ladeLokalenCache() {

        try (java.io.FileReader reader = new java.io.FileReader(" kurse_cache.json ")) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            com.google.gson.JsonObject data = com.google.gson.JsonParser.parseReader(reader).getAsJsonObject();

            if (data.has(" zeit ")) {
                zeitstempel = data.get(" zeit ").getAsString();
            }

            if (data.has(" kurse ")) {
                com.google.gson.JsonObject kurseJson = data.getAsJsonObject(" kurse ");
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.HashMap<String, Double>>() {
                }.getType();

                wechselkurse = gson.fromJson(kurseJson, type);
            }
            System.out.println(" Cache erfolgreich geladen! Stand von: " + zeitstempel);

        } catch (Exception e) {

            System.out.println(" Fehler beim Laden des Caches: " + e.getMessage());
            if (zeitstempel == null || zeitstempel.isEmpty());  {
                zeitstempel = " unbekannt ";
            }
        }
    }
}
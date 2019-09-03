package stefanini.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VerificarCpf {

    private static VerificarCpf uniqueInstance;

    private VerificarCpf() {
    }

    public static synchronized VerificarCpf getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new VerificarCpf();

        return uniqueInstance;
    }

    public boolean verificarCpfValido(String cpf) {

        try {
            URL url = new URL("https://user-info.herokuapp.com/users/" + cpf);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                return false;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            String response = "";

            while ((inputLine = in.readLine()) != null) {
                response = inputLine;
            }
            in.close();

            return !response.contains("UNABLE_TO_VOTE");

        } catch (Exception ex) {
            return false;
        }

    }

}

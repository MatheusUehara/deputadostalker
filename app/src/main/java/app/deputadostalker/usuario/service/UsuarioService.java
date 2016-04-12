package app.deputadostalker.usuario.service;

/**
 * Created by Matheus Uehara on 07/03/2016.
 */
public class UsuarioService {

    private static final UsuarioService instance = new UsuarioService();

    private UsuarioService() {
        super();
    }

    public static UsuarioService getInstance(){
        return instance;
    }

    public static boolean isEmailValid(String email) {
        //TODO change for your own logic
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO change for your own logic
        //Não seria melhor com 6?
        return password.length() > 4;
    }
}

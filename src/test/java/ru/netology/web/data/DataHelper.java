package ru.netology.web.data;

public class DataHelper {

    //Valid User 1
    private static final String[] userDataOne = {"vasya", "qwerty123", "12345"}; //login, password, verification code
    private static final String[][] cardsOne = {
            {"5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0", "10000"},   //number, id, initial balance
            {"5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d", "10000"}    //number, id, initial balance
    };

    public static class AuthInfo {
        private String login;
        private String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo(userDataOne[0], userDataOne[1]);
    }

    public static class VerificationCode {
        private String code;

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode(userDataOne[2]);
    }

    public static String getCardId(int number) {
        return cardsOne[number - 1][1];
    }

    public static String getCardNumber(int number) {
        return cardsOne[number - 1][0];
    }

    public static String getCardNumberById(String id) {
        for (String[] card : cardsOne) {
            if (card[1].equals(id)) return card[0];
        }
        return null;
    }
}

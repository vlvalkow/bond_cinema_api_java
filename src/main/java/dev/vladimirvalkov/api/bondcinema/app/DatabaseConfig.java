package dev.vladimirvalkov.api.bondcinema.app;

public record DatabaseConfig(String url, String user, String pass) {
    public static DatabaseConfig fromEnv() {
        String url = getenvOrNull("DB_URL");
        String user = getenvOrNull("DB_USER");
        String pass = getenvOrNull("DB_PASS");
        return new DatabaseConfig(url, user, pass);
    }

    private static String getenvOrNull(String key) {
        String val = System.getenv(key);
        return (val != null && !val.isBlank()) ? val : null;
    }
}

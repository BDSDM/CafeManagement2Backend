package societedestin.cafemanagement2.service;

public interface PasswordResetService {
    void createPasswordResetToken(String email);
    boolean resetPassword(String token, String newPassword);
}

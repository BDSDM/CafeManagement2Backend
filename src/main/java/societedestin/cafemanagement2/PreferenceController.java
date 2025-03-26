package societedestin.cafemanagement2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class PreferenceController {

    // Endpoint pour sauvegarder la préférence de couleur dans un cookie
    @PostMapping("/api/set-color")
    public String setColorPreference(@RequestParam("color") String color, HttpServletResponse response) {
        try {
            // Vérifiez que la couleur n'est pas nulle ou vide
            if (color == null || color.trim().isEmpty()) {
                throw new IllegalArgumentException("La couleur ne peut pas être vide");
            }

            // Créez un cookie pour la couleur préférée
            Cookie colorCookie = new Cookie("preferredColor", color);
            colorCookie.setMaxAge(20); // Durée de vie de 20 secondes
            colorCookie.setPath("/");
            colorCookie.setHttpOnly(true); // Sécuriser le cookie
            colorCookie.setSecure(false); // Utilisez false pour le développement local
            response.addCookie(colorCookie);

            // Retournez un message de succès
            return "Couleur préférée sauvegardée: " + color;
        } catch (IllegalArgumentException e) {
            // Log de l'exception pour le débogage
            System.err.println("Erreur: " + e.getMessage());
            return "Erreur: " + e.getMessage();
        } catch (Exception e) {
            // Log de l'exception pour le débogage
            e.printStackTrace(); // Ou utilisez un logger pour enregistrer l'erreur
            return "Erreur lors de la sauvegarde de la couleur: " + e.getMessage();
        }
    }


    // Endpoint pour lire la préférence de couleur à partir du cookie
    @GetMapping("/api/get-color")
    public String getColorPreference(@CookieValue(value = "preferredColor", defaultValue = "white") String preferredColor) {
        return preferredColor;
    }
}

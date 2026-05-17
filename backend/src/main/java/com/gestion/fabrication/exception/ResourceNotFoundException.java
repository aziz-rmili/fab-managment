package com.gestion.fabrication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception levée quand une ressource n'existe pas en base.
 * Spring renverra automatiquement un HTTP 404 Not Found.
 *
 * Cours du prof slide 30 :
 * "Si la méthode renvoie Etudiant alors utiliser les méthodes
 *  .orElseThrow(() -> new EmployeeNotFoundException(id))
 *  pour éviter les NullPointerException"
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

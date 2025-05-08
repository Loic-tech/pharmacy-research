package com.search.pharmacy.service;

import com.search.pharmacy.ws.model.OrderDTO;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenStorageService {
  // Utilisation d'une Map pour stocker les commandes par token
  // Dans un environnement de production, vous pourriez utiliser Redis ou une autre solution
  private final Map<String, OrderDTO> orderStorage = new ConcurrentHashMap<>();

  // Expiration au bout de 1 heure
  private final Map<String, Instant> expirationTimes = new ConcurrentHashMap<>();
  private final long EXPIRATION_MINUTES = 60;

  public void storeOrder(String token, OrderDTO order) {
    orderStorage.put(token, order);
    expirationTimes.put(token, Instant.now().plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES));
  }

  public OrderDTO retrieveOrder(String token) {
    // Vérifier si le token est expiré
    Instant expirationTime = expirationTimes.get(token);
    if (expirationTime == null || expirationTime.isBefore(Instant.now())) {
      orderStorage.remove(token);
      expirationTimes.remove(token);
      return null;
    }

    return orderStorage.get(token);
  }

  public void removeOrder(String token) {
    orderStorage.remove(token);
    expirationTimes.remove(token);
  }

  // Méthode qui pourrait être appelée périodiquement pour nettoyer les commandes expirées
  @Scheduled(fixedRate = 60 * 60 * 1000) // Toutes les heures
  public void cleanupExpiredOrders() {
    Instant now = Instant.now();
    List<String> expiredTokens =
        expirationTimes.entrySet().stream()
            .filter(entry -> entry.getValue().isBefore(now))
            .map(Map.Entry::getKey)
            .toList();

    expiredTokens.forEach(
        token -> {
          orderStorage.remove(token);
          expirationTimes.remove(token);
        });
  }
}

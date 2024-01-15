# Objectif
+ Ecrire un test présentant une recherche d'un hôtel avec des chambres de libre pour un lieu et une pédiode donnés.

# Entrée
+ Une ville:
  + code postal
  + nom
+ une période
  + une date d'arrivée 
  + une date de départ

# Sortie
+ Liste des hôtels avec des disponibilités

## Exemple
```json
{
  "hotels": [
    {
      "id": 1,
      "name": "Elegance Hotel",
      "address": "25 RUE DU LOUVRE, 75001, PARIS",
      "available_rooms": 10,
      "price": 150,
      "amenities": ["Free Wi-Fi", "Parking", "Complimentary Breakfast"]
    },
    {
      "id": 2,
      "name": "Charming Inn",
      "address": "21 RUE DU BOULOI, 75001, PARIS",
      "available_rooms": 5,
      "price": 120,
      "amenities": ["Free Wi-Fi", "Swimming Pool", "Room Service"]
    }
  ]
}
```


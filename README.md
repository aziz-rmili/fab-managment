# Gestion des Ordres de Fabrication

Application web complète de gestion des ordres de fabrication industrielle, développée dans le cadre du projet de fin de module Spring Boot / Angular.

---

## Description du projet

Cette application permet de gérer l'ensemble du cycle de fabrication :

- **Ordres de fabrication** : création, suivi et mise à jour de l'état des ordres (EN_ATTENTE → EN_COURS → TERMINE / ANNULE)
- **Produits** : gestion du catalogue produits avec suivi du stock et des fournisseurs
- **Machines** : gestion du parc machines avec suivi des états et des maintenances
- **Employés** : gestion des employés et de leurs affectations aux machines

---

## Technologies utilisées

### Back-end
| Technologie | Version | Rôle |
|---|---|---|
| Java | 17 | Langage principal |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Data JPA | 3.2.0 | Persistance des données (Hibernate) |
| Spring Validation | 3.2.0 | Validation des données |
| MySQL | 8.0 | Base de données relationnelle |
| ModelMapper | 3.2.2 | Conversion Entité ↔ DTO |
| SpringDoc OpenAPI | 2.3.0 | Documentation Swagger |
| Lombok | latest | Réduction du code répétitif |
| Maven | 3.x | Gestionnaire de dépendances |

### Front-end
| Technologie | Version | Rôle |
|---|---|---|
| Angular | 21.2 | Framework front-end |
| TypeScript | 5.9 | Langage principal |
| Bootstrap | 5.3 | Interface utilisateur |
| Bootstrap Icons | 1.13 | Icônes |
| Nginx | alpine | Serveur web (production) |

### Infrastructure
| Technologie | Rôle |
|---|---|
| Docker | Conteneurisation |
| Docker Compose | Orchestration locale des services |
| Kubernetes (GKE) | Déploiement cloud sur Google Kubernetes Engine |
| Google Container Registry | Stockage des images Docker |

---

## Architecture du projet

```
fabrication-management/
├── backend/                        # API REST Spring Boot
│   ├── src/main/java/com/gestion/fabrication/
│   │   ├── config/                 # Configuration (ModelMapper, CORS)
│   │   ├── controller/             # Contrôleurs REST (@RestController)
│   │   ├── dto/                    # Data Transfer Objects
│   │   ├── entity/                 # Entités JPA (@Entity)
│   │   ├── exception/              # Gestion globale des erreurs
│   │   ├── mapper/                 # Conversion Entité ↔ DTO
│   │   ├── repository/             # Accès base de données (JpaRepository)
│   │   └── service/                # Couche métier (@Service)
│   ├── Dockerfile
│   └── pom.xml
├── frontend/                       # Application Angular
│   ├── src/app/
│   │   ├── components/             # Composants (dashboard, produits, machines, employés, ordres)
│   │   └── services/               # Service HTTP (ApiService)
│   ├── nginx.conf                  # Configuration Nginx + proxy vers le backend
│   └── Dockerfile
├── k8s/                            # Manifestes Kubernetes (déploiement GKE)
│   ├── namespace.yaml              # Namespaces : dev, test, production
│   ├── dev/                        # Environnement développement
│   │   ├── dev-configmap.yaml
│   │   ├── dev-backend-deployment.yaml
│   │   ├── dev-frontend-deployment.yaml
│   │   ├── dev-mysql-deployment.yaml
│   │   └── dev-mysql-pvc.yaml
│   ├── test/                       # Environnement test
│   │   ├── test-configmap.yaml
│   │   ├── test-backend-deployment.yaml
│   │   ├── test-frontend-deployment.yaml
│   │   ├── test-mysql-deployment.yaml
│   │   └── test-mysql-pvc.yaml
│   └── production/                 # Environnement production (GKE)
│       ├── prod-configmap.yaml
│       ├── prod-backend-deployment.yaml
│       ├── prod-frontend-deployment.yaml
│       ├── prod-mysql-deployment.yaml
│       └── prod-mysql-pvc.yaml
├── docker-compose.yml              # Orchestration locale des 3 services
└── README.md
```

---

## Endpoints de l'API REST

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/produits` | Lister tous les produits |
| POST | `/api/produits` | Créer un produit |
| PUT | `/api/produits/{id}` | Modifier un produit |
| DELETE | `/api/produits/{id}` | Supprimer un produit |
| GET | `/api/machines` | Lister toutes les machines |
| POST | `/api/machines` | Créer une machine |
| PUT | `/api/machines/{id}` | Modifier une machine |
| DELETE | `/api/machines/{id}` | Supprimer une machine |
| GET | `/api/employes` | Lister tous les employés |
| POST | `/api/employes` | Créer un employé |
| PUT | `/api/employes/{id}` | Modifier un employé |
| DELETE | `/api/employes/{id}` | Supprimer un employé |
| GET | `/api/ordres` | Lister tous les ordres |
| POST | `/api/ordres` | Créer un ordre de fabrication |
| PUT | `/api/ordres/{id}` | Modifier un ordre |
| DELETE | `/api/ordres/{id}` | Supprimer un ordre |
| PATCH | `/api/ordres/{id}/etat` | Changer l'état d'un ordre |

Documentation interactive complète disponible sur : `http://localhost:8080/swagger-ui.html`

---

## Instructions d'installation et d'exécution

### Prérequis

- [Docker](https://www.docker.com/get-started) installé
- [Docker Compose](https://docs.docker.com/compose/install/) installé

### Lancement avec Docker Compose (recommandé)

1. Cloner le repository :
```bash
git clone https://github.com/aziz-rmili/fab-managment.git
cd fab-managment
```

2. Lancer tous les services :
```bash
docker-compose up --build
```

3. Accéder à l'application :

| Service | URL |
|---|---|
| Frontend (Angular) | http://localhost:4200 |
| Backend (API REST) | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |

4. Arrêter l'application :
```bash
docker-compose down
```

> La base de données MySQL est persistée dans un volume Docker. Les données sont conservées entre les redémarrages.

---

### Déploiement Kubernetes sur Google Kubernetes Engine (GKE)

Prérequis : `kubectl` configuré et connecté à un cluster GKE, images Docker publiées sur Google Container Registry.

#### 1. Créer les namespaces

```bash
kubectl apply -f k8s/namespace.yaml
```

#### 2. Déployer un environnement (exemple : production)

```bash
kubectl apply -f k8s/production/
```

#### 3. Vérifier le déploiement

```bash
kubectl get all -n production
```

#### 4. Récupérer l'IP externe du frontend

```bash
kubectl get service frontend -n production
```

L'IP externe affichée dans la colonne `EXTERNAL-IP` est l'URL d'accès à l'application.

> Les 3 environnements disponibles sont : `dev`, `test`, `production`. Chaque environnement est isolé dans son propre namespace Kubernetes.

---

### Lancement sans Docker (développement)

#### Back-end

Prérequis : Java 17, Maven, MySQL 8.0

1. Créer la base de données MySQL :
```sql
CREATE DATABASE fabrication_db;
```

2. Configurer `backend/src/main/resources/application.properties` si nécessaire :
```properties
spring.datasource.username=root
spring.datasource.password=root
```

3. Lancer le back-end :
```bash
cd backend
mvn spring-boot:run
```

#### Front-end

Prérequis : Node.js 20, npm

```bash
cd frontend
npm install
ng serve
```

L'application sera accessible sur `http://localhost:4200`.

---

## Fonctionnalités principales

- ✅ CRUD complet sur les 4 entités (Produit, Machine, Employé, Ordre de fabrication)
- ✅ Création et suivi des ordres de fabrication avec gestion des états
- ✅ Affectation des machines et employés aux ordres
- ✅ Suivi des maintenances et disponibilités des machines
- ✅ Validation des données côté API (Spring Validator)
- ✅ Gestion des erreurs avec réponses JSON structurées
- ✅ Documentation interactive de l'API (Swagger UI)
- ✅ Interface Angular responsive avec Bootstrap
- ✅ Déploiement Docker Compose (local)
- ✅ Déploiement Kubernetes multi-environnements sur GKE (dev / test / production)

---

## Auteur

Projet réalisé dans le cadre du module Spring Boot / Angular.

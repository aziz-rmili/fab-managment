import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Les interfaces correspondent exactement aux DTOs retournés par le backend

export interface Produit {
  id?: number;
  nom: string;
  type: string;
  stock: number;
  fournisseur: string;
}

export interface Machine {
  id?: number;
  nom: string;
  etat: string;
  derniereMaintenance?: string;
}

export interface Employe {
  id?: number;
  nom: string;
  poste: string;
  machineId?: number;    // ID pour création/modification
  machineNom?: string;   // Nom pour affichage (retourné par le backend)
}

export interface OrdreFabrication {
  id?: number;
  projet: string;
  quantite: number;
  date: string;
  etat: string;
  produitId?: number;    // ID pour création/modification
  produitNom?: string;   // Nom pour affichage
  machineId?: number;
  machineNom?: string;
  employeId?: number;
  employeNom?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  // On utilise /api au lieu de http://localhost:8080/api
  // Nginx intercepte /api/ et redirige vers le backend automatiquement
  // Ça marche en local ET sur GKE sans changer le code
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  // ===== PRODUITS =====
  getProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.baseUrl}/produits`);
  }
  createProduit(p: Produit): Observable<Produit> {
    return this.http.post<Produit>(`${this.baseUrl}/produits`, p);
  }
  updateProduit(id: number, p: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.baseUrl}/produits/${id}`, p);
  }
  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/produits/${id}`);
  }

  // ===== MACHINES =====
  getMachines(): Observable<Machine[]> {
    return this.http.get<Machine[]>(`${this.baseUrl}/machines`);
  }
  createMachine(m: Machine): Observable<Machine> {
    return this.http.post<Machine>(`${this.baseUrl}/machines`, m);
  }
  updateMachine(id: number, m: Machine): Observable<Machine> {
    return this.http.put<Machine>(`${this.baseUrl}/machines/${id}`, m);
  }
  deleteMachine(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/machines/${id}`);
  }

  // ===== EMPLOYES =====
  getEmployes(): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.baseUrl}/employes`);
  }
  createEmploye(e: Employe): Observable<Employe> {
    return this.http.post<Employe>(`${this.baseUrl}/employes`, e);
  }
  updateEmploye(id: number, e: Employe): Observable<Employe> {
    return this.http.put<Employe>(`${this.baseUrl}/employes/${id}`, e);
  }
  deleteEmploye(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/employes/${id}`);
  }

  // ===== ORDRES =====
  getOrdres(): Observable<OrdreFabrication[]> {
    return this.http.get<OrdreFabrication[]>(`${this.baseUrl}/ordres`);
  }
  createOrdre(o: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.post<OrdreFabrication>(`${this.baseUrl}/ordres`, o);
  }
  updateOrdre(id: number, o: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.put<OrdreFabrication>(`${this.baseUrl}/ordres/${id}`, o);
  }
  deleteOrdre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/ordres/${id}`);
  }
  changerEtatOrdre(id: number, etat: string): Observable<OrdreFabrication> {
    return this.http.patch<OrdreFabrication>(
      `${this.baseUrl}/ordres/${id}/etat`,
      etat,
      { headers: { 'Content-Type': 'application/json' } }
    );
  }
}

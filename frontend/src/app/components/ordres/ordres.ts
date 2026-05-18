//import { Component } from '@angular/core';

//@Component({
  //selector: 'app-ordres',
  //imports: [],
  //templateUrl: './ordres.html',
  //styleUrl: './ordres.css',
//})
//export class Ordres {}


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService, OrdreFabrication, Produit, Machine, Employe } from '../../services/api';

@Component({
  selector: 'app-ordres',
  imports: [CommonModule, FormsModule],
  templateUrl: './ordres.html',
  styleUrl: './ordres.css'
})
export class OrdresComponent implements OnInit {

  ordres: OrdreFabrication[] = [];
  produits: Produit[] = [];
  machines: Machine[] = [];
  employes: Employe[] = [];

  showModal = false;
  isEditing = false;
  loading = false;

  selectedProduitId: number | null = null;
  selectedMachineId: number | null = null;
  selectedEmployeId: number | null = null;

  form: OrdreFabrication = {
    projet: '',
    quantite: 1,
    date: new Date().toISOString().split('T')[0],
    etat: 'EN_ATTENTE'
  };

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.load();
    this.api.getProduits().subscribe(d => this.produits = d);
    this.api.getMachines().subscribe(d => this.machines = d);
    this.api.getEmployes().subscribe(d => this.employes = d);
  }

  load() {
    this.api.getOrdres().subscribe(data => this.ordres = data);
  }

  openCreate() {
    this.isEditing = false;
    this.form = {
      projet: '',
      quantite: 1,
      date: new Date().toISOString().split('T')[0],
      etat: 'EN_ATTENTE'
    };
    this.selectedProduitId = null;
    this.selectedMachineId = null;
    this.selectedEmployeId = null;
    this.showModal = true;
  }

  openEdit(o: OrdreFabrication) {
    this.isEditing = true;
    this.form = { ...o };
    // Les IDs sont directement dans le DTO retourné par le backend
    this.selectedProduitId = o.produitId ?? null;
    this.selectedMachineId = o.machineId ?? null;
    this.selectedEmployeId = o.employeId ?? null;
    this.showModal = true;
  }

  save() {
    if (!this.selectedProduitId) return;
    this.loading = true;

    const payload: OrdreFabrication = {
      ...this.form,
      produitId: this.selectedProduitId,
      machineId: this.selectedMachineId ?? undefined,
      employeId: this.selectedEmployeId ?? undefined
    };

    if (this.isEditing && this.form.id) {
      this.api.updateOrdre(this.form.id, payload).subscribe({
        next: () => { this.load(); this.showModal = false; this.loading = false; },
        error: () => { this.loading = false; }
      });
    } else {
      this.api.createOrdre(payload).subscribe({
        next: () => { this.load(); this.showModal = false; this.loading = false; },
        error: () => { this.loading = false; }
      });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer cet ordre ?')) {
      this.api.deleteOrdre(id).subscribe(() => this.load());
    }
  }

  changerEtat(id: number, etat: string) {
    this.api.changerEtatOrdre(id, etat).subscribe(() => this.load());
  }

  // Le backend retourne produitNom directement dans le DTO
  getProduitNom(o: OrdreFabrication): string {
    return o.produitNom ?? '—';
  }

  getMachineNom(o: OrdreFabrication): string {
    return o.machineNom ?? '—';
  }

  getEmployeNom(o: OrdreFabrication): string {
    return o.employeNom ?? '—';
  }

  getBadgeClass(etat: string): string {
    switch (etat) {
      case 'EN_ATTENTE': return 'badge-en-attente';
      case 'EN_COURS': return 'badge-en-cours';
      case 'TERMINE': return 'badge-termine';
      case 'ANNULE': return 'badge-annule';
      default: return '';
    }
  }

  getProchainEtat(etat: string): string | null {
    switch (etat) {
      case 'EN_ATTENTE': return 'EN_COURS';
      case 'EN_COURS': return 'TERMINE';
      default: return null;
    }
  }

  getProchainLabel(etat: string): string {
    switch (etat) {
      case 'EN_ATTENTE': return 'Démarrer';
      case 'EN_COURS': return 'Terminer';
      default: return '';
    }
  }
}

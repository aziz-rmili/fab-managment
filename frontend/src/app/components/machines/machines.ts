//import { Component } from '@angular/core';

//@Component({
  //selector: 'app-machines',
 // imports: [],
 // templateUrl: './machines.html',
 // styleUrl: './machines.css',
//})
//export class Machines {}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService, Machine } from '../../services/api';

@Component({
  selector: 'app-machines',
  imports: [CommonModule, FormsModule],
  templateUrl: './machines.html',
  styleUrl: './machines.css'
})
export class MachinesComponent implements OnInit {

  machines: Machine[] = [];
  showModal = false;
  isEditing = false;
  loading = false;

  form: Machine = { nom: '', etat: 'DISPONIBLE' };

  constructor(private api: ApiService) {}

  ngOnInit() { this.load(); }

  load() {
    this.api.getMachines().subscribe(data => this.machines = data);
  }

  openCreate() {
    this.isEditing = false;
    this.form = { nom: '', etat: 'DISPONIBLE' };
    this.showModal = true;
  }

  openEdit(m: Machine) {
    this.isEditing = true;
    this.form = { ...m };
    this.showModal = true;
  }

  save() {
    this.loading = true;
    if (this.isEditing && this.form.id) {
      this.api.updateMachine(this.form.id, this.form).subscribe({
        next: () => { this.load(); this.showModal = false; this.loading = false; },
        error: () => { this.loading = false; }
      });
    } else {
      this.api.createMachine(this.form).subscribe({
        next: () => { this.load(); this.showModal = false; this.loading = false; },
        error: () => { this.loading = false; }
      });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer cette machine ?')) {
      this.api.deleteMachine(id).subscribe(() => this.load());
    }
  }

  // Démarrer maintenance : on met l'état à EN_MAINTENANCE via updateMachine
  demarrerMaintenance(m: Machine) {
    if (confirm('Démarrer la maintenance de cette machine ?')) {
      const updated: Machine = {
        ...m,
        etat: 'EN_MAINTENANCE',
        derniereMaintenance: new Date().toISOString().split('T')[0]
      };
      this.api.updateMachine(m.id!, updated).subscribe(() => this.load());
    }
  }

  // Terminer maintenance : on remet l'état à DISPONIBLE
  terminerMaintenance(m: Machine) {
    if (confirm('Terminer la maintenance et remettre la machine en service ?')) {
      const updated: Machine = { ...m, etat: 'DISPONIBLE' };
      this.api.updateMachine(m.id!, updated).subscribe(() => this.load());
    }
  }

  getBadgeClass(etat: string): string {
    switch (etat) {
      case 'DISPONIBLE': return 'badge-disponible';
      case 'EN_MAINTENANCE': return 'badge-maintenance';
      case 'HORS_SERVICE': return 'badge-hors-service';
      default: return '';
    }
  }
}

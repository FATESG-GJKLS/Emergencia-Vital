import { Routes } from '@angular/router';
import { TelaLogin } from './telas/login/tela-login';
import { TelaGestor } from './telas/gestor/tela-gestor';
import { TelaProfissional } from './telas/profissional/tela-profissional';
import { TelaAtendente } from './telas/atendente/tela-atendente';

export const routes: Routes = [
  { path: '', component: TelaLogin },
  { path: 'gestor', redirectTo: 'gestor/dashboard', pathMatch: 'full' },
  { path: 'gestor/:tela', component: TelaGestor },
  { path: 'profissional', redirectTo: 'profissional/ocorrencias', pathMatch: 'full' },
  { path: 'profissional/:tela', component: TelaProfissional },
  { path: 'atendente', redirectTo: 'atendente/ocorrencias', pathMatch: 'full' },
  { path: 'atendente/:tela', component: TelaAtendente },
  { path: '**', redirectTo: '' }
];
